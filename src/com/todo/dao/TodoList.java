package com.todo.dao;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;
import com.todo.service.DbConnection;
import com.todo.service.DbSingleton;

public class TodoList {
	private List<TodoItem> list;
	private int userId;
	private String currentUserName;
	Connection conn;
	
	
	public TodoList() {
		this.conn = DbSingleton.getConnection();
	}
	
	
	public void getUser(String user) {
	    
	}
	public int getSize() {
		int count = 0;
		for(TodoItem item : list) {
			count++;
		}
		return count;
	}

//	public void addItem(TodoItem t) {
//		list.add(t); 
//		System.out.println("Todo Item Added");
//	}
	
	public int addItem(TodoItem t) {
		String sql = "INSERT INTO list (title, memo, category,current_date, due_date, user_id_fk)" + 
					"VALUES(?,?,?,?,?,?)";
		
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDueDate());
			pstmt.setInt(6, this.userId);
			count = pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return count;
	}
	
	
	public void addItemPre(TodoItem t) {
		list.add(t); 
	}

	public int deleteItem(int index) {
		String sql = "DELETE FROM list WHERE id = ? and user_id_fk = ?;";
		PreparedStatement pstmt;
		int count = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			pstmt.setInt(2, this.userId);
			count = pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return count;
		
	}
	
	public int updateItem(TodoItem t) {
	    String sql = "update list set title = ?, memo = ?, category = ?, current_date = ?, due_date = ?" + " WHERE id = ? AND user_id_fk = ?;";
	    
	    PreparedStatement pstmt;
	    int count = 0;
	    
	    try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, t.getTitle());
		pstmt.setString(2, t.getDesc());
		pstmt.setString(3, t.getCategory());
		pstmt.setString(4, t.getCurrent_date());
		pstmt.setString(5, t.getDueDate());
		pstmt.setInt(6, t.getIndex());
		pstmt.setInt(7, this.userId);
		
		count = pstmt.executeUpdate();
		pstmt.close();
		
		
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    
	    
	    return count;
	    
	}


	public ArrayList<TodoItem> getList(){
		
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {	
			PreparedStatement pstmt;
			String sql = "SELECT * FROM list WHERE user_id_fk like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,this.userId);
			
			ResultSet rs = pstmt.executeQuery(sql);
			
			while(rs.next()) {
				int id= rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date=  rs.getString("current_date");
				
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setIndex(id);
				t.setCurrent_date(current_date);
				list.add(t);
				
			}
			pstmt.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;	
	}
	public ArrayList<TodoItem> getList(String keyword){
		
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		
		keyword = "%" + keyword + "%";
		
		
		try {
			
			String sql = "SELECT * FROM list WHERE title LIKE ? or memo LIKE ? AND user_id_fk LIKE ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			pstmt.setInt(3, this.userId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
			    	int id= rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date=  rs.getString("current_date");
				
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setIndex(id);
//				t.setCurrent_date(current_date);
				list.add(t);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	
	public void sortByName() {
		System.out.println("List sorted by Name");
		Collections.sort(list, new TodoSortByName());
		

	}

	public void listAll() {
		for (TodoItem myitem : list) {
			System.out.println("["+myitem.getTitle() +"] "+ myitem.getDesc()+" - "+ myitem.getDueDate()+" " + myitem.getCurrent_date());
		}
	}
	
	public void reverseList() {
		System.out.println("List in Reverse Order");
		Collections.reverse(list);
	}

	public void sortByDate() {
		System.out.println("List Sorted by Date");
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title_s) {
	    	
		PreparedStatement pstmt;
	   
		try {
		    	
			String sql = "SELECT * FROM list WHERE title LIKE ? WHERE user_id_fk like ? ";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(sql);
			
			while(rs.next()) {
//			    	int id= rs.getInt("id");
//				String category = rs.getString("category");
//				String title = rs.getString("title");
//				String description = rs.getString("memo");
//				String due_date = rs.getString("due_date");
//				String current_date=  rs.getString("current_date");
//				
//				TodoItem t = new TodoItem(title, description, category, due_date);
//				t.setIndex(id);
////				t.setCurrent_date(current_date);
//				list.add(t);
			    return true;
				
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	public void getIndexItem(int index) {
		int count  = 0;
		for(TodoItem item : list) {
			if(count == index) {
				System.out.println(item.toString());
			}
			
		}
	}
	
	public String toString(TodoItem myitem) {
//		return "TodoList [list=" + list + "]";
		return "["+myitem.getTitle() +"] "+ myitem.getDesc()+" - "+ myitem.getDueDate()+" " + myitem.getCurrent_date();
	}
	
	public TodoItem getIndex(int value) {
		int count = 0;
		for(TodoItem item : list) {
			if(count == value) {
				return item;
			}
			count++;
		}
		return null;
		
	}
	
	public int getCount() {
		PreparedStatement pstmt;
		
		
		int count= 0;
		try {
			
			String sql = "SELECT COUNT(id) FROM list where user_id_fk like ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, this.userId);
			
		ResultSet rs = pstmt.executeQuery(sql);
		rs.next();
		count = rs.getInt("COUNT(ID)");
		pstmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return count;
		
	}
	
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "INSERT INTO list (title, memo, category, due_date, current_date,user_id_fk)"
					+ "VALUES(?,?,?,?,?,?);";
			
			int records = 0;
			
			while((line = br.readLine())!=null) {
				StringTokenizer st =new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description =st.nextToken();
				String due_date = st.nextToken ();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,  title);;
				pstmt.setString(2,  description);
				pstmt.setString(3, category);
				pstmt.setString(4, due_date);
				pstmt.setString(5, current_date);
				pstmt.setInt(6, this.userId);
				
				int count = pstmt.executeUpdate();
				
				if(count> 0 ) records++;
				
				pstmt.close();
				
			};
			System.out.println(records + "records read!!");
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		PreparedStatement pstmt = null;
		
		try {
			
			String sql = "SELECT DISTINCT category FROM list WHERE user_id_fk LIKE ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, this.userId);
			
			ResultSet rs = pstmt.executeQuery(sql);
			
			
			
			while(rs.next()) {
			    String category = rs.getString("category");
			    
			    list.add(category);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		
		keyword = "%" +  keyword + "%";
		String sql = "SELECT * FROM list WHERE category LIKE ? AND user_id_fk LIKE"+this.userId;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
			    	int id= rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date=  rs.getString("current_date");
				
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setIndex(id);
//				t.setCurrent_date(current_date);
				list.add(t);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
		
	
		}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		
		try {
			
			String sql = "SELECT * FROM list ORDER BY " + orderby + "WHERE user_id_fk LIKE"+this.userId;
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, this.userId);
			if(ordering == 0) {
				sql += " desc";
			}
			else {
			    sql += " asc";
			}
			ResultSet rs = pstmt.executeQuery(sql);
			
			while(rs.next()) {
			    int id= rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date=  rs.getString("current_date");
				int isComplete = rs.getInt("is_complete");
				
//				TodoItem t = new TodoItem(title, description, category, due_date);
				boolean compValue;
				if(isComplete == 0) {
				    compValue = false;
				}
				else
				    compValue = true;
				
				TodoItem t = new TodoItem(id, category, title, description, current_date, due_date, compValue);
				t.setIndex(id);
//				t.setCurrent_date(current_date);
				list.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return list;
		
	}
	
	public void alterTable() {
	    Statement stmt;
	    try {
		stmt = conn.createStatement();
		
		String sql = "DROP TABLE list";
		
		ResultSet rs = stmt.executeQuery(sql);
		
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    
	}
	
	public void isCompleted(int indexId) {
	    PreparedStatement pstmt;
	    try {
		
		
		String sql = "UPDATE list SET is_complete = 1 WHERE id =  "+ indexId + "AND user_id_fk like" + this.userId;
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(indexId, this.userId);
//		ResultSet rs = stmt.executeUpdate(sql);
		pstmt.executeUpdate(sql);
		
		
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	
	public ArrayList<TodoItem> getCompList(){
	    
	    ArrayList<TodoItem> list = new ArrayList();
	    
	    String sql = "SELECT * FROM list WHERE is_complete = 1 AND user like ?;";
//	    Statement stmt;
	    PreparedStatement pstmt;
	    try {
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, this.userId);
		ResultSet rs = pstmt.executeQuery(sql);
		
		while(rs.next()) {
		    	
		    	int id= rs.getInt("id");
			String category = rs.getString("category");
			String title = rs.getString("title");
			String description = rs.getString("memo");
			String due_date = rs.getString("due_date");
			String current_date=  rs.getString("current_date");
			int isComplete = rs.getInt("is_complete");
			
			boolean compValue;
//			System.out.println("is comp test : "+isComplete);
			if(isComplete == 0) {
			    compValue = false;
			}
			else
			    compValue = true;
			TodoItem t = new TodoItem(id, category, title, description, current_date, due_date, compValue );
			t.setIndex(id);
//			t.setCurrent_date(current_date);
			list.add(t);
			
		    
		}
		
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return list;
	}
	
	public void setUser(String user) {
	    
	    Scanner s = new Scanner(System.in);
	    
	    PreparedStatement pstmt = null;
	    
//	    String sql = "SELECT * FROM list WHERE user_id_fk = ?";
	    String sql = "SELECT * FROM user WHERE name LIKE ?";
	    
	    
	    try {
		pstmt = conn.prepareStatement(sql);
//		pstmt.setInt(1, user);
		pstmt.setString(1, user);
		
		ResultSet rs = pstmt.executeQuery();
//		
//		setting user Index for user table
		this.userId = rs.getInt("id");
		this.currentUserName = rs.getString("name");
		
		System.out.println("User "+this.currentUserName+" Welcome");
		
		
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
		System.out.println("No Such User, Would you like to register?");
		String registerStatus = s.nextLine().trim();
		
		if(registerStatus.equalsIgnoreCase("y")) {
		    String newUserName = s.nextLine().trim();
		    
		    setUserName(newUserName);
		}
	    }    
	    
	}
	
	public void setUserName(String userName) {
	    PreparedStatement pstmt;
	    String sql = "INSERT INTO user(name) values (?)";
	    
	    try {
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, userName);
		
		int count = pstmt.executeUpdate();
		if(count > 0) {
		    System.out.println("Welcome New User "+userName);
		    
		}
		else {
		    System.out.println("error");
		}
		
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	}
	
	
	
	public void getDbUserName() {
//	    getUserName();
	    Statement stmt;
	    String query = "Select * from user";
	    
	    try {
		stmt = conn.createStatement();
		
		ResultSet rs = stmt.executeQuery(query);
		    
		while(rs.next()) {
		    System.out.println(rs.getString("name"));
		}
		
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	
	public void getCurrentUserName() {
	    System.out.println("Current User : "+currentUserName);
	}
	
}
