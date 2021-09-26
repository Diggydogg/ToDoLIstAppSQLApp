package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc,category,due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Create item Section\n"
				+ "enter the title\n");
		
		
		
		
		title = sc.nextLine().trim();
		if (list.isDuplicate(title)) {
			System.out.printf("title can't be duplicate");
			return;
		}
		
		System.out.println("enter the category");
		
		category = sc.nextLine().trim();
		
		
		
		System.out.println("enter the description");
		//desc = sc.nextLine().trim();
		desc = sc.nextLine();
		
		System.out.println("enter the due date");
		due_date = sc.nextLine();
		
		TodoItem t = new TodoItem(category,title, desc);
		t.setDue_date(due_date);
		list.addItem(t);
		
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		
		System.out.println("\n"
				+ "========== Delete Item Section\n"
				//+ "enter the title of item to remove\n"
				+ "enter the number of item to remove\n"
				+ "\n");
		
		//String title = sc.next();
		int number =sc.nextInt(); 
		
		
		
		for (TodoItem item : l.getList()) {
			//if (title.equals(item.getTitle())) {
			if (number==l.indexOf(item)) {
				
				//System.out.println(l.indexOf(item)+". ["+item.getCategory()+"] "+"Item Title: " + item.getTitle() + "  Item Description:  " + item.getDesc() +" "+item.getCurrent_date());
				System.out.println(l.indexOf(item)+". ["+item.getCategory()+"] "+"Item Title: " + item.getTitle() + "  Item Description:  " + item.getDesc() +" "+item.getDue_date()+" "+item.getCurrent_date());
				
				System.out.println("Do you want to delete this item? (y/n)");
				
				char yes_no = sc.next().charAt(0);
				if(yes_no =='y') {
					l.deleteItem(item);
				}
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Edit Item Section\n"
				//+ "enter the title of the item you want to update\n"
				+ "enter the number of the item you want to update\n"
				+ "\n");
		//String title = sc.nextLine().trim();
		int number = sc.nextInt();
		//for emptying buffer
		
		sc.nextLine();
		/*
		if (!l.isDuplicate(title)) {
			System.out.println("title doesn't exist");
			return;
		}
		*/
		boolean found = false;
		for (TodoItem item : l.getList()) {
			if (number ==l.indexOf(item)) {
				found = true;
			}
		}
		if (!found) {
			System.out.println("number of item does not exist in the List");
			return;
		}
		
		
		System.out.println("enter the new title of the item");
		//String new_title = sc.nextLine().trim();
		String new_title = sc.nextLine();
		
		if (l.isDuplicate(new_title)) {
			System.out.println("title can't be duplicate");
			return;
		}
		
		
		
		
		
		
		System.out.println("enter the category");
		String category = sc.nextLine().trim();
		
		
		
		System.out.println("enter the new description ");
		//String new_description = sc.nextLine().trim();
		String new_description = sc.nextLine();
		
		System.out.println("enter the due date");
		String due_date = sc.nextLine();
		
		
		
		for (TodoItem item : l.getList()) {
			if (number == l.indexOf(item)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(category,new_title, new_description);
				
				t.setDue_date(due_date);
				l.addItem(t);
				System.out.println("item updated");
			}
		}

	}

	public static void listAll(TodoList l) {
		int count  =0;
		for (TodoItem item:l.getList()) {
			count++;
		}
		
		System.out.println("[전체목록, 총 "+count+"개]");
		
		for (TodoItem item : l.getList()) {
			//System.out.println(l.indexOf(item)+". ["+item.getCategory()+"] "+"Item Title: " + item.getTitle() + "  Item Description:  " + item.getDesc() +" "+item.getCurrent_date());
			System.out.println(l.indexOf(item)+". ["+item.getCategory()+"] "+"Item Title: " + item.getTitle() + "  Item Description:  " + item.getDesc() +" "+item.getDue_date()+" "+item.getCurrent_date());
			
		}
	}
	
	
	
	
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				//item.toSaveString();
				w.write(item.toSaveString());
			}
			w.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String oneline;
			while((oneline = br.readLine())!=null) {
				StringTokenizer st= new StringTokenizer(oneline,"##");
				String category = st.nextToken();
				String title =st.nextToken();
				String desc = st.nextToken();
				
				String current_date = st.nextToken();
				String due_date = st.nextToken();
				TodoItem t = new TodoItem(category,title,desc);
				
				t.setCurrent_date(current_date);
				t.setDue_date(due_date);
				l.addItem(t);
				
			}
			br.close();
			System.out.println("파일 로드성공!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			
		}
		
	}

	public static void find(TodoList l,String keyword) {
		// TODO Auto-generated method stub
		// title, desc include keyword
		
		for (TodoItem item : l.getList()) {
			if (item.getTitle().contains(keyword)||item.getDesc().contains(keyword)) {
				System.out.println(l.indexOf(item)+". ["+item.getCategory()+"] "+"Item Title: " + item.getTitle() + "  Item Description:  " + item.getDesc() +" "+item.getDue_date()+" "+item.getCurrent_date());
				
			}
		}
	}
	
}
