package cn.edu.uzz.dao;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.enterprise.inject.New;

import cn.edu.uzz.entity.Rent;

public class StartCount {
	long betweenDate;
	Timer timer= new Timer();
	public void startCount(Rent rent){
		String endDate=rent.getEnddate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = endDate+" 6:00:00";
		System.out.println(dateString);
		Calendar calendar = Calendar.getInstance();
		long nowDate = calendar.getTime().getTime();//Date.getTime() 获得毫秒型日期
		try {
			long specialDate = sdf.parse(dateString).getTime();
			betweenDate = (specialDate - nowDate); //计算间隔多少天，则除以毫秒到天的转换公式
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("时间间隔为"+betweenDate);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				BooksDao booksDao=new BooksDao();
				booksDao.deleteRent(rent);
				
			}
		}, betweenDate);
	}

}
