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

public class GetCarServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		String account=req.getParameter("account");
		ArrayList<Rent> rent=BooksDao.getCarBooks(account);
		Gson gson=new Gson();
		String list_json = gson.toJson(rent);	
		JSONObject data = new JSONObject();
		data.put("json", list_json);
		data.put("resultCode", 0);
		data.put("errorMsg", "用户收藏的书");
		resp.getWriter().write(data.toString());
	}

}
