package util;

import com.google.gson.Gson;
import dao.ApiResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Api {
    public static <T> void sendOK(HttpServletResponse response, T object) throws IOException {
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(new Gson().toJson(new ApiResponse<>(200,"ok",object) ));
    }

    public static <T> void sendError(HttpServletResponse response, int code, String msg) throws IOException{
        response.setContentType("text/json;charset=utf-8");
        response.setStatus(code);
        response.getWriter().write(new Gson().toJson(new ApiResponse<>(code, msg, null)));
    }
}
