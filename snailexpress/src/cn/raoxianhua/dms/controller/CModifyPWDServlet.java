/**
 * 
 */
package cn.raoxianhua.dms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.raoxianhua.dms.service.IClientService;
import cn.raoxianhua.dms.service.impl.ClientServiceImpl;

/**
 * @author raoxianhua
 *
 */
@WebServlet("/doModifyPWD")
public class CModifyPWDServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IClientService ClientService = new ClientServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		req.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession();
		String cid = (String) session.getAttribute("cid_in_session");
		String cpwd = req.getParameter("cpwd");
		String ncpwd = req.getParameter("ncpwd");
		
		int modifypwd = ClientService.doModifyPWD(cid,cpwd,ncpwd);
		
		String msg = "";
		if (modifypwd > 0) {
			msg = "密码修改成功！";
			session.setAttribute("msg", msg);
			// 1. 请求重定向
			resp.sendRedirect("settings");
			// 2. 请求转发
			// RequestDispatcher dispatcher = ç.getRequestDispatcher("index.html");
			// dispatcher.forward(request, response);
		} else {
			msg = "密码修改失败！";
			session.setAttribute("msg", msg);
			resp.sendRedirect(req.getHeader("Referer"));
		}
		
	}
	
}
