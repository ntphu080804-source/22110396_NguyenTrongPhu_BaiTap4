package controller;
import dao.CategoryDAO;
import entity.Category;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
	private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        switch (action) {
            case "add":
                String name = req.getParameter("name");
                categoryDAO.insert(new Category(0, name, user.getId()));
                break;

            case "update":
                int idU = Integer.parseInt(req.getParameter("id"));
                String newName = req.getParameter("name");
                categoryDAO.update(new Category(idU, newName, user.getId()));
                break;

            case "delete":
                int idD = Integer.parseInt(req.getParameter("id"));
                categoryDAO.delete(idD, user.getId());
                break;
        }

        // Sau khi thao tác → quay lại trang home theo role
        if (user.getRoleId() == 1) { // user
            resp.sendRedirect(req.getContextPath() + "/user/home");
        } else if (user.getRoleId() == 2) { // manager
            resp.sendRedirect(req.getContextPath() + "/manager/home");
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/home");
        }
    }
}
