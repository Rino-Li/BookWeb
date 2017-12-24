package cn.edu.uzz.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.uzz.util.DBHelper;
import net.sf.json.JSONObject;

public class LikeServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		String account=req.getParameter("account");
		String bookname=req.getParameter("bookname");
		String bookid=req.getParameter("bookid");
		String booktype=req.getParameter("booktype");
		String picture=req.getParameter("picture");
		
		JSONObject data = new JSONObject();
		String sql1 = "insert into likebook (account,bookname,bookid,booktype,picture) values ('"+account
				+"','"+bookname+"','"+bookid+"','"+booktype+"','"+picture+"')";
		
		
		
		try {
			Connection connection = DBHelper.getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql1);
			data.put("resultCode", 1);
			data.put("errorMsg", "");
			resp.getWriter().write(data.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			data.put("resultCode", 2);
			data.put("errorMsg", "");
			resp.getWriter().write(data.toString());
		}
		
		
		
		
		
		
	}

}
