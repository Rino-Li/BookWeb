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
		long nowDate = calendar.getTime().getTime();//Date.getTime() ��ú���������
		try {
			long specialDate = sdf.parse(dateString).getTime();
			betweenDate = (specialDate - nowDate); //�����������죬����Ժ��뵽���ת����ʽ
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ʱ����Ϊ"+betweenDate);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				BooksDao booksDao=new BooksDao();
				booksDao.deleteRent(rent);
				
			}
		}, betweenDate);
	}

}
