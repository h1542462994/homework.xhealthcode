package controllers;

import dao.CollegeDao;
import dao.ProfessionDao;
import ext.exception.ServiceConstructException;
import services.ICollegeRepository;
import services.ServiceContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controller/AdminCollegeServlet", urlPatterns = "/admin/college")
public class AdminCollegeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        if(page == null || page.equals("college")){
            request.setAttribute("path", new CollegeDao());
            request.getRequestDispatcher("/admin_college.jsp").forward(request, response);
            return;
        }

        if(page.equals("profession")){
            int college = Integer.parseInt(request.getParameter("college"));
//                ICollegeRepository collegeRepository = ServiceContainer.get().collegeRepository();
//                CollegeDao collegeDao = collegeRepository.getCollege(college);
            request.setAttribute("path", CollegeDao.getPathFromCollege(college));
            request.getRequestDispatcher("/admin_profession.jsp").forward(request, response);
            return;
        }

        if (page.equals("xclass")){
            int profession = Integer.parseInt(request.getParameter("profession"));
//                ICollegeRepository collegeRepository = ServiceContainer.get().collegeRepository();
//                ProfessionDao professionDao = collegeRepository.getProfession(profession);
//                CollegeDao collegeDao = collegeRepository.getCollege(professionDao.getCollegeId());
//                request.setAttribute("college", collegeDao);
//                request.setAttribute("profession", professionDao);
            request.setAttribute("path", CollegeDao.getPathFromProfession(profession));
            request.getRequestDispatcher("/admin_xclass.jsp").forward(request, response);
            return;
        }
    }
}
