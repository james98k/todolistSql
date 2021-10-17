package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int index;
        private String title;
        private String desc;
        private String current_date;
        private String category;
	private String dueDate;
	private boolean isComplete;
	protected String user;
   
	
    public TodoItem(String title, String desc, String category, String dueDate){
//    	setDate inside
    	Date d;
    	this.category = category;
        this.title=title;
        this.desc=desc;
        this.dueDate = dueDate;
        
        d = new Date();
        SimpleDateFormat s_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.current_date = s_date.format(d);
        
    }
    
    public TodoItem(String category, String title, String desc, String date, String dueDate){
//    	load date from loadList;
    	this.category = category;
        this.title=title;
        this.desc=desc;
        this.current_date = date;
        this.dueDate = dueDate;
        
        Date d = new Date();
        SimpleDateFormat s_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.current_date = s_date.format(d);
    }
    public TodoItem(int index,String category, String title, String desc, String date, String dueDate){
//    	load date from loadList;
	this.index = index;
    	this.category = category;
        this.title=title;
        this.desc=desc;
        this.current_date = date;
        this.dueDate = dueDate;
        
    }
    
    public TodoItem(int index,String category, String title, String desc, String date, String dueDate, boolean isComplete){
//    	load date from loadList;
	this.index = index;
    	this.category = category;
        this.title=title;
        this.desc=desc;
        this.current_date = date;
        this.dueDate = dueDate;
        this.isComplete = isComplete;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }
    
    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    public String toSaveString() {
    	return category + "##" + title + "##" + desc + "##" + dueDate + "##"+ current_date + "\n";
    }
   
    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "TodoItem [title=" + title + ", desc=" + desc + ", current_date=" + current_date + ", category="
				+ category + ", dueDate=" + dueDate + "]"; 
	}
	public String toFormatString() {
		return index + ". [ "+ category +" ] " + title + " " + desc + " - "+dueDate+" - " + current_date;
		
	}
	
	public String toDbString() {
	    	String compSign = "X";
		if(isComplete == true) {
		    compSign = "V";
		}
		
		
		return index+" ["+compSign+"] "+title+" // " + desc +" // " + dueDate + " // " + category + " // ";
	  }
	
	
	
}

