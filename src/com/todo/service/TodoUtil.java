package com.todo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import com.todo.service.DbConnection;
import com.todo.service.DbSingleton;

public class TodoUtil {
	Connection conn;
	
	TodoUtil(){
		this.conn = DbSingleton.getConnection();
	}
	
	

	public static void createItem(TodoList l) {
		
		String title, desc, category, dueDate;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("enter title");
		title = sc.nextLine().trim();
//		
		if(l.isDuplicate(title)) {
			System.out.println("title is duplicated");
			return;
		}
		
		System.out.println("enter the Category");
		
		category = sc.nextLine().trim();
		
		System.out.println("enter the description");
		desc = sc.nextLine().trim();
		
		System.out.println("enter due date");
		dueDate = sc.nextLine().trim();
		
		
		TodoItem t = new TodoItem(title, desc, category, dueDate);
		if(l.addItem(t) > 0) {
			System.out.println("update completed");
		}
		else {
		    System.out.println("error");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("enter index to delete");
		int index = s.nextInt();
		
		if(l.deleteItem(index) > 0) {
			System.out.println("deleted");
		 }
		else {
		    System.out.println("No such data to delete");
		}
//		
	}
	
	public static void deleteMultipleItem(TodoList l) {
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("enter index to delete");
		String index = s.nextLine();
		
		StringTokenizer st = new StringTokenizer(index," ");
		
		int tempInt = Integer.parseInt(st.nextToken());
		
		int countTemp;
		while(st.hasMoreTokens()) {
			System.out.println("deleting index : "+ tempInt);
			countTemp = l.deleteItem(Integer.parseInt(st.nextToken()));
		   }
		
		if(l.deleteItem(tempInt) > 0) {
		    System.out.println("deleted");
		 }
		
		else {
		    System.out.println("No such data to delete");
		}
//		
	}
	
	

	public static void updateItem(TodoList l) {
		
		String newTitle, newDesc, newCategory, newDueDate;
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("choose number of item");
		int num = sc.nextInt();
		sc.nextLine();
		
		System.out.println("enter the new title of the item");
		String new_title = sc.nextLine().trim();
		
		
		System.out.println("enter category");
		String new_category = sc.nextLine().trim();
		
		System.out.println("enter the new description ");
		String new_description = sc.nextLine().trim();
		
		System.out.println("enter new due date");
		String new_dueDate = sc.nextLine().trim();
		
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_dueDate);
		t.setIndex(num);
		
		
		if(l.updateItem(t) >0) {
			System.out.println("Todo edit finsihed");
		}
		
		
	}

	
	public static void saveList(TodoList l, String filename) {
		String file_n = filename + ".txt";
		
		try {
			FileWriter file = new FileWriter(file_n);
//			file.write("이게 되니...");
			for(TodoItem item : l.getList("temp_keyword")) {
				file.write(item.toSaveString());
			}
			System.out.println("TodoList Data is Safely Stored");
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
	}
	
	public static void loadList(TodoList l, String filename) {
//		read file
		String file_n = filename + ".txt";
		try {
			BufferedReader bfr = new BufferedReader(new FileReader(file_n));
			String singleLn;
			int count = 0;
			while((singleLn = bfr.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(singleLn, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String dueDate = st.nextToken();
				String date = st.nextToken();
				
				
				TodoItem t = new TodoItem(count, category, title, desc, date, dueDate);
				
				l.addItemPre(t);
				
				count++;
			}
			bfr.close();
			System.out.println(count+" Items Added to List\n");
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void findList(TodoList l, String keyword) {

		int count = 0;
		
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다\n",count);
		
	}
	
	public static void findList(TodoList l) {	
		Scanner sc = new Scanner(System.in);
		String s_input = sc.next().trim();
		System.out.println("token to find : " + s_input);
		
		
		for(TodoItem item : l.getList(s_input)) {			
			
			String s = item.getDesc().toString();
			String title = item.getTitle().toString();
			
			StringTokenizer st_title = new StringTokenizer(title, " ");
			StringTokenizer st = new StringTokenizer(s , " ");
			
			List<Integer> tempList = new ArrayList<Integer>();
			
			
			
			while(st.hasMoreTokens()) {
				String a = st.nextToken();
//				String titleToken = st_title.nextToken();
//				System.out.println("token value : "+ a);
				if(a.contains(s_input)) {
//					System.out.println("token true : " + a);
//					return item value]
					
//					System.out.print(" "+l.indexOf(item));
					System.out.println(item.toFormatString());
					
					if(tempList.contains(l.indexOf(item))){
						continue;
					}
					else {
						tempList.add(l.indexOf(item));
//						System.out.println("value added");
					}
				}				
			}
			while(st_title.hasMoreElements()) {
				String titleToken = st_title.nextToken();
				if(titleToken.contains(s_input)) {
					
//					System.out.print(" "+l.indexOf(item));
					System.out.println(item.toFormatString());
					
					if(tempList.contains(l.indexOf(item))){
						continue;
					}
					else {
						tempList.add(l.indexOf(item));
//						System.out.println("value added");
					}
				}
			}
//			System.out.println("the index of your searching value is ");
//			for(Integer i : tempList) {
//				System.out.print(" " + i );
//			}
//			-> return index;
			
				
			
		}
//		System.out.println("");
//		sc.close();
		
		
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.println(item);
			count ++;
		}
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n%dhas founded\n", count);
	}
	
	
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[all list, count : %d]\n",l.getCount());
		
		for(TodoItem item : l.getOrderedList(orderby, ordering)) {
//		    toString 수정
//			System.out.println(item.toFormatString());
		    	System.out.println(item.toDbString());
		}
	}
	
	public static void showComp(TodoList l) {
	    for(TodoItem item: l.getCompList()) {
		System.out.println(item.toDbString());
	    }
	}
	
	public static void compMulti(TodoList l) {
	    Scanner s = new Scanner(System.in);
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    
	    String compString;
	    try {
		compString = br.readLine();
		
		 l.completeMulti(compString);
		 
		
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
//	  
	   
	}
	
	
	public static void alterTable(TodoList l) {
	    l.alterTable();
	    System.exit(0);
	}
	
	public static void isCompleted(TodoList l, int indexId) {
	    l.isCompleted(indexId);
	}
	
        public static void setUser(TodoList l, String userName) {
            l.setUser(userName);
        }
        public static void showUser(TodoList l) {
            l.getDbUserName();
        }
        
        public static void showCurrentUserName(TodoList l) {
            l.getCurrentUserName();
        }
        
}
	












