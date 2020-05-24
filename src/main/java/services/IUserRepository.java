package services;

import dao.UserHandle;
import requests.UserLogin;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserRepository {
    UserHandle adminLogin(UserLogin login, HttpServletResponse response);

    UserHandle getUser(HttpServletRequest request);
}
