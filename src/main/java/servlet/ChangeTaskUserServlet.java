package servlet;

import manager.TaskManager;
import manager.UserManager;
import model.User;
import model.UserType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/changeTaskUser")
public class ChangeTaskUserServlet extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        int userId = Integer.parseInt(req.getParameter("userId"));
        String taskUser = req.getParameter("user");

        TaskManager taskManager = new TaskManager();
        taskManager.updateTaskUser(userId,taskUser);
//        UserManager userManager = new UserManager();
//        userManager.updateTaskUser(userId,taskUser);
        if(user.getType() == UserType.MANAGER){
            resp.sendRedirect("/managerHome");
        }else {
            resp.sendRedirect("/userHome");
        }
    }
}
