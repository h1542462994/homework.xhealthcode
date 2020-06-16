package services;

import com.google.gson.Gson;
import dao.*;
import enums.Result;
import enums.TypeType;
import ext.declare.DbContextBase;
import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import models.*;
import requests.DailyCardAnswer;
import requests.UserLogin;
import requests.UserRequest;
import util.StringTools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class HealthFeedback implements IHealthFeedback{
    private final DbContext context;

    private String msg;

    public HealthFeedback(DbContextBase context){
        this.context = (DbContext) context;
    }

    public String getMsg(){
        return msg;
    }


    public Date creatDate(){
        return new Date(System.currentTimeMillis());
    }

    public String creatAnswer(DailyCardAnswer dailyCardAnswer){
        String answer;
        Gson gson = new Gson();
        answer = gson.toJson(dailyCardAnswer);
        return answer;
    }

    public int creatResult(DailyCardAnswer dailyCardAnswer) {

        if(dailyCardAnswer.isDefiniteDiagnosis.equals("y") ||
                dailyCardAnswer.isContactedPatient.equals("y") || dailyCardAnswer.illness.length >= 2){
            return 2;
        }

        if(dailyCardAnswer.isArrivedInfectedArea.equals("y") ||
                dailyCardAnswer.isBeenAbroad.equals("y") || dailyCardAnswer.illness.length == 1){
            return 1;
        }

        return 0;
    }

    public DailyCard creatDailyCard(DailyCardAnswer dailyCardAnswer, HttpServletRequest request) throws ServiceConstructException {
        DailyCard dailyCard = new DailyCard();

        IUserRepository userRepository = ServiceContainer.get().userRepository();
        UserAccess userAccess = userRepository.active(request);

        dailyCard.setUserId(userAccess.getUserId());
        dailyCard.setAnswer(creatAnswer(dailyCardAnswer));
        dailyCard.setResult(creatResult(dailyCardAnswer));
        dailyCard.setDate(creatDate());

        return dailyCard;
    }

    public void addToSql(DailyCard dailyCard) throws OperationFailedException {
        context.dailyCards.add(dailyCard);
    }



}
