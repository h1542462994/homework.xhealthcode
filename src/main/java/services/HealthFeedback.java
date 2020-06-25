package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.UserDao;
import ext.sql.DbContextBase;
import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import models.*;
import requests.UserAcquire;
import util.QRCode.QRCodeInfo;
import util.QRCode.QRCodeParams;
import util.QRCode.QRCodeUtil;
import util.QRCode.QRParamsException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
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
        Cache.clearCache();
        addInfo(creatInfo(userAcquire,request));
        addDailyCard(creatDailyCard(userAcquire,request));
    }

    /**
     * 打卡表单存库，更新info表
     */
    public void processingClock(UserAcquire userAcquire, HttpServletRequest request)
            throws ServiceConstructException, OperationFailedException{
        Cache.clearCache();
        updateInfo(creatInfoBaseOnPre(userAcquire,request));
        addDailyCard(creatDailyCard(userAcquire,request));
    }

    /**
     * 生成健康码信息：打包为json
     * @param request 信息源：获取当前用户信息
     */
    public String creatQRCodeInfo(HttpServletRequest request) throws ServiceConstructException, OperationFailedException {
        UserDao user = getUserDao(request);
        String name = user.getName();
        String number = user.getNumber();
        int type = user.getType();
        long fieldId = user.getFieldId();
        int result = user.getResult();

        if(type == 0){
            Xclass xclass = context.xclasses.query("xClassId = ?", fieldId).unique();
            College college = ServiceContainer.get().collegeRepository().getCollege(xclass);
            fieldId = college.getCollegeId();
        }

        String sType = "";
        if(type == 1)
            sType = "教师";
        else if(type == 0)
            sType = "学生";

        String sCollege;
        College college = context.colleges.query("collegeId = ?", fieldId).unique();
        sCollege = college.getName();

        QRCodeInfo qrCodeInfo = new QRCodeInfo();
        qrCodeInfo.setCollege(sCollege);
        qrCodeInfo.setName(name);
        qrCodeInfo.setNumber(number);
        qrCodeInfo.setType(sType);
        qrCodeInfo.setResult(result);

        Gson gson = new Gson();
        return gson.toJson(qrCodeInfo);
    }

    public void creatQRCodeImg(String info, String path) throws QRParamsException {

        JsonObject jsonObject = new JsonParser().parse(info).getAsJsonObject();
        int result = jsonObject.get("result").getAsInt();
        String number = jsonObject.get("number").getAsString();

        QRCodeParams qrCode = new QRCodeParams();
        qrCode.setFilePath(path + "/image/QRCodeOut");




        qrCode.setTxt(info);
        qrCode.setFileName(number + ".png");
        if(result == 1){
            qrCode.setOnColor(0xFF3e86c5);
        }else if (result == 2){
            qrCode.setOnColor(0xFFFFFF00);
        }else if (result == 3){
            qrCode.setOnColor(0xFFFF0000);
        }

        QRCodeUtil.generateQRImage(qrCode);
    }

    public void creatQRCode(HttpServletRequest request) throws ServiceConstructException, OperationFailedException, QRParamsException {
        creatQRCodeImg(creatQRCodeInfo(request),request.getRealPath("/"));
    }


}
