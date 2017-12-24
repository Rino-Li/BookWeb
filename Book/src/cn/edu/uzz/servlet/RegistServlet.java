package cn.edu.uzz.servlet;

import java.io.IOException;
import java.sql.Connection;
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
		
		try {
		String usrAccount=req.getParameter("account");
		String usrName=req.getParameter("username");
		String usrPwd=req.getParameter("password");
		JSONObject data = new JSONObject();
		String sql="select * FROM user a WHERE a.account = '"+usrAccount+"'";
		
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
				statement.executeUpdate("insert into user (username,password,rdate,account) values ('"+usrName
						+"','"+usrPwd+"',(now()),'"+usrAccount+"')");
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
