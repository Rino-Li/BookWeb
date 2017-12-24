package cn.edu.uzz.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import cn.edu.uzz.dao.BooksDao;
import cn.edu.uzz.entity.Books;
import cn.edu.uzz.util.DBHelper;
import net.sf.json.JSONObject;

public class OldGetBookList extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		String typeString1=req.getParameter("type1");
		String a=req.getParameter("a");
		int type1=Integer.parseInt(typeString1);
		ArrayList<Books> list=BooksDao.getAllBooks(type1);
		Gson gson=new Gson();
		String list_json = gson.toJson(list);	
		JSONObject data = new JSONObject();
		data.put("json", list_json);
		data.put("resultCode", 0);
		data.put("errorMsg", "网络错误，请重新获取!");
		resp.getWriter().write(data.toString());
		
	}

}
