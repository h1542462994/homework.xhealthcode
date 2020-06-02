package services;

import dao.UserDao;
import models.UserAccess;
import requests.UserLogin;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * 用户仓储模式
 */
public interface IUserRepository {
    String getMsg();

    UserDao get(long id);

    UserAccess login(UserLogin login, HttpServletResponse response);

    UserAccess active(HttpServletRequest request);

    //ArrayList<UserDao> fromCollege(long college);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
