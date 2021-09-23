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
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Create item Section\n"
				+ "enter the title\n");
		
		title = sc.nextLine().trim();
		if (list.isDuplicate(title)) {
			System.out.printf("title can't be duplicate");
			return;
		}
		
		System.out.println("enter the description");
		//desc = sc.nextLine().trim();
		desc = sc.nextLine();
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title = sc.next();
		
		System.out.println("\n"
				+ "========== Delete Item Section\n"
				+ "enter the title of item to remove\n"
				+ "\n");
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Edit Item Section\n"
				+ "enter the title of the item you want to update\n"
				+ "\n");
		String title = sc.nextLine().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("title doesn't exist");
			return;
		}

		System.out.println("enter the new title of the item");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("title can't be duplicate");
			return;
		}
		
		System.out.println("enter the new description ");
		//String new_description = sc.nextLine().trim();
		String new_description = sc.nextLine();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("item updated");
			}
		}

	}

	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println("Item Title: " + item.getTitle() + "  Item Description:  " + item.getDesc() +" "+item.getCurrent_date());
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
				String title =st.nextToken();
				String desc = st.nextToken();
				String current_date = st.nextToken();
				TodoItem t = new TodoItem(title, desc);
				
				t.setCurrent_date(current_date);
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
	
}
