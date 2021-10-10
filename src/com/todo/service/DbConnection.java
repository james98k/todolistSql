package com.todo.service;

import java.sql.*;
import java.util.*;

public class DbConnection {
	
	
	public static boolean insertDb() {
		
		String category, title, desc, duedate;
		Scanner s = new Scanner(System.in);
		
		try {
//			Statement stat1 = CreateDbConnection().createStatement();
			System.out.println("Enter Category");
			category = s.next();
			
			System.out.println("Enter Title");
			title = s.next();
			
			if(checkTitleDuplication(title)) {
				System.out.println("title duplication false");
				return false;
			}
			
			System.out.println("Enter Description");
		
			
			
			
			String query = "select * from todolist";
			ResultSet rsValue = stat1.executeQuery(query);
			
			while(rsValue.next()) {
				String tl_id = rsValue.getString("ID");
				String tl_title = rsValue.getString("TITLE");
				String tl_desc = rsValue.getString("DESCRIPTION");
				String tl_dueDate = rsValue.getString("DUEDATE");
				String tl_createDate = rsValue.getString("INSERTDATE");
				System.out.printf("id : %s title : %s\n description : %s\n duedate : %s\n createDate : %s"
									,tl_id, tl_title, tl_desc, tl_dueDate, tl_createDate
						);
			}
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	

	
	public static void db_ls() {
		try {
			Statement stat1 = CreateDbConnection().createStatement();
			String query = "SELECT * FROM TODOLIST";
			ResultSet rsValue = stat1.executeQuery(query);
			while(rsValue.next()) {
				System.out.println(rsValue);
				String tl_cate = rsValue.getString("CATEGORY");
				String tl_id = rsValue.getString("ID");
				String tl_title = rsValue.getString("TITLE");
				String tl_desc = rsValue.getString("DESCRIPTION");
				String tl_dueDate = rsValue.getString("DUEDATE");
				String tl_createDate = rsValue.getString("INSERTDATE");
				System.out.printf("category : %s\nid :%s title : %s\ndescription : %s\nduedate : %s\ncreateDate : %s\n\n"
									,tl_cate,tl_id, tl_title, tl_desc, tl_dueDate, tl_createDate
						);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static boolean checkTitleDuplication(String title) {
		boolean titleDuplicationStatus = false;
		try {
			Statement statement = CreateDbConnection().createStatement();
			PreparedStatement pstmt = null;
			Statement conn = CreateDbConnection().createStatement();
			
			
			
			System.out.println("title check in");
			String query = "SELECT * FROM TODOLIST WHERE TITLE LIKE '%?%'";
			
			
			
			pstmt.setString(0, title);
			
			titleDuplicationStatus = pstmt.execute();
			System.out.println("title check done");
			System.out.println(titleDuplicationStatus);				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		return titleDuplicationStatus;
		return false;
		
		
	}
	
}
