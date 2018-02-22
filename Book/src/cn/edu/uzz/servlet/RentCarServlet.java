package cn.edu.uzz.servlet;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.uzz.util.DBHelper;
import cn.edu.uzz.util.Push;
import net.sf.json.JSONObject;

public class RentCarServlet extends HttpServlet{
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
		String timeend=req.getParameter("timeend")+" 23:59:59";
		String picture=req.getParameter("picture");
		//String timeend="2017-12-3";
		
		String sql="select * FROM rentcar a WHERE a.account = '"+account+"' and a.bookname='"+bookname+"' and booktype='"+type+"' and bookid='"+id+"'";
		Connection conn;
		try {
			conn = DBHelper.getConnection();
			Statement statement;
		    statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);	
			
			if(rs.next()){
				data.put("resultCode", 3);
				data.put("describe", "用户已添加到借阅车");
				resp.getWriter().write(data.toString());	
			}else{
					statement.executeUpdate("insert into rentcar (account,bookname,nowdate,enddate,booktype,bookid,picture) values ('"+account
							+"','"+bookname+"',(now()),'"+timeend+"','"+type+"','"+id+"','"+picture+"')");
					
					//借书，修改书的借阅状态
					/*CallableStatement cs=conn.prepareCall("{call rentchange(?,?)}");
					cs.setInt(1, id);
					cs.setInt(2, type);
					cs.execute();*/
					
					data.put("resultCode", 1);
					data.put("describe", "添加到借阅车");
					resp.getWriter().write(data.toString());	
					
					/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dateString = timeend+" 24:00:00";
					Calendar calendar = Calendar.getInstance();
					long nowDate = calendar.getTime().getTime(); //Date.getTime() 获得毫秒型日期
					try {
					       long specialDate = sdf.parse(dateString).getTime();
					        betweenDate = (specialDate - nowDate); //计算间隔多少天，则除以毫秒到天的转换公式
					       
					} catch (Exception e) {
					         e.printStackTrace();
					}*/
					
					
					
					delete(bookname,account,id,type);		
					
					
					 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			data.put("resultCode", 2);
			data.put("describe", "出故障了");
			resp.getWriter().write(data.toString());	
		}
		
		
	}
	public static void delete(String bookname,String account,int id,int type){
		Timer t1= new Timer();
		t1.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Connection conn;
				try {
					conn = DBHelper.getConnection();
					Statement statement;
				    statement = conn.createStatement();
				    statement.executeUpdate("DELETE from rentcar where account='" + account + "' and bookname='" + bookname + "' and booktype='"+type+"' and bookid='"+id+"'");
				    Push.push(account, "借阅车书本已到期");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}, 1000*60*25);
		
	}

}
