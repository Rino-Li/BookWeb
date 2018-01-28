package cn.edu.uzz.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.edu.uzz.dao.BooksDao;
import cn.edu.uzz.entity.Books;
import net.sf.json.JSONObject;

public class SearchServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		JSONObject data = new JSONObject();
		String searchName=req.getParameter("searchName");
		BooksDao booksDao=new BooksDao();
		ArrayList<Books> list=booksDao.getSearch(searchName);
		Gson gson=new Gson();
		String list_json = gson.toJson(list);
		data.put("searchlist", list_json);
		data.put("describe", "用户搜索的书籍列表");
		resp.getWriter().write(data.toString());
	}

}
