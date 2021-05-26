package controllers.api;

import ext.exception.ServiceConstructException;
import services.CurrentTimeService;
import services.ServiceContainer;
import util.Web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@WebServlet(name = "CurrentTimeServlet", urlPatterns = "/api/time")
public class CurrentTimeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            // 获取当前时间的服务
            CurrentTimeService timeService = ServiceContainer.get().currentTimeService();
            if (action.equals("set")) {
                String time = request.getParameter("time");
                LocalDateTime localDateTime = LocalDateTime.parse(time);
                timeService.setCurrentTime(localDateTime);
                Web.sendOK(response,"修改时间成功");
            } else if (action.equals("get")) {
                Web.sendOK(response, timeService.getCurrentTime().toString());
            } else if (action.equals("getdate")) {
                Web.sendOK(response, timeService.getCurrentTime().toLocalDate().toString());
            } else {
                Web.sendError(response, 403, "不支持的action操作");
            }
        } catch(DateTimeParseException e) {
            Web.sendError(response, 403, "时间解析失败");
            e.printStackTrace();
        } catch (ServiceConstructException e)  {
            e.printStackTrace();
        }
    }
}
