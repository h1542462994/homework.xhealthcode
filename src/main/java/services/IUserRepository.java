package services;

import dao.PageDao;
import dao.UserDao;
import dao.UserResult;
import models.User;
import models.UserAccess;
import requests.UserLogin;

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

    UserResult result(User user);

    long count();

    ArrayList<UserResult> page(long start, long count);

    PageDao<UserResult> fromLocator(ResourceLocator locator);
}
