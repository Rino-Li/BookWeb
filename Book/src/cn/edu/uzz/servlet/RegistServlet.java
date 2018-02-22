package cn.edu.uzz.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edu.uzz.util.DBHelper;
import net.sf.json.JSONObject;

public class RegistServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		
		String account=req.getParameter("account");
		String username=req.getParameter("username");
		String sex=req.getParameter("sex");
		String tel=req.getParameter("tel");
		String address=req.getParameter("address");
		int age=Integer.parseInt(req.getParameter("age"));
		String password=req.getParameter("password");
		String truthname=req.getParameter("truthname");
		
		try {
		JSONObject data = new JSONObject();
		String sql="select * FROM user a WHERE a.account = '"+account+"'";
		String sql2="insert into user (account,username,password,phone,addr,rdate,usersex,userage,name) values(?,?,?,?,?,(now()),?,?,?)";
		Connection conn=DBHelper.getConnection();
		Statement statement;
	    statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);	
		
		
		
		if(rs.next()){
			data.put("resultCode", 2);
			data.put("errorMsg", "");
			resp.getWriter().write(data.toString());
			return;
		}else{//ÓÃ»§Î´×¢²á
			PreparedStatement stmt;
			stmt=conn.prepareStatement(sql2);
			stmt.setString(1, account);
			stmt.setString(2, username);
			stmt.setString(3, password);
			stmt.setString(4, tel);
			stmt.setString(5, address);
			stmt.setString(6, sex);
			stmt.setInt(7, age);
			stmt.setString(8, truthname);
			int rs2=stmt.executeUpdate();
		}
		
		data.put("resultCode", 1);
		data.put("errorMsg", "");
		resp.getWriter().write(data.toString());
		return;
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
