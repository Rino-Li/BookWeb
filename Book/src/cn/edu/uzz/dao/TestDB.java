package cn.edu.uzz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.uzz.util.DBHelper;

public class TestDB {
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	Timer timer=new Timer();
	public TestDB() {
		// TODO Auto-generated constructor stub
		try {
			conn = DBHelper.getConnection();
			String sql="select * from book0";
			stmt = conn.prepareStatement(sql);
			timer.schedule(new TimerTask() {			
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						rs = stmt.executeQuery();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, 2000, 1000*60*60*6);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
