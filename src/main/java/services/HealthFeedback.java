package services;

import com.google.gson.Gson;
import dao.UserDao;
import enums.Result;
import ext.declare.DbContextBase;
import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import models.*;
import requests.UserAcquire;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class HealthFeedback implements IHealthFeedback{
    private final DbContext context;
    private String msg;

    public HealthFeedback(DbContextBase context){
        this.context = (DbContext) context;
    }

    public String getMsg(){
        return msg;
    }

    public UserDao getUserDao(HttpServletRequest request) throws ServiceConstructException {
        IUserRepository repository = ServiceContainer.get().userRepository();
        repository = ServiceContainer.get().userRepository();
        UserAccess access = repository.active(request);
        UserDao user= repository.get(access.getUserId());
        return user;
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

        Info info = new Info();
        info.setDate(creatDate());
        info.setPhone(userAcquire.phone);
        info.setResult(creatResult(userAcquire));
        info.setUserId(getUserId(request));
        info.setContinuousClockDays(1);

        return info;
    }

    public void test(UserAcquire userAcquire, HttpServletRequest request)
            throws ServiceConstructException, OperationFailedException{

        creatInfoBaseOnPre(userAcquire,request);

    }

    public Info creatInfoBaseOnPre(UserAcquire userAcquire, HttpServletRequest request)
            throws ServiceConstructException, OperationFailedException {
        long userId = getUserId(request);
        Info preInfo = context.infos.query("userId = ?", userId).unique();

        int preResult = preInfo.getResult();
        int curResult = creatResult(userAcquire);
        int continuousClockDays = preInfo.getContinuousClockDays();
        Date preDate = preInfo.getDate();
        Date curDate = creatDate();
        long gap = ChronoUnit.DAYS.between(preDate.toLocalDate(),preDate.toLocalDate());

        if(curResult == 0){
            if(gap == 0)
                continuousClockDays ++;
            else
                continuousClockDays = 1;
            if(continuousClockDays >= 7)
                if(preResult != 0){
                    preResult --;
                    continuousClockDays = 0;
                }
        }else{
            preResult = curResult;
            continuousClockDays = 0;
        }

        Info info = new Info();
        info.setContinuousClockDays(continuousClockDays);
        info.setDate(curDate);
        info.setUserId(userId);
        info.setPhone(preInfo.getPhone());
        info.setResult(preResult);
        info.setInfoId(preInfo.getInfoId());

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
    /**
     * 申请健康码表单存库
     * @param userAcquire 表单数据容器
     */
    public void processingAcquire(UserAcquire userAcquire, HttpServletRequest request)
            throws ServiceConstructException, OperationFailedException{
        addInfo(creatInfo(userAcquire,request));
        addDailyCard(creatDailyCard(userAcquire,request));
    }

    /**
     * 打卡表单存库，更新info表
     */
    public void processingClock(UserAcquire userAcquire, HttpServletRequest request)
            throws ServiceConstructException, OperationFailedException{
        updateInfo(creatInfoBaseOnPre(userAcquire,request));
        addDailyCard(creatDailyCard(userAcquire,request));
    }

}
