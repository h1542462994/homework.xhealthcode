package controllers.api;

import dao.CollegeDao;
import ext.exception.ServiceConstructException;
import models.College;
import services.ICache;
import services.ICollegeRepository;
import services.ServiceContainer;
import util.Api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CollegeServlet", urlPatterns = "/api/college")
public class CollegeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if(action == null){
                Api.sendError(response, 403, "未指定action");
                return;
            }

            ICollegeRepository collegeRepository = ServiceContainer.get().collegeRepository();
            ICache cache = ServiceContainer.get().cache();
            if(action.equals("get")) {
                Api.sendOK(response, cache.collegeDaos());
                return;
            }
            if(action.equals("add")) {
                String name = request.getParameter("name");
                // TODO 对name的合法性进行验证
                if(name == null){
                    Api.sendError(response, 403, "name不符合要求");
                    return;
                }

                College college = new College();
                college.setName(name);
                CollegeDao collegeDao = collegeRepository.addCollege(college);
                if(collegeDao == null){
                    Api.sendError(response, 403, "插入college失败");
                    return;
                }
                Api.sendOK(response, collegeDao);
                return;
            }
            if(action.equals("update")) {
                String id = request.getParameter("id");
                String name = request.getParameter("name");
                // TODO 对id和name的合法性进行验证
                if(id == null || name == null){
                    Api.sendError(response, 403, "id或name不符合要求");
                    return;
                }

                College college = new College();
                college.setCollegeId(Long.parseLong(id));
                college.setName(name);

                CollegeDao collegeDao = collegeRepository.updateCollege(college);
                if(collegeDao == null){
                    Api.sendError(response, 403, "更新college失败");
                    return;
                }
                Api.sendOK(response, collegeDao);
                return;
            }
            if(action.equals("delete")){
                String ids = request.getParameter("ids");
                //TODO 对ids的合法性进行验证
                if(ids == null){
                    Api.sendError(response, 403, "ids不符合要求");
                    return;
                }

                for (String id: ids.split(",")) {
                    collegeRepository.deleteCollege(Long.parseLong(id));
                }
                Api.sendOK(response,null);
                return;
            }

            Api.sendError(response, 403, "不支持的action操作");
        } catch (ServiceConstructException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
