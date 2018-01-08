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
			//error�ǽ���ʧ�ܵĸ���
			error=booksDao.geterror();
		}
		data.put("resultCode", rs);
		data.put("errorNum", error);
		data.put("describe", "���Ľ��");
		resp.getWriter().write(data.toString());
		//result���Ϊ0��˵���Ȿ�鱻��ǰ�����ˣ�һ�㲻����ܣ���Ϊ�Ȿ�����û��ø�����Ա�ģ�����½����ˣ���ô��������������
		//���Ϊ1��˵���������ڽ��ı�ʧ��
		//���Ϊ2��˵��ɾ�����ĳ�ʧ��
		//���Ϊ3��˵���û��ڹ���Աȷ��֮ǰɾ���˽��ĳ�
		//Ϊ4���ɹ�
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
