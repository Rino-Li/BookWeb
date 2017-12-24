package cn.edu.uzz.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import cn.edu.uzz.util.DBHelper;
import net.sf.json.JSONObject;

public class Reg_InforServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		
		 String user_id = req.getParameter("id");
		 String user_name = req.getParameter("name");
		 String user_sex = req.getParameter("sex");
		 String user_tel = req.getParameter("tel");
		 String user_age = req.getParameter("age");
		 String user_address = req.getParameter("address");
		 

		Connection conn;
		try {
			JSONObject data = new JSONObject();
			conn = DBHelper.getConnection();
			Statement statement = conn.createStatement();
			
			statement.executeUpdate("update user set name='"+user_name+"',usersex='"+user_sex+"',phone='"+user_tel+"',userage='"+user_age+"',addr='"+user_address+"' where username='"+user_id+"'");
			data.put("success", "success");
			resp.getWriter().write(data.toString());
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
