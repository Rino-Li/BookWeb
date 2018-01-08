package cn.edu.uzz.servlet;

import java.awt.List;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.uzz.dao.BooksDao;
import cn.edu.uzz.entity.Rent;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RentOneServlet extends HttpServlet{
	int[] type=new int[10];
	int[] id=new int[10];
	private JSONArray jsonArray;
	private int rs;
	private int error;
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		JSONObject data = new JSONObject();
		String result=req.getParameter("result");
		getarray(result);
		BooksDao booksDao=new BooksDao();
		for(int i=0;i<type.length;i++){
			rs=booksDao.rentone(type[i], id[i]);
			//error是借阅失败的个数
			error=booksDao.geterror();
		}
		data.put("resultCode", rs);
		data.put("errorNum", error);
		data.put("describe", "借阅结果");
		resp.getWriter().write(data.toString());
		//result如果为0，说明这本书被提前借阅了，一般不大可能，因为这本书是用户拿给管理员的，如果呗借阅了，怎么可能拿在手中呢
		//如果为1，说明插入正在借阅表失败
		//如果为2，说明删除借阅车失败
		//如果为3，说明用户在管理员确认之前删除了借阅车
		//为4，成功
	}
	
	private void getarray(String result){
		JSONObject jsonObject;
		jsonObject=JSONObject.fromObject(result);
		jsonArray=jsonObject.getJSONArray("json");
		for(int i=0;i<jsonArray.size();i++){
			jsonObject=jsonArray.getJSONObject(i);
			type[i]=jsonObject.getInt("booktype");
			id[i]=jsonObject.getInt("bookid");
		}
	}

}
