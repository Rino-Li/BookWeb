package cn.edu.uzz.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.uzz.dao.BooksDao;
import cn.edu.uzz.entity.Books;
import cn.edu.uzz.entity.Like;
import net.sf.json.JSONObject;

public class GetBookServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		String account=req.getParameter("account");
		String t=req.getParameter("booktype");
		String i=req.getParameter("bookid");
		int type=Integer.parseInt(t);
		int id=Integer.parseInt(i);
		BooksDao booksDao=new BooksDao();
		Books books=booksDao.getoneBooks(type, id);
		JSONObject data = new JSONObject();
		data.put("books", books);
		data.put("resultCode", 0);
		data.put("errorMsg", "用户收藏的书");
		resp.getWriter().write(data.toString());
	}
}
