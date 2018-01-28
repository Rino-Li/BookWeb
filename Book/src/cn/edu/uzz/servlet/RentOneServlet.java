package cn.edu.uzz.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.New;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


import com.google.gson.Gson;

import cn.edu.uzz.dao.BooksDao;
import cn.edu.uzz.entity.RentCar;

public class RentOneServlet extends HttpServlet{
	private JSONArray jsonArray;
	private ArrayList<Integer> rs=new ArrayList<Integer>();
	private int error;
	List<RentCar> books=new ArrayList<RentCar>();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		JSONObject data = new JSONObject();
		String result=req.getParameter("result");
		List<RentCar> car=getarray(result);
		BooksDao booksDao=new BooksDao();
		rs.clear();
		for(int i=0;i<car.size();i++){
			RentCar cari=car.get(i);
			System.out.println("传入的id为"+cari.getBookid());
			rs.add(booksDao.rentone(cari.getBooktype(), cari.getBookid()));
		}
		error=selectError();
		
		try {
			data.put("rentLength", rs.size());
			data.put("errorNum", error);
			data.put("describe", "借阅结果");
			resp.getWriter().write(data.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//result如果为0，说明这本书被提前借阅了，一般不大可能，因为这本书是用户拿给管理员的，如果被借阅了，怎么可能拿在手中呢
		//如果为1，说明插入正在借阅表失败
		//如果为2，说明删除借阅车失败
		//如果为3，说明用户在管理员确认之前删除了借阅车
		//为4，成功
	}
	
	private int selectError() {
		int errorNum=0;
		for(int i=0;i<rs.size();i++){
			System.out.println("第"+i+"本借阅结果为"+rs.get(i));
			if(rs.get(i)==1||rs.get(i)==2||rs.get(i)==3||rs.get(i)==0){
				errorNum++;
			}
		}
		System.out.println("error is"+errorNum);
		return errorNum;
	}
	private List<RentCar> getarray(String result){
		JSONArray jsonArray=JSONArray.fromObject(result);
		List<RentCar> books=(List)JSONArray.toList(jsonArray, RentCar.class);
		return books;
	}

}
