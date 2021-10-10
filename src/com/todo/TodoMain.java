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
		
		TodoUtil.loadList(l, "todolist");
		
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
//				TodoUtil.setDatabase();
//				DbConnection.insertDb();
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
//				TodoUtil.listAll(l);
				DbConnection.db_ls();
				break;

			case "ls_name_asc":
				l.sortByName();
				isList = true;
				break;

			case "ls_name":
				System.out.println("order by name");
				TodoUtil.listAll(l, "TITLE", 1);
				break;
				
			case "ls_name_desc":
				System.out.println("order by name");
				TodoUtil.listAll(l, "TITLE", 0);
				break;
				
			case "ls_date":
				System.out.println("order by duedate");
				TodoUtil.listAll(l, "DUEDATE", 1);
				break;
			case "ls_date_desc":
				System.out.println("order by date desc");
				TodoUtil.listAll(l, "DUEDATE", 1);
				break;
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "exit":
				TodoUtil.saveList(l, "student");
				
				quit = true;
				break;
			case "find":
//				TodoUtil.findList(l);
				String keyword = sc.nextLine().trim();
				TodoUtil.findListDb(l, keyword);
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.findCateList(l, cate);
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
