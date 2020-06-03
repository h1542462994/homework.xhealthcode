package controllers.api;

import dao.CollegeDao;
import dao.ProfessionDao;
import ext.exception.ServiceConstructException;
import models.College;
import models.Profession;
import services.ICache;
import services.ICollegeRepository;
import services.ServiceContainer;
import util.Api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@WebServlet(name = "ProfessionServlet", urlPatterns = "/api/profession")
public class ProfessionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            int college = Integer.parseInt(request.getParameter("college"));
            if(action == null){
                Api.sendError(response, 403, "未指定action或college");
                return;
            }

            ICache cache = ServiceContainer.get().cache();
            ICollegeRepository collegeRepository = ServiceContainer.get().collegeRepository();
            if(action.equals("get")){
                ArrayList<ProfessionDao> professionDaos = cache.professionDaos(college);
                if(professionDaos == null){
                    Api.sendError(response, 403, "不存在指定的college");
                    return;
                }

                Api.sendOK(response, cache.professionDaos(college));
                return;
            }
            //TODO 对college是否进行验证
            if(action.equals("add")) {
                String name = request.getParameter("name");
                // TODO 对name的合法性进行验证
                if(name == null){
                    Api.sendError(response, 403, "name不符合要求");
                    return;
                }

                Profession profession = new Profession();
                profession.setCollegeId(college);
                profession.setName(name);
                ProfessionDao professionDao = collegeRepository.addProfession(profession);
                if(professionDao == null){
                    Api.sendError(response, 403, "插入profession失败");
                    return;
                }
                Api.sendOK(response, professionDao);
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

                Profession profession = new Profession();
                profession.setCollegeId(college);
                profession.setProfessionId(Long.parseLong(id));
                profession.setName(name);

                ProfessionDao professionDao = collegeRepository.updateProfession(profession);
                if(professionDao == null){
                    Api.sendError(response, 403, "更新college失败");
                    return;
                }
                Api.sendOK(response, professionDao);
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
                    collegeRepository.deleteProfession(Long.parseLong(id));
                }
                Api.sendOK(response,null);
                return;
            }

            Api.sendError(response, 403, "不支持action");

        } catch (ServiceConstructException e) {
            e.printStackTrace();
        }
    }
}