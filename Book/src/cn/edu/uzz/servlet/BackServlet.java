package cn.edu.uzz.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.uzz.dao.BooksDao;
import net.sf.json.JSONObject;

public class BackServlet extends HttpServlet{
	Date d1;
	Date d2;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		JSONObject data = new JSONObject();
		int booktype=Integer.parseInt(req.getParameter("booktype"));
		int bookid=Integer.parseInt(req.getParameter("bookid"));
		BooksDao booksDao=new BooksDao();
		String enddate=booksDao.getEnddate(booktype, bookid);
		if(enddate.equals("nonono")){
			data.put("resultCode", 1);
			data.put("describe", "该书没有被借阅");
			resp.getWriter().write(data.toString());
			return;
		}
		//获取本应该还书的时间，与现在的时间进行比较，判断是否超出之前设置的时间
		int result=booksDao.backBook(booktype, bookid);
		//结果为1，说明此书没有被借阅
		//结果为2，说明sql出问题了
		//结果为3，说明成功了
		Date now1=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String now=df.format(now1);
		try {
			d1 = df.parse(now);
			d2=df.parse(enddate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(d1.getTime()>d2.getTime()){
			result=4;
			//说明用户借书超期
			long l=d1.getTime()-d2.getTime();     
			long day=l/(24*60*60*1000);     
			long hour=(l/(60*60*1000)-day*24);     
			long min=((l/(60*1000))-day*24*60-hour*60);     
			long s=(l/1000-day*24*60*60-hour*60*60-min*60);     
			System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");   
			data.put("resultCode", result);
			data.put("describe", "还书结果");
			data.put("time",""+day+"天"+hour+"小时"+min+"分"+s+"秒");
			resp.getWriter().write(data.toString());
		}else{
			data.put("resultCode", result);
			data.put("describe", "还书结果");
			resp.getWriter().write(data.toString());
		}
	}
	
}
