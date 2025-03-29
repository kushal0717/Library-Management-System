package com.kushal.LibraryManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private int userId;
    private String name;
    private String email;
    private String phone;

    static Scanner scanner = new Scanner(System.in);

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Method to add a new user
    static void addUser(){
        User u = new User();
        Scanner sc = new Scanner(System.in);
        System.out.println("--Add a new User--");
        System.out.println("Enter Book Details");

        System.out.print("Enter Name: ");
        u.setName(sc.nextLine());
        System.out.print("Enter Email: ");
        u.setEmail(sc.nextLine());
        System.out.print("Enter Phone: ");
        u.setPhone(sc.nextLine());


        try {
            Database.addUser(u);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Method to display all users
    static void displayAllUsers() throws SQLException {
        ResultSet rs = Database.getUsers();

        String format = "| %-8s | %-25s | %-20s | %-20s |\n";
        String separator = "+----------+---------------------------+----------------------+----------------------+\n";

        System.out.println(separator);
        System.out.printf(format, "User ID", "Name", "Email", "Phone");
        System.out.println(separator);

        assert rs != null;
        if (rs.next()) {
            while (rs.next()) {
                System.out.printf(format,
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
            }
        } else {
            System.out.println("| No users found in the database. |");
        }
        System.out.println(separator);
    }


    // Method to delete a user
    static void deleteUser() throws SQLException {
        System.out.print("Enter User ID to delete: ");
        int userId = scanner.nextInt();

        int result = Database.deleteUser(userId);
        if (result > 0) {
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("User ID not found.");
        }
    }

    static void issueBook(int loggedInUserId){
        System.out.println("Enter Book ID You want to issue: ");
        Scanner sc = new Scanner(System.in);
        int book_id=sc.nextInt();
        Database.issueBook(loggedInUserId,book_id);
    }
    static void returnBook(int loggedInUserId){
        System.out.println("Enter Book ID You want to return: ");
        Scanner sc = new Scanner(System.in);
        int book_id=sc.nextInt();
        Database.returnBook(loggedInUserId,book_id);
    }
}
