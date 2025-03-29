package com.kushal.LibraryManagement;

import java.sql.*;
import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static boolean isAdmin = false;
    static int loggedInUserId = -1;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to Library Management System");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    userLogin();
                    break;
                case 3:
                    System.out.println("Exiting... Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void adminLogin() {
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();
        if (password.equals("admin123")) {  // Set a secure password
            isAdmin = true;
            showAdminMenu();
        } else {
            System.out.println("Incorrect password. Returning to main menu.");
        }
    }

    static void userLogin() {
        System.out.print("Enter your User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        if (Database.isValidUser(userId)) {
            loggedInUserId = userId;
            showUserMenu(userId);
        } else {
            System.out.println("User not found. Please try again.");
        }
    }

    static void showAdminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. View Books");
            System.out.println("4. Add User");
            System.out.println("5. Remove User");
            System.out.println("6. View Users");
            System.out.println("7. View Transactions");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Book.addBook();
                    break;
                case 2:
                    Book.deleteBook();
                    break;
                case 3:
                    try {
                        Book.displayAllBooks();
                    } catch (SQLException e) {
                        System.out.println("Display Failed!! ");
                    }
                    break;
                case 4:
                       User.addUser();
                       break;
                case 5:
                    try {
                        User.deleteUser();
                    } catch (SQLException e) {
                        System.out.println("Display Failed!! ");
                    }
                    break;
                case 6:
                    try {
                        User.displayAllUsers();
                    } catch (SQLException e) {
                        System.out.println("Display Failed!! ");
                    }
                    break;
                case 7:
                    Database.viewTransactions();
                    break;
                case 8:
                    System.out.println("Logging out...");
                    isAdmin = false;
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void showUserMenu(int userId) {
        while (true) {
            System.out.println("\nUser Menu");
            System.out.println("1. View Available Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. View Your Issued Books");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    try {
                        Book.displayAllBooks();
                    } catch (SQLException e) {
                        System.out.println("Error fetching books.");
                    }
                    break;
                case 2:
                    System.out.print("Enter Book ID to Issue: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();
                    Database.issueBook(userId, bookId);
                    break;
                case 3:
                    System.out.print("Enter Book ID to Return: ");
                    int returnBookId = scanner.nextInt();
                    scanner.nextLine();
                    Database.returnBook(userId, returnBookId);
                    break;
                case 4:
                    Database.viewIssuedBooks(userId);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
