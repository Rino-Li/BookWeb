package cn.edu.uzz.servlet;

import java.io.IOException;

import javax.imageio.event.IIOReadWarningListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.uzz.dao.BooksDao;
import net.sf.json.JSONObject;

public class RentOneServlet extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		JSONObject data = new JSONObject();
		int id=Integer.parseInt(req.getParameter("bookid"));
		int type=Integer.parseInt(req.getParameter("booktype"));
		BooksDao booksDao=new BooksDao();
		int result=booksDao.rentone(type, id);
		data.put("resultCode", result);
		data.put("describe", "借阅结果");
		resp.getWriter().write(data.toString());
		//result如果为0，说明这本书被提前借阅了，一般不大可能，因为这本书是用户拿给管理员的，如果呗借阅了，怎么可能拿在手中呢
		//如果为1，说明插入正在借阅表失败
		//如果为2，说明删除借阅车失败
		//如果为3，说明用户在管理员确认之前删除了借阅车
		//为4，成功
	}

}
