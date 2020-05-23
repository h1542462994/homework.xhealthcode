package services;

import dao.UserHandle;
import requests.AdminLogin;

import javax.servlet.ServletContext;

public interface IUserRepository {
    UserHandle adminLogin(AdminLogin login, ServletContext servletContext);
}
