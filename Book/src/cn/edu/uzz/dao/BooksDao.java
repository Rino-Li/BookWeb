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
	// ������е�ͼ����Ϣ
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
			ArrayList<Books> list = new ArrayList<Books>(); // ��Ʒ����
			
			try {
				conn = DBHelper.getConnection();
				String sql = "select * from "+tablename+";"; // SQL���
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
					list.add(book);// ��һ����Ʒ���뼯��
				}
				return list; // ���ؼ��ϡ�
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			} finally {
				// �ͷ����ݼ�����
				if (rs != null) {
					try {
						rs.close();
						rs = null;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				// �ͷ�������
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
		
		//����û��Ƿ��ղش���
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
		
		//ȡ���ղ�
		public  int cancellike(String account,int type,int id){
			
			
			try {
				String sql = "select * FROM likebook a WHERE a.account = '"+account+"' and a.booktype='"+type+"' and a.bookid='"+id+"'";

				Connection conn = DBHelper.getConnection();
				Statement statement;
				statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()){
					statement.executeUpdate("DELETE from likebook where account='" + account + "' and booktype='" + type + "' and bookid='"+id+"'");
					return 1;//ɾ���ɹ�
				}else{
					return 3;//û���ղؼ�¼
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return 2;//������
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
					return 1;//ɾ���ɹ�
				}else{
					return 3;//û���ղؼ�¼
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return 2;//������
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
					return "Ԥ��";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return "Ԥ�� ";
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
					if(rentstatus.equals("������")){
						return "����";
					}else{
					return rentstatus;
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return "����";
			}
			return "����";
		}
			
			//���Ԥ��������Ƿ�Ϊͬһ���û�
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
				ArrayList<Like> list = new ArrayList<Like>(); // ��Ʒ����
				try {
					conn = DBHelper.getConnection();
					String sql = "select * from likebook where account='"+account+"';"; // SQL���
					stmt = conn.prepareStatement(sql);
					rs = stmt.executeQuery();
					while (rs.next()) {
						Like like=new Like();
						like.setAccount(account);
						like.setBookname(rs.getString("bookname"));
						like.setBooktype(rs.getInt("booktype"));
						like.setBookid(rs.getInt("bookid"));
						like.setPicture(rs.getString("picture"));
						list.add(like);// ��һ����Ʒ���뼯��
					}
					return list; // ���ؼ��ϡ�
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				} finally {
					// �ͷ����ݼ�����
					if (rs != null) {
						try {
							rs.close();
							rs = null;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					// �ͷ�������
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
					String sql = "select * from "+tablename+" where id='"+id+"';"; // SQL���
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
					return book; // ���ؼ��ϡ�
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				} finally {
					// �ͷ����ݼ�����
					if (rs != null) {
						try {
							rs.close();
							rs = null;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					// �ͷ�������
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
				ArrayList<Subscribe> list = new ArrayList<Subscribe>(); // ��Ʒ����
				try {
					conn = DBHelper.getConnection();
					String sql = "select * from subscribe where account='"+account+"';"; // SQL���
					stmt = conn.prepareStatement(sql);
					rs = stmt.executeQuery();
					while (rs.next()) {
						Subscribe subscribe=new Subscribe();
						subscribe.setAccount(account);
						subscribe.setBookid(rs.getInt("bookid"));
						subscribe.setBooktype(rs.getInt("booktype"));
						subscribe.setBookname(rs.getString("bookname"));
						subscribe.setPicture(rs.getString("picture"));
						list.add(subscribe);// ��һ����Ʒ���뼯��
					}
					return list; // ���ؼ��ϡ�
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				} finally {
					// �ͷ����ݼ�����
					if (rs != null) {
						try {
							rs.close();
							rs = null;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					// �ͷ�������
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
				ArrayList<Rent> list = new ArrayList<Rent>(); // ��Ʒ����
				try {
					conn = DBHelper.getConnection();
					String sql = "select * from rentcar where account='"+account+"';"; // SQL���
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
						rent.setWilltime(rs.getString("enddate"));
						list.add(rent);// ��һ����Ʒ���뼯��
					}
					return list; // ���ؼ��ϡ�
				} catch (Exception ex) {
					ex.printStackTrace();
					return null;
				} finally {
					// �ͷ����ݼ�����
					if (rs != null) {
						try {
							rs.close();
							rs = null;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					// �ͷ�������
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
						return 1;//ɾ���ɹ�
					}else{
						return 3;//û�н��ļ�¼
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					return 2;//������
				}
			}
}
