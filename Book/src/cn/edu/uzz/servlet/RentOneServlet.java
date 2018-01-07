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
		data.put("describe", "���Ľ��");
		resp.getWriter().write(data.toString());
		//result���Ϊ0��˵���Ȿ�鱻��ǰ�����ˣ�һ�㲻����ܣ���Ϊ�Ȿ�����û��ø�����Ա�ģ�����½����ˣ���ô��������������
		//���Ϊ1��˵���������ڽ��ı�ʧ��
		//���Ϊ2��˵��ɾ�����ĳ�ʧ��
		//���Ϊ3��˵���û��ڹ���Աȷ��֮ǰɾ���˽��ĳ�
		//Ϊ4���ɹ�
	}

}
