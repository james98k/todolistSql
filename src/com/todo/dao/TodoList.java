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
	Connection conn;
	
	public TodoList() {
		this.conn = DbSingleton.getConnection();
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
		String sql = "INSERT INTO TODOLIST (TITLE, DESCRIPTION, CATEGORY, INSERTDATE, DUEDATE)" + 
					"VALUES(?,?,?,?,?)";
		
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDueDate());
			
			count = pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
	}
	
	
	public void addItemPre(TodoItem t) {
		list.add(t); 
	}

	public int deleteItem(int index) {
		String sql = "DELETE FROM TODOLIST WHERE ID = ?;";
		PreparedStatement pstmt;
		int count = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
		
	}

	public int updateItem(TodoItem t) {

		String sql = "UPDATE LIST SET TITLE  =? , DESCRIPTION = ?, CATEGORY = ?, INSERTDATE = ?, DUEDATE = ?"+
		"where ID = ?";
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
			
			count = pstmt.executeUpdate();
			
			pstmt.close();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;

	}

	public ArrayList<TodoItem> getList(String keyword){
		
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM TODOLIST";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int id= rs.getInt("id");
				String category = rs.getString("CATEGORY");
				String title = rs.getString("TITLE");
				String description = rs.getString("DESCRIPTION");
				String due_date = rs.getString("DUEDATE");
				String current_date=  rs.getString("INSERTDATE");
				
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setIndex(id);
				t.setCurrent_date(current_date);
				list.add(t);
				
			}
			stmt.close();
			
			
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

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
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
		Statement stmt;
		int count= 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT COUNT(ID) FROM TODOLIST;";
			
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		count = rs.getInt("COUNT(ID)");
		stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return count;
		
	}
	
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("student.txt"));
			String line;
			String sql = "INSERT INTO LIST (TITLE, DESCRIPTION, CATEGORY, INSERTDATE, DUEDATE)"
					+ "VALUES(?,?,?,?,?);";
			
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
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				
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
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT CATEGORY FROM TODOLIST";
			ResultSet rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		
		String sql = "SELECT * FROM TODOLIST WEHRE CATEGORY = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM TODOLIST ORDER BY" + orderby;
			if(ordering == 0) {
				sql += " desc";
			}
			ResultSet rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return null;
		
	}
	
	
	
	
	
}
