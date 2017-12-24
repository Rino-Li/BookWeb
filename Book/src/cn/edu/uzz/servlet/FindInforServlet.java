package cn.edu.uzz.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import cn.edu.uzz.util.DBHelper;
import net.sf.json.JSONObject;

public class FindInforServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		try {
			
			String account=req.getParameter("account");
			
			
			JSONObject data = new JSONObject();
			
			Connection conn= DBHelper.getConnection();
			Statement statement = conn.createStatement();
			String sql= "select username,usersex,phone,addr,userage,name FROM user a WHERE a.account ="+account;
			PreparedStatement stmt =conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				 String username = rs.getString(1);
				 String usersex = rs.getString(2);
				 String phone = rs.getString(3);
				 String addr = rs.getString(4);
				 String userage = rs.getString(5);
				 String name = rs.getString(6);
				 data.put("resultCode", 0);
				 data.put("username", username);
				 data.put("usersex", usersex);
				 data.put("phone", phone);
				 data.put("addr", addr);
				 data.put("userage", userage);
				 data.put("name", name);
				 resp.getWriter().write(data.toString());
			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
