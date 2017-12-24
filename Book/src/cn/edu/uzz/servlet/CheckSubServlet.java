package cn.edu.uzz.servlet;

import java.io.IOException;

import javax.imageio.event.IIOReadWarningListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.uzz.dao.BooksDao;
import net.sf.json.JSONObject;

public class CheckSubServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		JSONObject data = new JSONObject();
		String account=req.getParameter("account");
		String t=req.getParameter("booktype");
		int type=Integer.parseInt(t);
		String i=req.getParameter("bookid");
		int id=Integer.parseInt(i);
		BooksDao booksDao=new BooksDao();
		if(booksDao.checksub(account, type, id)){
			data.put("resultCode", 1);
			data.put("describe", "预定与借阅为同一用户");
			resp.getWriter().write(data.toString());
		}else{
			data.put("resultCode", 2);
			data.put("describe", "预定与借阅不为同一用户");
			resp.getWriter().write(data.toString());
		}
	}

}
