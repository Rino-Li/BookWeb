package cn.edu.uzz.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.edu.uzz.dao.BooksDao;
import cn.edu.uzz.entity.Rent;
import net.sf.json.JSONObject;

public class GetRentingServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		JSONObject data = new JSONObject();
		String account=req.getParameter("account");
		BooksDao booksDao=new BooksDao();
		ArrayList<Rent> rent=BooksDao.getRentingBooks(account);
		Gson gson=new Gson();
		String list_json = gson.toJson(rent);	
		data.put("json", list_json);
		data.put("resultCode", 0);
		data.put("errorMsg", "用户正在借阅的书");
		resp.getWriter().write(data.toString());
	}

}
