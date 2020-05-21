import ext.*;
import ext.service.DbContext;
import model.User;

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

            response.getWriter().write(context.Users.get(2).getName());

            for (User user: context.Users.all()) {
                response.getWriter().write(user.getName() + "\n");
            }
//            for (Field field : User.class.getDeclaredFields()) {
//                response.getWriter().write(field.getName());
//            }
        } catch (NullPointerException | ServiceConstructException e) {
            response.getWriter().write("error");
            e.printStackTrace();
        }

    }
}
