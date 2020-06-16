//package util.QRCode;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@WebServlet(name = "QRCodeCreateServlet", urlPatterns = {"/QRCodeCreateServlet"})
//public class QRCodeCreateServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        String id = request.getParameter("id");
//        String name = request.getParameter("name");
//        String ID_number = request.getParameter("ID_number");
//        String phone = request.getParameter("phone");
//        String Q1 = request.getParameter("Q1");
//        String Q2 = request.getParameter("Q2");
//        String Q3 = request.getParameter("Q3");
//        String Q4 = request.getParameter("Q4");
//        String[] Q5 = request.getParameterValues("Q5");
//        User user = new User(id, name, ID_number, phone);
//
//        Date d = new Date();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String now = df.format(d);
//
//        QRCodeParams QRCode = new QRCodeParams();
//        QRCode.setTxt("姓名：" + name + "\n" +
//                "学号/工号：" + id + "\n" +
//                "生成二维码时间：" + now);
//        QRCode.setFileName(name + "的健康码.png");
//        QRCode.setFilePath("D:\\Code\\Web\\idea\\workspace\\experiment03\\out\\artifacts\\experiment03_war_exploded\\QRCodeImage");
//        if (Q3.equals("Y") || Q4.equals("Y") || (Q5.length >= 2 && !Q5[0].equals("无异常"))) {
//            QRCode.setOnColor(0xFFFF0000);
//            user.setColor("red");
//        } else if (Q1.equals("Y") || Q2.equals("Y") || (Q5.length == 1 && !Q5[0].equals("无异常"))) {
//            QRCode.setOnColor(0xFFFFFF00);
//            user.setColor("yellow");
//        } else {
//            QRCode.setOnColor(0xFF008000);
////            QRCode.setOnColor(0xFF3e86c5);
//            user.setColor("green");
//        }
//        try {
//            QRCodeUtil.generateQRImage(QRCode);
//        } catch (QRParamsException e) {
//            e.printStackTrace();
//        }
//
//        HttpSession session = request.getSession();
//        synchronized (session) {
//            session.setAttribute("user", user);
//        }
//        RequestDispatcher rd = request.getRequestDispatcher("/showHealthCode.jsp");
//        rd.forward(request, response);
//
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//}
