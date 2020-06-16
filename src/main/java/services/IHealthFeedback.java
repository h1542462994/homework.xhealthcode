package services;

import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import models.DailyCard;
import requests.DailyCardAnswer;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

public interface IHealthFeedback {
    String getMsg();

    public DailyCard creatDailyCard(DailyCardAnswer dailyCardAnswer, HttpServletRequest request) throws ServiceConstructException;

    public void addToSql(DailyCard dailyCard) throws OperationFailedException;

}
