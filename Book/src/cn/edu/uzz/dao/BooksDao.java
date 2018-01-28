package cn.edu.uzz.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.uzz.entity.Books;
import cn.edu.uzz.entity.Like;
import cn.edu.uzz.entity.Rent;
import cn.edu.uzz.entity.Subscribe;
import cn.edu.uzz.util.DBHelper;

public class BooksDao {
	
	private String status;
	
	// 获得所有的图书信息
		public static ArrayList<Books> getAllBooks(int type1) {
			String tablename = null;
			switch (type1) {
			case 0:
				tablename="book0";
				break;
			case 1:
				tablename="book1";
				break;
			case 2:
				tablename="book2";
				break;
			case 3:
				tablename="book3";
				break;
			case 4:
				tablename="book4";
				break;
			case 5:
				tablename="book5";
				break;
			case 6:
				tablename="book6";
				break;
			case 7:
				tablename="book7";
				break;
			case 8:
				tablename="book8";
				break;
			case 9:
				tablename="book9";
				break;
			case 10:
				tablename="book10";
				break;
			default:
				break;
			}
			
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			ArrayList<Books> list = new ArrayList<Books>(); // 商品集合
			TestDB testDB=new TestDB();
			try {
				conn = DBHelper.getConnection();
				String sql = "select * from "+tablename+";"; // SQL语句
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				while (rs.next()) {
					Books book = new Books();
					book.setId(rs.getInt("id"));
					book.setName(rs.getString("name"));
					book.setPublish(rs.getString("publish"));
					book.setVersion(rs.getString("version"));
					book.setPicture(rs.getString("picture"));
					book.setWriter(rs.getString("writer"));
					book.setRentstatus(rs.getString("rentstatus"));
					book.setSubstatus(rs.getString("substatus"));
					book.setPrice(rs.getString("price"));
					book.setTime(rs.getString("time"));
					book.setISBN(rs.getString("ISBN"));
					book.setType(rs.getInt("type"));
					list.add(book);// 把一个商品加入集合
				}
				return list; // 返回集合。
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			} finally {
				// 释放数据集对象
				if (rs != null) {
					try {
						rs.close();
						rs = null;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				// 释放语句对象
				if (stmt != null) {
					try {
						stmt.close();
						stmt = null;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}

		}
		
		//检测用户是否收藏此书
		public  int checklike(String account,int type,int id){
			String sql="select * FROM likebook a WHERE a.account = '"+account+"' and a.booktype='"+type+"' and a.bookid='"+id+"'";
			Connection conn;
			try {
				conn = DBHelper.getConnection();
				Statement statement;
			    statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(sql);	
				if(rs.next()){
					return 1;
				}else{
					return 2;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 2;
			
		}
		
		//取消收藏
		public  int cancellike(String account,int type,int id){
			
			
			try {
				String sql = "select * FROM likebook a WHERE a.account = '"+account+"' and a.booktype='"+type+"' and a.bookid='"+id+"'";

				Connection conn = DBHelper.getConnection();
				Statement statement;
				statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()){
					statement.executeUpdate("DELETE from likebook where account='" + account + "' and booktype='" + type + "' and bookid='"+id+"'");
					return 1;//删除成功
				}else{
					return 3;//没有收藏记录
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return 2;//出问题
			}
		}
		
		public  int cancelsub(String account,int type,int id){
			
			
			try {
				String sql = "select * FROM subscribe a WHERE a.account = '"+account+"' and a.booktype='"+type+"' and a.bookid='"+id+"'";

				Connection conn = DBHelper.getConnection();
				Statement statement;
				statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()){
					statement.executeUpdate("DELETE from subscribe where account='" + account + "' and booktype='" + type + "' and bookid='"+id+"'");
					CallableStatement cs=conn.prepareCall("{call calsubchange(?,?)}");
					cs.setInt(1, id);
					cs.setInt(2, type);
					cs.execute();
					return 1;//删除成功
				}else{	
					return 3;//没有收藏记录
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return 2;//出问题
			}
		}
		
		
		
			public  String  checksubscribe(String account,int type,int id){
				String tablename = null;
				switch (type) {
				case 0:
					tablename="book0";
					break;
				case 1:
					tablename="book1";
					break;
				case 2:
					tablename="book2";
					break;
				case 3:
					tablename="book3";
					break;
				case 4:
					tablename="book4";
					break;
				case 5:
					tablename="book5";
					break;
				case 6:
					tablename="book6";
					break;
				case 7:
					tablename="book7";
					break;
				case 8:
					tablename="book8";
					break;
				case 9:
					tablename="book9";
					break;
				case 10:
					tablename="book10";
					break;
				default:
					break;
				}
			try {
				String sql = "select substatus FROM "+tablename+" a WHERE a.id = "+id+"";

				Connection conn = DBHelper.getConnection();
				Statement statement;
				statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()){
					String substatus=rs.getString(1);
					return substatus;
				}else{
					return "预定";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return "预定 ";
			}
		}
		
			public  String  checkrent(String account,int type,int id){
				String tablename = null;
				switch (type) {
				case 0:
					tablename="book0";
					break;
				case 1:
					tablename="book1";
					break;
				case 2:
					tablename="book2";
					break;
				case 3:
					tablename="book3";
					break;
				case 4:
					tablename="book4";
					break;
				case 5:
					tablename="book5";
					break;
				case 6:
					tablename="book6";
					break;
				case 7:
					tablename="book7";
					break;
				case 8:
					tablename="book8";
					break;
				case 9:
					tablename="book9";
					break;
				case 10:
					tablename="book10";
					break;
				default:
					break;
				}
			try {
				String sql = "select rentstatus FROM "+tablename+" a WHERE a.id = "+id+"";

				Connection conn = DBHelper.getConnection();
				Statement statement;
				statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()){
					String rentstatus=rs.getString(1);
					if(rentstatus.equals("待借阅")){
						return "借阅";
					}else{
					return rentstatus;
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return "借阅";
			}
			return "借阅";
		}
			
			//检测预定与借阅是否为同一个用户
			public  boolean checksub(String account,int type,int id){
				String sql="select account FROM subscribe a WHERE a.booktype='"+type+"' and a.bookid='"+id+"'";
				Connection conn;
				try {
					conn = DBHelper.getConnection();
					Statement statement;
				    statement = conn.createStatement();
					ResultSet rs = statement.executeQuery(sql);	
					if(rs.next()){
						String subuser=rs.getString(1);
						if (subuser.equals(account)) {
							return true;
						}else{
							return false;
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				return true;
				
			}
			
			
			public static ArrayList<Like> getLikeBooks(String account) {
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				ArrayList<Like> list = new ArrayList<Like>(); // 商品集合
				try {
					conn = DBHelper.getConnection();
					String sql = "select * from likebook where account='"+account+"';"; // SQL语句
					stmt = conn.prepareStatement(sql);
					rs = stmt.executeQuery();
					while (rs.next()) {
						Like like=new Like();
						like.setAccount(account);
						like.setBookname(rs.getString("bookname"));
						like.setBooktype(rs.getInt("booktype"));
						like.setBookid(rs.getInt("bookid"));
						like.setPicture(rs.getString("picture"));
						list.add(like);// 把一个商品加入集合
					}
					return list; // 返回集合。
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				} finally {
					// 释放数据集对象
					if (rs != null) {
						try {
							rs.close();
							rs = null;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					// 释放语句对象
					if (stmt != null) {
						try {
							stmt.close();
							stmt = null;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}

			}
		
			public static Books getoneBooks(int type,int id) {
				String tablename = null;
				switch (type) {
				case 0:
					tablename="book0";
					break;
				case 1:
					tablename="book1";
					break;
				case 2:
					tablename="book2";
					break;
				case 3:
					tablename="book3";
					break;
				case 4:
					tablename="book4";
					break;
				case 5:
					tablename="book5";
					break;
				case 6:
					tablename="book6";
					break;
				case 7:
					tablename="book7";
					break;
				case 8:
					tablename="book8";
					break;
				case 9:
					tablename="book9";
					break;
				case 10:
					tablename="book10";
					break;
				default:
					break;
				}
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				Books book=new Books();
				try {
					conn = DBHelper.getConnection();
					String sql = "select * from "+tablename+" where id='"+id+"';"; // SQL语句
					stmt = conn.prepareStatement(sql);
					rs = stmt.executeQuery();
					while (rs.next()) {
						book.setId(rs.getInt("id"));
						book.setName(rs.getString("name"));
						book.setPublish(rs.getString("publish"));
						book.setVersion(rs.getString("version"));
						book.setPicture(rs.getString("picture"));
						book.setWriter(rs.getString("writer"));
						book.setRentstatus(rs.getString("rentstatus"));
						book.setSubstatus(rs.getString("substatus"));
						book.setPrice(rs.getString("price"));
						book.setTime(rs.getString("time"));
						book.setISBN(rs.getString("ISBN"));
						book.setType(rs.getInt("type"));
					}
					return book; // 返回集合。
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				} finally {
					// 释放数据集对象
					if (rs != null) {
						try {
							rs.close();
							rs = null;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					// 释放语句对象
					if (stmt != null) {
						try {
							stmt.close();
							stmt = null;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}

			}
			public static ArrayList<Subscribe> getSubBooks(String account) {
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				ArrayList<Subscribe> list = new ArrayList<Subscribe>(); // 商品集合
				try {
					conn = DBHelper.getConnection();
					String sql = "select * from subscribe where account='"+account+"';"; // SQL语句
					stmt = conn.prepareStatement(sql);
					rs = stmt.executeQuery();
					while (rs.next()) {
						Subscribe subscribe=new Subscribe();
						subscribe.setAccount(account);
						subscribe.setBookid(rs.getInt("bookid"));
						subscribe.setBooktype(rs.getInt("booktype"));
						subscribe.setBookname(rs.getString("bookname"));
						subscribe.setPicture(rs.getString("picture"));
						list.add(subscribe);// 把一个商品加入集合
					}
					return list; // 返回集合。
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				} finally {
					// 释放数据集对象
					if (rs != null) {
						try {
							rs.close();
							rs = null;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					// 释放语句对象
					if (stmt != null) {
						try {
							stmt.close();
							stmt = null;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}

			}
			public static ArrayList<Rent> getCarBooks(String account) {
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				ArrayList<Rent> list = new ArrayList<Rent>(); // 商品集合
				try {
					conn = DBHelper.getConnection();
					String sql = "select * from rentcar where account='"+account+"';"; // SQL语句
					stmt = conn.prepareStatement(sql);
					rs = stmt.executeQuery();
					while (rs.next()) {
						Rent rent=new Rent();
						rent.setAccount(account);
						rent.setBookid(rs.getInt("bookid"));
						rent.setBooktype(rs.getInt("booktype"));
						rent.setBookname(rs.getString("bookname"));
						rent.setPicture(rs.getString("picture"));
						rent.setNowdate(rs.getString("nowdate"));
						rent.setEnddate(rs.getString("enddate"));
						list.add(rent);// 把一个商品加入集合
					}
					return list; // 返回集合。
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				} finally {
					// 释放数据集对象
					if (rs != null) {
						try {
							rs.close();
							rs = null;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					// 释放语句对象
					if (stmt != null) {
						try {
							stmt.close();
							stmt = null;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}

			}
	public  int cancelcar(String account,int type,int id){
				
				
				try {
					String sql = "select * FROM rentcar a WHERE a.account = '"+account+"' and a.booktype='"+type+"' and a.bookid='"+id+"'";
					Connection conn = DBHelper.getConnection();
					Statement statement;
					statement = conn.createStatement();
					ResultSet rs = statement.executeQuery(sql);
					if(rs.next()){
						statement.executeUpdate("DELETE from rentcar where account='" + account + "' and booktype='" + type + "' and bookid='"+id+"'");
						return 1;//删除成功
					}else{
						return 3;//没有借阅记录
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					return 2;//出问题
				}
			}
	
	//借一本书的方法1.先检查书的 状态  2.进行增删改查
	public int rentone(int booktype,int bookid){
		String tablename = null;
		switch (booktype) {
		case 0:
			tablename="book0";
			break;
		case 1:
			tablename="book1";
			break;
		case 2:
			tablename="book2";
			break;
		case 3:
			tablename="book3";
			break;
		case 4:
			tablename="book4";
			break;
		case 5:
			tablename="book5";
			break;
		case 6:
			tablename="book6";
			break;
		case 7:
			tablename="book7";
			break;
		case 8:
			tablename="book8";
			break;
		case 9:
			tablename="book9";
			break;
		case 10:
			tablename="book10";
			break;
		default:
			break;
		}
		String sql = "select rentstatus from "+tablename+" where id="+bookid+";";
		Connection conn;
		PreparedStatement statement;
		Rent rent=new Rent();
		try {
			conn = DBHelper.getConnection();
			statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			if(rs.next()){
				status=rs.getString(1);
			}else{
				System.out.println("diyigefangfa");
				return 3;
			}
			if (status.equals("已借阅")) {
				return 0;
			}else{
				String sql2="select * from rentcar where bookid='"+bookid+"' and booktype='"+booktype+"' ;";
				statement = conn.prepareStatement(sql2);
				rs=statement.executeQuery(sql2);
				if(!rs.next()){
					System.out.println("这本书被用户取消借阅了");
					return 3;
				}else{
					System.out.println("yunxing");
					rent.setAccount(rs.getString("account"));
					rent.setBookname(rs.getString("bookname"));
					rent.setBooktype(rs.getInt("booktype"));
					rent.setBookid(rs.getInt("bookid"));
					rent.setEnddate(rs.getString("enddate"));
					rent.setPicture(rs.getString("picture"));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return startrentone(rent);
	}
	public int startrentone(Rent rent){
		System.out.println(rent.getPicture()+"   picture的名字");
		String sql = "insert into rentbook (bookname,account,enddate,nowdate,booktype,bookid,picture) values(?,?,?,(now()),?,?,?)";
		Connection conn;
		PreparedStatement statement;
		try {
			conn = DBHelper.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setString(1, rent.getBookname());
			statement.setString(2, rent.getAccount());
			statement.setString(3, rent.getEnddate());
			statement.setInt(4, rent.getBooktype());
			statement.setInt(5, rent.getBookid());
			statement.setString(6, rent.getPicture());
			int rs = statement.executeUpdate();
			System.out.println("rs的值是："+rs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		return deletecar(rent);
	}
	public int deletecar(Rent rent){
		String sql = "delete from rentcar where bookid="+rent.getBookid()+" and booktype="+rent.getBooktype();
		String sql2="select * from rentcar where bookid="+rent.getBookid()+" and booktype="+rent.getBooktype();
		Connection conn;
		PreparedStatement statement;
		try {
			conn = DBHelper.getConnection();
			statement=conn.prepareStatement(sql2);
			ResultSet rs = statement.executeQuery(sql2);
			if(!rs.next()){
				return 3;
				//在这里有一个问题，就是在插入数据前后对借阅车的检索，在插入数据之前，如果用户删除了借阅车，那么提示用户取消订单
				//插入数据之后如果用户删除了借阅车，那么需要回滚操作，修改图书状态，删除之前插入的数据
				//这些争论的地方可有可无，但我觉着最终还是要加上
			}
			statement = conn.prepareStatement(sql);
			int rs2 = statement.executeUpdate(sql);	
			CallableStatement cs=conn.prepareCall("{call rentchange(?,?)}");
			cs.setInt(1, rent.getBookid());
			cs.setInt(2, rent.getBooktype());
			cs.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		}
		StartCount startCount=new StartCount();
		startCount.startCount(rent);
		return 4;
	}
	
	public void deleteRent(Rent rent){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int bookid=rent.getBookid();
		int booktype=rent.getBooktype();
		
		try {
			conn=DBHelper.getConnection();
			String sql="delete from rentbook where booktype="+booktype+" and bookid="+bookid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static ArrayList<Books> getSearch(String searchName){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Books> list = new ArrayList<Books>(); // 商品集合
		
		try {
			conn = DBHelper.getConnection();
			String sql = "select * from book0 where  locate('"+searchName+"',name)>0 "
					+ "union "
					+ "select * from book1 where  locate('"+searchName+"',name)>0 "
					+ "union "
					+ "select * from book2 where  locate('"+searchName+"',name)>0 "
					+ "union "
					+ "select * from book3 where  locate('"+searchName+"',name)>0 "
					+ "union "
					+ "select * from book4 where  locate('"+searchName+"',name)>0 "
					+ "union "
					+ "select * from book5 where  locate('"+searchName+"',name)>0 "
					+ "union "
					+ "select * from book6 where  locate('"+searchName+"',name)>0 "
					+ "union "
					+ "select * from book7 where  locate('"+searchName+"',name)>0 "
					+ "union "
					+ "select * from book8 where  locate('"+searchName+"',name)>0 "
					+ "union "
					+ "select * from book9 where  locate('"+searchName+"',name)>0 "
					+ "union "
					+ "select * from book10 where  locate('"+searchName+"',name)>0 "; // SQL语句
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Books book = new Books();
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("name"));
				book.setPublish(rs.getString("publish"));
				book.setVersion(rs.getString("version"));
				book.setPicture(rs.getString("picture"));
				book.setWriter(rs.getString("writer"));
				book.setRentstatus(rs.getString("rentstatus"));
				book.setSubstatus(rs.getString("substatus"));
				book.setPrice(rs.getString("price"));
				book.setTime(rs.getString("time"));
				book.setISBN(rs.getString("ISBN"));
				book.setType(rs.getInt("type"));
				list.add(book);// 把一个商品加入集合
			}
			return list; // 返回集合。
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			// 释放数据集对象
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			// 释放语句对象
			if (stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
