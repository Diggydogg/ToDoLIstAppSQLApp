package com.todo.dao;


import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	Connection conn;
	
	private List<TodoItem> list;

	public TodoList() {
		
		
		this.conn = DbConnect.getConnection();
		this.list = new ArrayList<TodoItem>();
	}
	

	public int addItem(TodoItem t) {
		//list.add(t);
		String sql = "insert into list (title, memo, category, current_date, due_date,is_completed,process,important_level)"
		+" values (?,?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getIs_completed());
			pstmt.setInt(7, t.getProcess());
			pstmt.setInt(8, t.getImportant_level());
			count = pstmt.executeUpdate();
			pstmt.close();
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count =0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	/*
	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}
	*/
	public int getCount() {
		Statement stmt;
		int count=0;
		try {
			stmt=conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			
			count = rs.getInt("count(id)");
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	public ArrayList<TodoItem> getList() {
		
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int process = rs.getInt("process");
				int important_level = rs.getInt("important_level");
				TodoItem t = new TodoItem(title, description,category,due_date);
				
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				t.setProcess(process);
				t.setImportant_level(important_level);
				list.add(t);
			}
			stmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}
	
	public void listAll() {
		System.out.println("\n"
				+ "inside list_All method\n");
		for (TodoItem myitem : list) {
			System.out.println(myitem.getTitle() + myitem.getDesc());
		}
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}
	
	public void sortByDate() {
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
	
	//unused
	
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (id,important_level,category,title,is_completed,process,memo,current_date,due_date)"
					+" values (?,?,?,?,?,?,?,?,?);";
			int records = 0;
			while ((line = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line,"##");
				int id = Integer.parseInt(st.nextToken());
				int important_level = Integer.parseInt(st.nextToken());
				String category = st.nextToken();
				String title = st.nextToken();
				int is_completed =Integer.parseInt(st.nextToken());
				int process =Integer.parseInt(st.nextToken());
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,id);
				pstmt.setInt(2,important_level);
				pstmt.setString(3,category);
				pstmt.setString(4,title);
				pstmt.setInt(5,is_completed);
				pstmt.setInt(6,process);
				pstmt.setString(7, description);
				pstmt.setString(8, current_date);
				pstmt.setString(9, due_date);
				int count = pstmt.executeUpdate();
				if(count >0) records++;
				pstmt.close();
				
				
			}
			System.out.println(records+" records read!!");
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?, is_completed=?, process=?,important_level=?"
				+" where id =?;";
		PreparedStatement pstmt;
		int count =0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getCurrent_date());
			pstmt.setString(5,t.getDue_date());
			pstmt.setInt(6,t.getIs_completed());
			pstmt.setInt(7,t.getProcess());
			pstmt.setInt(8, t.getImportant_level());
			pstmt.setInt(9,t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList(String keyword) {
		// TODO Auto-generated method stub
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword="%"+keyword+"%";
		try {
			String sql ="SELECT * FROM list WHERE title like ? or memo like ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs =pstmt.executeQuery();
			
			
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int process = rs.getInt("process");
				int important_level = rs.getInt("important_level");
				
				TodoItem t = new TodoItem(title, description,category,due_date);
				
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				t.setProcess(process);
				t.setImportant_level(important_level);
	
				list.add(t);
			}
			pstmt.close();
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getCategories() {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList <String>();
		Statement stmt;
		try {
			stmt=conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				
				String category = rs.getString("category");
				
				
				list.add(category);
			}
			stmt.close();
			
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getListCategory(String keyword) {
		// TODO Auto-generated method stub
		ArrayList <TodoItem> list= new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql ="SELECT * FROM list WHERE category =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs =pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int process = rs.getInt("process");
				int important_level =rs.getInt("important_level");
				TodoItem t = new TodoItem(title, description,category,due_date);
				
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				t.setProcess(process);
				t.setImportant_level(important_level);
				list.add(t);
			}
			pstmt.close();
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList <TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list where "+orderby;
			if(ordering==0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int process = rs.getInt("process");
				int important_level =rs.getInt("important_level");
				TodoItem t = new TodoItem(title, description,category,due_date);
				
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				t.setProcess(process);
				t.setImportant_level(important_level);
				list.add(t);
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}


	public void completeItem(int index) {
		// TODO Auto-generated method stub
		
		
		
		String sql = "update list set is_completed=?"
				+" where id =?;";
		PreparedStatement pstmt;
		int count =0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,1);
			
			pstmt.setInt(2,index);
			count = pstmt.executeUpdate();
			pstmt.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
	}


	public ArrayList <TodoItem> getList(int completed) {
		// TODO Auto-generated method stub
		ArrayList<TodoItem> list = new ArrayList <TodoItem>();
		
		try {
			
			PreparedStatement pstmt;
			//stmt = conn.createStatement();
			
			String sql = "select * from list where is_completed = ?;";
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,completed);
			
			//ResultSet rs = stmt.executeQuery(sql);
			ResultSet rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int process = rs.getInt("process");
				int important_level =rs.getInt("important_level");
				TodoItem t = new TodoItem(title, description,category,due_date);
				
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_completed(is_completed);
				t.setProcess(process);
				t.setImportant_level(important_level);
				list.add(t);
			}
			//stmt.close();
			pstmt.close();
			
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
}
