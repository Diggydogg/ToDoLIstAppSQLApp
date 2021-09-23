package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("1. Add a new item ( add ) 항목 추가 ");
        System.out.println("2. Delete an existing item ( del )항목 삭제 ");
        System.out.println("3. Update an item  ( edit ) 항목 수정 ");
        System.out.println("4. List all items ( ls ) 전체 보기 ");
        System.out.println("5. sort the list by name ( ls_name_asc ) 제목순 정렬 ");
        System.out.println("6. sort the list by name ( ls_name_desc ) 제목순 역정렬 ");
        System.out.println("7. sort the list by date ( ls_date ) 날짜순 정렬 ");
        System.out.println("8. exit (Or press escape key to exit) 종료 ");
        System.out.println("Enter your choice >");
    }
    public static void prompt() {
    	System.out.println("\nCommand > ");
    }
}
