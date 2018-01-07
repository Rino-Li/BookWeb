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

public class LoginServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			this.doGet(request, response);
		
	}
	
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		String usrName = req.getParameter("username");
		String usrPwd = req.getParameter("password");
	
	try {
		
		ResultSet rs;

		Connection connection = DBHelper.getConnection();
		String sql1 = "select * FROM user b WHERE b.account = '" + usrName + "'";
		
		Statement statement = connection.createStatement();
		
		rs = statement.executeQuery(sql1);
		
		JSONObject data = new JSONObject();
		
		if (!rs.next()) {//没有查询到 数据
			data.put("resultCode", 2);
			data.put("errorMsg", "");
			resp.getWriter().write(data.toString());
			return;
			
		} else {// 查询到数据
			String pwd = rs.getString("password");
			if (pwd.equals(usrPwd)) {
				
				HttpSession session=req.getSession();
				
				session.setAttribute("username",usrName);
				data.put("resultCode", 3);
				data.put("errorMsg", "");
				resp.getWriter().write(data.toString());
				return;
			} else {
				data.put("resultCode", 4);
				data.put("errorMsg", "");
				resp.getWriter().write(data.toString());
				return;
			}
		}

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	

}
