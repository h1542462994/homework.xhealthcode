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
    private final IUserRepository userRepository;
    private final CurrentTimeService timeService;
    private String msg;

    public HealthFeedback(DbContextBase context, IUserRepository userRepository, CurrentTimeService timeService){
        this.context = (DbContext) context;
        this.userRepository = userRepository;
        this.timeService = timeService;
    }

    public String getMsg(){
        return msg;
    }

    public UserDao getUserDao(HttpServletRequest request) {
        UserAccess access = userRepository.active(request);
        return userRepository.get(access.getUserId());
    }

    public long getUserId(HttpServletRequest request) {
        UserAccess userAccess = userRepository.active(request);
        return userAccess.getUserId();
    }

    public Date creatDate(){
        //return new Date(System.currentTimeMillis());
        return timeService.getCurrentDateOld();
    }

    public String creatAnswer(UserAcquire userAcquire){
        String answer;
        Gson gson = new Gson();
        answer = gson.toJson(userAcquire);
        return answer;
    }

    public int creatResult(UserAcquire userAcquire) {
        int maxResult = 0;

        if(userAcquire.illness != null){
            if(userAcquire.illness.length >= 2)
                maxResult = 2;
            else if(userAcquire.illness.length == 1)
                maxResult = 1;
        }


        if(userAcquire.isDefiniteDiagnosis.equals("y") ||
                userAcquire.isContactedPatient.equals("y")){
            maxResult = 2;
        }

        if(userAcquire.isArrivedInfectedArea.equals("y") ||
                userAcquire.isBeenAbroad.equals("y")){
            maxResult = Math.max(maxResult, 1);
        }

        return maxResult;
    }

    public DailyCard creatDailyCard(UserAcquire userAcquire, long userId) {
        DailyCard dailyCard = new DailyCard();

        dailyCard.setUserId(userId);
        dailyCard.setAnswer(creatAnswer(userAcquire));
        dailyCard.setResult(creatResult(userAcquire));
        dailyCard.setDate(creatDate());
        return dailyCard;
    }

    public Info creatInfo(UserAcquire userAcquire, long userId) {

        Info info = new Info();
        info.setDate(creatDate());
        info.setPhone(userAcquire.phone);
        info.setResult(creatResult(userAcquire));
        info.setUserId(userId);
        info.setContinuousClockDays(0);

        return info;
    }

    public void test(UserAcquire userAcquire, HttpServletRequest request)
            throws ServiceConstructException, OperationFailedException{

        long userId = getUserId(request);
        creatInfoBaseOnPre(userAcquire, userId);

    }

    public Info creatInfoBaseOnPre(UserAcquire userAcquire, long userId)
            throws OperationFailedException {
        Info preInfo = context.infos.query("userId = ?", userId).unique();

        int preResult = preInfo.getResult();
        int curResult = creatResult(userAcquire);
        int continuousClockDays = preInfo.getContinuousClockDays();
        Date preDate = preInfo.getDate();
        Date curDate = creatDate();
        long gap = ChronoUnit.DAYS.between(preDate.toLocalDate(),curDate.toLocalDate());

        System.out.println("gap: "+gap);

        if(curResult == 0){
            if(gap == 1){
                continuousClockDays ++;
                if(continuousClockDays >= 7){
                    if(preResult != 0){
                        preResult --;
                        continuousClockDays = 0;
                    }
                }
            }
            else
                continuousClockDays = 1;
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
            throws OperationFailedException{
//        Cache.clearCache();
//        addInfo(creatInfo(userAcquire,request));
//        addDailyCard(creatDailyCard(userAcquire,request));
        long userId = getUserId(request);
        processingAcquireInternal(userAcquire, userId);
    }

    @Override
    public void processingAcquireInternal(UserAcquire userAcquire, long userId) throws OperationFailedException {
        Cache.clearCache();
        addInfo(creatInfo(userAcquire, userId));
        addDailyCard(creatDailyCard(userAcquire, userId));
    }


    /**
     * 打卡表单存库，更新info表
     */
    public void processingClock(UserAcquire userAcquire, HttpServletRequest request)
            throws OperationFailedException{
//        Cache.clearCache();
//        updateInfo(creatInfoBaseOnPre(userAcquire,request));
//        addDailyCard(creatDailyCard(userAcquire,request));
        long userId = getUserId(request);
        processingClockInternal(userAcquire, userId);
    }

    @Override
    public void processingClockInternal(UserAcquire userAcquire, long userId) throws OperationFailedException {
        Cache.clearCache();
        updateInfo(creatInfoBaseOnPre(userAcquire, userId));
        addDailyCard(creatDailyCard(userAcquire, userId));
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
            qrCode.setOnColor(0xFF528C76);
        }else if (result == 2){
            qrCode.setOnColor(0xFFaa9911);
        }else if (result == 3){
            qrCode.setOnColor(0xFFcf5c4b);
        }

        QRCodeUtil.generateQRImage(qrCode);
    }

    public void creatQRCode(HttpServletRequest request) throws ServiceConstructException, OperationFailedException, QRParamsException {
        creatQRCodeImg(creatQRCodeInfo(request),request.getRealPath("/"));
    }


}
