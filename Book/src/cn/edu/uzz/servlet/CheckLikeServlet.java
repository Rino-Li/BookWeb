package cn.edu.uzz.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.uzz.dao.BooksDao;
import net.sf.json.JSONObject;


public class CheckLikeServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		JSONObject data = new JSONObject();
		String account=req.getParameter("account");
		String t=req.getParameter("type");
		String i=req.getParameter("id");
		int type=Integer.parseInt(t);
		int id=Integer.parseInt(i);
		BooksDao booksDao=new BooksDao();
		int re=booksDao.checklike(account, type, id);
		String substatus=booksDao.checksubscribe(account, type, id);
		String rentstatus=booksDao.checkrent(account, type, id);
		if (re==1) {
			data.put("resultCode", 1);
			data.put("describe", "用户收藏了该书");
			data.put("subscribe", substatus);
			data.put("rentstatus", rentstatus);
			resp.getWriter().write(data.toString());
		}else {
			data.put("resultCode", 2);
			data.put("describe", "用户未收藏该书");
			data.put("subscribe", substatus);
			data.put("rentstatus", rentstatus);
			resp.getWriter().write(data.toString());
		}
		
		
	}
	

}
