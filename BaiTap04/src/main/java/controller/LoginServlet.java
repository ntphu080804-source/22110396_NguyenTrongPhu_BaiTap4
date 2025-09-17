package controller;

import dao.UserDAO;
import entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private UserDAO userDAO = new UserDAO();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		User user = userDAO.login(username, password);
		if (user != null) {
		    HttpSession session = req.getSession();
		    session.setAttribute("user", user);

		    int roleId = user.getRoleId();
		    if (roleId == 1) {
		        resp.sendRedirect(req.getContextPath() + "/user/home");
		    } else if (roleId == 2) {
		        resp.sendRedirect(req.getContextPath() + "/manager/home");
		    } else if (roleId == 3) {
		        resp.sendRedirect(req.getContextPath() + "/admin/home");
		    }
		} else {
		    req.setAttribute("error", "Sai tài khoản hoặc mật khẩu");
		    req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}

	}
}
