package controller;

import dao.CategoryDAO;
import entity.Category;
import entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/home")
public class AdminHomeServlet extends HttpServlet {
	private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");
        String idStr = req.getParameter("id");

        if ("delete".equals(action) && idStr != null) {
            int id = Integer.parseInt(idStr);
            categoryDAO.delete(id, user.getId());
            resp.sendRedirect(req.getContextPath() + "/admin/home");
            return;
        } else if ("edit".equals(action) && idStr != null) {
            int id = Integer.parseInt(idStr);
            Category c = categoryDAO.getById(id, user.getId());
            req.setAttribute("category", c);
            req.getRequestDispatcher("/admin/home.jsp").forward(req, resp);
            return;
        }

        List<Category> list = categoryDAO.getByUserId(user.getId());
        req.setAttribute("categories", list);
        req.getRequestDispatcher("/admin/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String idStr = req.getParameter("id");
        String name = req.getParameter("name");

        if (idStr == null || idStr.isEmpty()) {
            // Add new
            Category c = new Category();
            c.setName(name);
            c.setUserId(user.getId());
            categoryDAO.insert(c);
        } else {
            // Update
            int id = Integer.parseInt(idStr);
            Category c = new Category();
            c.setId(id);
            c.setName(name);
            c.setUserId(user.getId());
            categoryDAO.update(c);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/home");
    }
}
