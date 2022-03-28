package by.training.gym.controller;

import by.training.gym.controller.command.CommandAction;
import by.training.gym.controller.command.CommandFactory;
import by.training.gym.controller.command.CurrentJsp;
import by.training.gym.util.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import static by.training.gym.controller.command.CommandAction.MESSAGE_ATTRIBUTE;
import static by.training.gym.util.MessageManager.NONE_MESSAGE_KEY;

@WebServlet("/ControllerServlet")
public class ControllerServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
//        PrintWriter printWriter = response.getWriter();
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Gym?useSSL=false" +
//                    "?verifyServerCertificate=false" +
//                    "&useSSL=false" +
//                    "&requireSSL=false" +
//                    "&useLegacyDatetimeCode=false" +
//                    "&amp" +
//                    "&serverTimezone=UTC", "root", "motherlode");
//            Statement statement = connection.createStatement();
//            printWriter.println("<html><head> <title> test</title> </head>");
//            ResultSet resultSet = statement.executeQuery("Select Firstname,Lastname from Users");
//            printWriter.println("<table border=2>");
//            printWriter.println("<tr>" + "<th>NAME</th>" + "<th>SURNAME</th>" + "</tr>");
//            while (resultSet.next()){
//
//                printWriter.println("<tr>" + "<th>"+resultSet.getString(1) + "</th>");
//                printWriter.println("<th>" + resultSet.getString(2) + "</th>" + "</tr>");
//            }
//            printWriter.println("</table>");
//            printWriter.println("</html>");
//        } catch (Exception e) {
//            System.out.println("Exception:"+e);
//        }
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        CurrentJsp page;
        CommandFactory factory = new CommandFactory();
        CommandAction command = factory.defineCommand(request);
        page = command.execute(request);

        boolean isRedirect = page.isRedirect();
        if (isRedirect) {
            redirect(page, request, response);
        } else {
            forward(page, request, response);
        }
    }

    private void redirect(CurrentJsp page, HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        String url = page.getPageUrl();
        response.sendRedirect(request.getContextPath() + url);
    }

    private void forward(CurrentJsp page, HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        String url = page.getPageUrl();
        String messageKey = page.getMessageKey();
        if (!NONE_MESSAGE_KEY.equals(messageKey)) {
            String message = MessageManager.getProperty(messageKey);
            request.setAttribute(MESSAGE_ATTRIBUTE, message);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
    }
}
