/**
 * 
 */
package cn.raoxianhua.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.raoxianhua.common.utils.DBUtils;

/**
 * @author raoxianhua
 *
 */
@WebServlet("/manager/staffhome")
public class StaffHomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Cookie[] cookies = req.getCookies();
		String sid = null;
		String spwd = null;
		HttpSession session = req.getSession();
		if (session.getAttribute("sid_in_session") == null) {
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					String id = cookies[i].getName();
					String pwd = cookies[i].getName();
					if ("sid".equals(id)) {
						// 如果是中文，cookies需要解码
						sid = URLDecoder.decode(cookies[i].getValue(), "utf-8");
					} else if ("spwd".equals(pwd)) {
						spwd = cookies[i].getValue();
					}
				}
			}
			// 当用户名和密码不为空时，自动登录
			if ((sid != null && !("".equals(sid))) && (spwd != null && !("".equals(spwd)))) {
				session.setAttribute("sid_in_session", sid);
				session.setAttribute("spwd_in_session", spwd);
				resp.sendRedirect("stafflogin");// get请求
			} else {
//				String sql = "SELECT * FROM STAFF";
//				List<Map<String, Object>> stafflist = DBUtils.query(sql);
//				req.setAttribute("stafflist", stafflist);
				//req.getRequestDispatcher("queryStaffList").forward(req, resp);
				req.getRequestDispatcher("staffindex.jsp").forward(req, resp);
			}
		} else {
			String sql1 = "SELECT * FROM STAFF";
			String sql2 = "SELECT * FROM CLIENT";
			List<Map<String, Object>> stafflist = DBUtils.query(sql1);
			List<Map<String, Object>> clientlist = DBUtils.query(sql2);
			req.setAttribute("stafflist", stafflist);
			req.setAttribute("clientlist", clientlist);
			//req.getRequestDispatcher("queryStaffList").forward(req, resp);
			req.getRequestDispatcher("staffindex.jsp").forward(req, resp);
		}

	}
}
