package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.DbConnection;
import com.todo.service.DbSingleton;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		System.out.println("Welcome to TodoList, Please enter your name to start");
		String user = sc.next().trim();
		
		TodoUtil.setUser(l, user);
//		처음 실행시 txt 파일에서 data 불러오기 -> 조건으로 변경하기
//		l.importData("student.txt");
//		TodoUtil.loadList(l, "student");
		
		Menu.displaymenu();
		do {
//			Menu.displaymenu();
			isList = false;
			String choice = sc.next();
			
			switch (choice) {
			case "help":
				Menu.displaymenu();
				break;
			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
//				TodoUtil.listAll(l);
				TodoUtil.listAll(l, "TITLE", 1);
				break;

//			case "ls_name_asc":
//				l.sortByName();
//				isList = true;
//				break;
			case "ls_name_asc":
				System.out.println("order by name");
				TodoUtil.listAll(l, "title", 1);
				break;
			case "ls_name_desc":
				System.out.println("order by name");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date_asc":
				System.out.println("order by duedate");
				TodoUtil.listAll(l, "due_date", 1);
				break;
			case "ls_date_desc":
				System.out.println("order by date desc");
				TodoUtil.listAll(l, "due_date", 0);
				break;
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			
			case "find":
//				TodoUtil.findList(l);
			    	System.out.println("find keyword");
				String keyword = sc.next();
				TodoUtil.findList(l, keyword);
				break;
			case "find_cate":
			    	System.out.println("find category");
				String cate = sc.next();
				TodoUtil.findCateList(l, cate);
				break;
//			case "exit_db":
//			    	System.out.println("alter table");
//			    	TodoUtil.alterTable(l);
			case "comp":
			    	System.out.println("enter id of the completed work");
			    	int indexID = sc.nextInt();
			    	TodoUtil.isCompleted(l, indexID);
			    	break;
			case "show_comp":
			    	System.out.println("show Completed works");
			    	TodoUtil.showComp(l);
			    	break;
			case "comp_multi":
			    	System.out.println("enter multiple complete index");
			    	TodoUtil.compMulti(l);
			    	break;
			case "show_all_user":
			    	TodoUtil.showUser(l);
			    	break;
			case "show_current_user":
			    	TodoUtil.showCurrentUserName(l);
			    	break;
			case "set_user":
			    	System.out.println("Enter user name");
			    	String userName = sc.next();
			    	TodoUtil.setUser(l,userName);
			    	break;
			case "del_multi":
//				System.out.println("enter indexs to delete");
				TodoUtil.deleteMultipleItem(l);
				break;

			case "exit":
				TodoUtil.saveList(l, "student");
				
				quit = true;
				break;
				
			default:
				System.out.println("##please enter one of the above mentioned command, (press \"help for instructions\")");
				break;
			}
			
//			System.out.println("\nhint : help");
			if(isList) l.listAll();
		} while (!quit);
	}
}
