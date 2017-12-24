package cn.edu.uzz.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.uzz.dao.BooksDao;
import net.sf.json.JSONObject;

public class CancelSubServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		JSONObject data = new JSONObject();
		String account=req.getParameter("account");
		String idString=req.getParameter("bookid");
		int id=Integer.parseInt(idString);
		String typeString=req.getParameter("booktype");
		int type=Integer.parseInt(typeString);
		
		BooksDao booksDao=new BooksDao();
		int re=booksDao.cancelsub(account, type, id);
		if (re==1) {
			data.put("resultCode", 1);
			data.put("describe", "取消预定成功");
			resp.getWriter().write(data.toString());
		}else if (re==2) {
			data.put("resultCode", 2);
			data.put("describe", "取消预定失败");
			resp.getWriter().write(data.toString());
		}else if (re==3) {
			data.put("resultCode", 3);
			data.put("describe", "没有预定记录，需要变成未预定状态");
			resp.getWriter().write(data.toString());
		}
	}

}
