package controllers.api;

import enums.RoleType;
import ext.exception.ServiceConstructException;
import imports.ImportCollection;
import services.IImportAction;
import services.ServiceContainer;
import util.ExcelImport;
import util.MultiForm;
import util.Web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "ImportServlet", urlPatterns = "/api/import")
public class ImportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Web.adminPass(request, RoleType.SYSTEM, null);

        Web.sendFile(response, getServletContext(), "/excel/import.xlsx");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Web.adminPass(request, RoleType.SYSTEM, null);

            MultiForm form = new MultiForm(request);
            String fileName = form.getStreams().get("file").first;
            InputStream stream = form.getStreams().get("file").second;
            ExcelImport excelImport = new ExcelImport(fileName, stream);
            excelImport.read();
            if(excelImport.isValid()){
                ImportCollection collection = excelImport.getImported();
                collection.validate();
                IImportAction importAction = ServiceContainer.get().importAction();
                importAction.importData(collection);
                Web.sendOK(response, collection);
            } else {
                Web.sendError(response, 403, "读取文件失败");
            }

            form.close();
        } catch (NumberFormatException e){
            e.printStackTrace();
            Web.sendError(response, 403, "输入参数错误");
        } catch (ServiceConstructException e) {
            e.printStackTrace();
            Web.sendError(response, 403, "创建服务异常");
        }
    }
}
