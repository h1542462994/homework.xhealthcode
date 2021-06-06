package services;

import dao.ResourceLocator;
import dao.UserDao;
import ext.exception.OperationFailedException;
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

    UserAccess login(UserLogin login, HttpServletResponse response);

    UserDao fromTypeNumber(int type, String number) throws OperationFailedException;

    UserAccess active(HttpServletRequest request);

    void logout(HttpServletRequest request, HttpServletResponse response);

    boolean insertOrUpdateUser(UserRequest userRequest, long id);

    UserDao get(long id);

    UserDao get(User user);

    void delete(long id);

    ArrayList<UserDao> fromLocator(ResourceLocator locator);
}
