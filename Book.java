package com.kushal.LibraryManagement;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Book {
    private int book_id;
    private String title;
    private String author;
    private String publisher;
    private int quantity;

    public int getBook_id() {
        return book_id;
    }
    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    static void displayAllBooks() throws SQLException {
        ResultSet rs = Database.getBooks();

        String format = "| %-8s | %-25s | %-20s | %-20s | %-8s |\n";
        String separator = "+----------+---------------------------+----------------------+----------------------+----------+\n";

        System.out.println(separator);
        System.out.printf(format, "Book ID", "Title", "Author", "Publisher", "Quantity");
        System.out.println(separator);

        assert rs != null;
        if (rs.next()) {
            while (rs.next()) {
                System.out.printf(format,
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getInt("quantity")
                );
            }
        } else {
            System.out.println("| No books found in the database. |");
        }
        System.out.println(separator);
    }

    static void addBook(){
        Book b = new Book();
        Scanner sc = new Scanner(System.in);
        System.out.println("--Add a new Book--");
        System.out.println("Enter Book Details");

        System.out.print("Enter Title: ");
        b.setTitle(sc.nextLine());
        System.out.print("Enter Author: ");
        b.setAuthor(sc.nextLine());
        System.out.print("Enter Publisher: ");
        b.setPublisher(sc.nextLine());
        System.out.print("Enter Quantity: ");
        b.setQuantity(sc.nextInt());

        System.out.println(b.getBook_id());
        try {
            Database.addBook(b);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    static void updateBookQuantity(){
        Book b = new Book();
        Scanner sc = new Scanner(System.in);
        System.out.println("--Update Quantity of a Book--");
        System.out.println("Enter Book Details");

        System.out.print("Enter Book ID: ");
        b.setBook_id(sc.nextInt());
        System.out.print("Enter Updated Quantity: ");
        b.setQuantity(sc.nextInt());

        try {
            Database.updateBookQuantity(b);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteBook() {
        Book b = new Book();
        Scanner sc = new Scanner(System.in);
        System.out.println("--Delete a Book--");
        System.out.println("Enter Book Details");

        System.out.print("Enter Book ID: ");
        b.setBook_id(sc.nextInt());

        try {
            Database.deleteBook(b);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
