package services;

import dao.ResourceLocator;
import dao.UserDao;
import models.User;
import models.UserAccess;
import requests.UserLogin;
import requests.UserRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * 用户仓储模式
 */
public interface IUserRepository {
    String getMsg();

//    UserDao get(long id);

    UserAccess login(UserLogin login, HttpServletResponse response);

    UserAccess active(HttpServletRequest request);

    //ArrayList<UserDao> fromCollege(long college);

    boolean addUser(UserRequest userRequest);

    void logout(HttpServletRequest request, HttpServletResponse response);

//    @Deprecated
//    UserDao result(User user);

//    UserDao getResultByLocator(User user, ResourceLocator locator);

//    @Deprecated
//    long count();
//
//    @Deprecated
//    ArrayList<UserDao> page(long start, long count);

    UserDao get(long id);

    UserDao get(User user);

    void delete(long id);

    ArrayList<UserDao> fromLocator(ResourceLocator locator);
}
