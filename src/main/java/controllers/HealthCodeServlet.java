package controllers;

import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import services.HealthFeedback;
import services.IHealthFeedback;
import services.ServiceContainer;
import util.QRCode.QRParamsException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers.HealthCodeServlet", urlPatterns = "/healthcode")
public class HealthCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            IHealthFeedback healthFeedback = ServiceContainer.get().healthFeedback();
            healthFeedback.creatQRCode(request);
        } catch (ServiceConstructException | OperationFailedException | QRParamsException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/healthcode.jsp").forward(request, response);
        try {
            IHealthFeedback healthFeedback = ServiceContainer.get().healthFeedback();
            healthFeedback.creatQRCode(request);
        } catch (ServiceConstructException | OperationFailedException | QRParamsException e) {
            e.printStackTrace();
        }
    }
}
