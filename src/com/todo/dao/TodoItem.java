package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private String category;
    private String title;
    private String desc;
    
    private String current_date;
    private String due_date;
	private int id;
	private int is_completed;
	
	private int process;
	
	private int important_level;

    public TodoItem(String category,String title, String desc){
    	this.category =category;
        this.title=title;
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date=f.format(new Date());
        this.due_date = "";
        
    }
    
    public TodoItem(String title,String desc, String category,String due_date){
    	this.category =category;
        this.title=title;
        this.desc=desc;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date=f.format(new Date());
        this.due_date =due_date;
        
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

    public String getDue_date() {
        return due_date;
    }
    
    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
    
    public String toSaveString() {
    	return id+"##"+important_level+"##"+category+"##"+title + "##"+is_completed+"##" +process+"##"+ desc +"##" + current_date+"##"+due_date+"\n";
    }

    public String toString() {
    	return id+stars() +"["+category+"] " +title   +check()+" - " + process()+ desc +" - " + current_date+" - "+due_date+"\n";
    }


	public String getCategory() {
		return category;
	}




	public void setCategory(String category) {
		this.category = category;
	}




	public void setId(int id) {
		this.id=id;
		// TODO Auto-generated method stub
		
	}

	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}
	public String check() {
		if (this.is_completed==1) {
			return " [v]";
		}
		else return " [ ]";
	}

	public int getProcess() {
		return process;
	}

	public void setProcess(int process) {
		this.process = process;
	}

	public int getImportant_level() {
		return important_level;
	}

	public void setImportant_level(int important_level) {
		this.important_level = important_level;
	}
	
	
	public String process() {
		if (this.is_completed!=1) {
			return " current process: "+getProcess()+" - ";
		}
		return " -  ";
	}
	
	public String stars() {
		String stars="";
		int il= getImportant_level(); 
		//System.out.print(important_level);
		for(int i=0;i<il;i++) {
			stars+="*";
		}
		return " [ "+stars+" ] ";
	}
}
