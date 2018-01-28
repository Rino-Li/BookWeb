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
			System.out.println("�����idΪ"+cari.getBookid());
			rs.add(booksDao.rentone(cari.getBooktype(), cari.getBookid()));
		}
		error=selectError();
		
		try {
			data.put("rentLength", rs.size());
			data.put("errorNum", error);
			data.put("describe", "���Ľ��");
			resp.getWriter().write(data.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//result���Ϊ0��˵���Ȿ�鱻��ǰ�����ˣ�һ�㲻����ܣ���Ϊ�Ȿ�����û��ø�����Ա�ģ�����������ˣ���ô��������������
		//���Ϊ1��˵���������ڽ��ı�ʧ��
		//���Ϊ2��˵��ɾ�����ĳ�ʧ��
		//���Ϊ3��˵���û��ڹ���Աȷ��֮ǰɾ���˽��ĳ�
		//Ϊ4���ɹ�
	}
	
	private int selectError() {
		int errorNum=0;
		for(int i=0;i<rs.size();i++){
			System.out.println("��"+i+"�����Ľ��Ϊ"+rs.get(i));
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
