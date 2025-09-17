package filter;

import entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String uri = req.getRequestURI();

		if (uri.endsWith("login") || uri.endsWith("login.jsp")) {
			chain.doFilter(request, response);
			return;
		}

		HttpSession session = req.getSession(false);
		User user = (session != null) ? (User) session.getAttribute("user") : null;

		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login.jsp");
			return;
		}

		if (uri.contains("/user/") && user.getRoleId() != 1) {
			resp.sendRedirect(req.getContextPath() + "/login.jsp");
			return;
		}
		if (uri.contains("/manager/") && user.getRoleId() != 2) {
			resp.sendRedirect(req.getContextPath() + "/login.jsp");
			return;
		}
		if (uri.contains("/admin/") && user.getRoleId() != 3) {
			resp.sendRedirect(req.getContextPath() + "/login.jsp");
			return;
		}

		chain.doFilter(request, response);
	}
}
