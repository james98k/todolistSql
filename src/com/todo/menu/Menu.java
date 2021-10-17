package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println("0. help(help)");
        System.out.println("1. Add a new item ( add )");
        System.out.println("2. Delete an existing item ( del )");
        System.out.println("3. Delete multi item! (del_multi)");
        System.out.println("4. Update an item  ( edit )");
        System.out.println("5. List all items ( ls )");
        System.out.println("6. sort the list by name ( ls_name_asc )");
        System.out.println("7. sort the list by name ( ls_name_desc )");
        System.out.println("8. sort the list by date ( ls_date_asc )");
        System.out.println("9. sort the list by category (ls_cate)");
        System.out.println("10. sort the list by date (ls_name)");
        System.out.println("11. find keyword from database (find)");
        System.out.println("12. find category from database (find_cate)");
        System.out.println("13. set your todoItem to complete (comp)");
        System.out.println("14. set multiple complete! (comp_multi)");
        System.out.println("15. show completed tasks (show_comp)");
        
        System.out.println("16. show all users of this todolist! (show_all_user)");
        System.out.println("17. show current user (show_current_user)");
        System.out.println("18. change user of the todolist! (set_user)");
        
        System.out.println("19. exit todolist (exit)");
        
        System.out.println("Enter your choice >");
        
    }
}
