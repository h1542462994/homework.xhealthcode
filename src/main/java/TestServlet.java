import ext.*;
import services.DbContext;
import model.User;
import services.ServiceContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;

@WebServlet(name = "TestServlet", urlPatterns = "/*")
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            DbContext context = ServiceContainer.get().dbContext();

            for (User user: context.Users.query("nick_name = ?", "hello world")) {
                response.getWriter().write(user.getUserId() + " " + user.getEmail() + "\n");
            }
        } catch (NullPointerException | ServiceConstructException | OperationFailedException e) {
            response.getWriter().write("error");
            e.printStackTrace();
        }

    }
}
