package cn.edu.uzz.servlet;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edu.uzz.entity.User;
import cn.edu.uzz.util.DBHelper;
import cn.edu.uzz.util.Push;
import net.sf.json.JSONObject;

public class SubscribeServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		JSONObject data = new JSONObject();
		String account=req.getParameter("account");
		String iString=req.getParameter("bookid");
		int id=Integer.parseInt(iString);
		String tString=req.getParameter("booktype");
		int type=Integer.parseInt(tString);
		String bookname=req.getParameter("bookname");
		String picture=req.getParameter("picture");
		
		try {
			
			
			String sql="select * FROM subscribe a WHERE a.account = '"+account+"' and a.bookname='"+bookname+"' and a.booktype='"+type+"' and a.bookid='"+id+"'";
			Connection conn=DBHelper.getConnection();
			Statement statement;
		    statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);	
			if(rs.next()){
				data.put("resultCode", 1);
				data.put("describe", "用户已预定了该书");
				resp.getWriter().write(data.toString());
				
			}else{
					
					statement.executeUpdate("insert into subscribe (account,bookname,date,booktype,bookid,picture) values ('"+account
							+"','"+bookname+"',(now()),'"+type+"','"+id+"','"+picture+"')");
					
					//预定，修改状态
					CallableStatement cs=conn.prepareCall("{call subchange(?,?)}");
					cs.setInt(1, id);
					cs.setInt(2, type);
					cs.execute();
					
					deleteSub(bookname,account,id,type);
					data.put("resultCode", 2);
					data.put("describe", "预订成功，请在24小时之内借阅此书");
					resp.getWriter().write(data.toString());
					 
			}
			
			
			
			return;
			
			
			} catch (SQLException e) {
				data.put("resultCode", 3);
				data.put("describe", "出现错误！");
				resp.getWriter().write(data.toString());
				e.printStackTrace();
			} catch (Exception e) {
				data.put("resultCode", 3);
				data.put("describe", "出现错误！");
				resp.getWriter().write(data.toString());
				e.printStackTrace();
			}	
	}
	
	//用于取消预定
		public static void deleteSub(final String bookname,final String username,final int bookid,final int booktype){
			
			Timer t1= new Timer();
			t1.schedule(new TimerTask() {
				
				@Override
				public void run() {
					
				Connection conn;
					try {
						conn = DBHelper.getConnection();
						Statement statement;
					    statement = conn.createStatement();
					    statement.executeUpdate("DELETE from subscribe where account='" + username + "' and bookname='" + bookname + "' and booktype='"+booktype+"' and bookid='"+bookid+"'");
					    
					    CallableStatement cs=conn.prepareCall("{call calsubchange(?,?)}");
						cs.setInt(1, bookid);
						cs.setInt(2, booktype);
						cs.execute();
						Push.push(username, "您的预定已到期");
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			},1000*60*24*60);
		}
	

}
