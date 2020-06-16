package services;

import com.google.gson.Gson;
import enums.Result;
import ext.declare.DbContextBase;
import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import models.*;
import requests.UserAcquire;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class HealthFeedback implements IHealthFeedback{
    private final DbContext context;
    private String msg;

    public HealthFeedback(DbContextBase context){
        this.context = (DbContext) context;
    }

    public String getMsg(){
        return msg;
    }

    public long getUserId(HttpServletRequest request) throws ServiceConstructException {
        IUserRepository userRepository = ServiceContainer.get().userRepository();
        UserAccess userAccess = userRepository.active(request);
        return userAccess.getUserId();
    }

    public Date creatDate(){
        return new Date(System.currentTimeMillis());
    }

    public String creatAnswer(UserAcquire userAcquire){
        String answer;
        Gson gson = new Gson();
        answer = gson.toJson(userAcquire);
        return answer;
    }

    public int creatResult(UserAcquire userAcquire) {
        if(userAcquire.illness != null){
            if(userAcquire.illness.length >= 2)
                return 2;
            if(userAcquire.illness.length == 1)
                return 1;
        }


        if(userAcquire.isDefiniteDiagnosis.equals("y") ||
                userAcquire.isContactedPatient.equals("y")){
            return 2;
        }

        if(userAcquire.isArrivedInfectedArea.equals("y") ||
                userAcquire.isBeenAbroad.equals("y")){
            return 1;
        }

        return 0;
    }

    public DailyCard creatDailyCard(UserAcquire userAcquire, HttpServletRequest request)
            throws ServiceConstructException, OperationFailedException {
        DailyCard dailyCard = new DailyCard();

        dailyCard.setUserId(getUserId(request));
        dailyCard.setAnswer(creatAnswer(userAcquire));
        dailyCard.setResult(creatResult(userAcquire));
        dailyCard.setDate(creatDate());
        return dailyCard;
    }

    public Info creatInfo(UserAcquire userAcquire, HttpServletRequest request)
            throws ServiceConstructException {
        System.out.println("VANOTNOTE:StartCreatingInfo");
        Info info = new Info();
        info.setDate(creatDate());
        System.out.println("VANOTNOTE:CreatingInfo-Date");
        info.setPhone(userAcquire.phone);
        System.out.println("VANOTNOTE:CreatingInfo-Phone");
        info.setResult(creatResult(userAcquire));
        System.out.println("VANOTNOTE:CreatingInfo-Result");
        info.setUserId(getUserId(request));
        System.out.println("VANOTNOTE:CreatingInfo-UserId");
        info.setContinuousClockDays(0);
        System.out.println("VANOTNOTE:CreatingInfo-ClockDays");
        System.out.println("VANOTNOTE:EndCreatingInfo");

        return info;
    }

    public void addDailyCard(DailyCard dailyCard) throws OperationFailedException {
        context.dailyCards.add(dailyCard);
    }

    public void addInfo(Info info) throws OperationFailedException {
        context.infos.add(info);
    }

    public void updateInfo(Info info) throws OperationFailedException {
        context.infos.update(info);
    }

    public void processingAcquire(UserAcquire userAcquire, HttpServletRequest request)
            throws ServiceConstructException, OperationFailedException{
        System.out.println("VANOTNOTE:StartProcessingAcquire");
        addInfo(creatInfo(userAcquire,request));
        addDailyCard(creatDailyCard(userAcquire,request));
    }

    public void processingClock(UserAcquire userAcquire, HttpServletRequest request)
            throws ServiceConstructException, OperationFailedException{
        updateInfo(creatInfo(userAcquire,request));
        addDailyCard(creatDailyCard(userAcquire,request));
    }


}
