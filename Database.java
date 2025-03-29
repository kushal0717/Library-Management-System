package com.kushal.LibraryManagement;

import java.sql.*;

public class Database {
    static Connection conn = null;

    static Connection getConnection() throws SQLException,ClassNotFoundException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/LibraryDB";
            String username = "root";
            String password = "1234";
            conn = DriverManager.getConnection(url, username, password);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    static void closeConnection(Connection conn)  {
        try {
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Connection closed!! ");
    }

    static ResultSet getBooks() {

        Connection conn = null;
        try {

            conn = Database.getConnection();
            String query = "SELECT * FROM books";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            return preparedStatement.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        if (conn != null) {
            Database.closeConnection(conn);
        }
        return null;
    }

    static void addBook(Book b) throws SQLException{
        Connection conn=null;

        int rowsAff = 0;
        String query = "INSERT INTO books (title, author, publisher, quantity) VALUES (?, ?, ?, ?)";
        try {
            conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1,b.getTitle());
            preparedStatement.setString(2,b.getAuthor());
            preparedStatement.setString(3,b.getPublisher());
            preparedStatement.setInt(4,b.getQuantity());

            rowsAff = preparedStatement.executeUpdate();


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(rowsAff == 0){
            System.out.println("Failed to Insert!! ");
        } else {
            System.out.println("Inserted Successfully!! ");
        }
        if (conn != null) {
            Database.closeConnection(conn);
        }
    }

    static void updateBookQuantity(Book b) throws SQLException{
        Connection conn=null;

        int rowsAff = 0;
        String query = "UPDATE books SET quantity = ? where book_id = ?";
        try {
            conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1,b.getQuantity());
            preparedStatement.setInt(2,b.getBook_id());


            rowsAff = preparedStatement.executeUpdate();


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(rowsAff == 0){
            System.out.println("Failed to Update!! ");
        } else {
            System.out.println("Updated Successfully!! ");
        }
        if (conn != null) {
            Database.closeConnection(conn);
        }
    }

    public static void deleteBook(Book b) throws SQLException{

        String query = "DELETE FROM books WHERE book_id = ?";
        int rowsAff = 0 ;
        try{
            conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1,b.getBook_id());
            rowsAff = preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(rowsAff == 0){
            System.out.println("Failed to Delete!! ");
        } else {
            System.out.println("Deleted Successfully!! ");
        }
        if (conn != null) {
            Database.closeConnection(conn);
        }

    }

    public static void addUser(User u) throws SQLException{

        Connection conn = null;
        PreparedStatement pstmt = null;

        int rowsAff = 0;
        String query = "INSERT INTO users (name, email, phone) VALUES (?, ?, ?)";
        try {
            conn = Database.getConnection();
            if(conn != null) {
                pstmt = conn.prepareStatement(query);

                pstmt.setString(1, u.getName());
                pstmt.setString(2, u.getEmail());
                pstmt.setString(3, u.getPhone());

                rowsAff = pstmt.executeUpdate();
            }

        } catch (SQLException  | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(rowsAff == 0){
            System.out.println("Failed to Insert!! ");
        } else {
            System.out.println("Inserted Successfully!! ");
        }
        if (conn != null) {
            Database.closeConnection(conn);
        }

    }

    public static ResultSet getUsers() {
        Connection conn = null;
        try {

            conn = Database.getConnection();
            String query = "SELECT * FROM users";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            return preparedStatement.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        if (conn != null) {
            Database.closeConnection(conn);
        }
        return null;
    }

    public static int deleteUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        int rowsAff = 0;
        try{
            conn = Database.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (PreparedStatement pstmt = conn.prepareStatement(query))
        {
            conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            rowsAff = pstmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return rowsAff;
    }

    public static boolean isValidUser(int userId) {
        String query = "SELECT COUNT(*) FROM users WHERE user_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true; // User exists
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false; // User does not exist
    }

    public static void issueBook(int userId, int bookId) {
        String checkQuery = "SELECT quantity FROM books WHERE book_id = ?";
        String insertQuery = "INSERT INTO transactions (user_id, book_id, issue_date) VALUES (?, ?, CURDATE())";
        String updateQuery = "UPDATE books SET quantity = quantity - 1 WHERE book_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt("quantity") > 0) {
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, bookId);
                insertStmt.executeUpdate();

                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();
                System.out.println("Book issued successfully!");
            } else {
                System.out.println("Book is not available.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void returnBook(int userId, int bookId) {
        String checkQuery = "SELECT transaction_id FROM transactions WHERE user_id = ? AND book_id = ? AND return_date IS NULL";
        String updateTransactionQuery = "UPDATE transactions SET return_date = CURDATE() WHERE transaction_id = ?";
        String updateBookQuery = "UPDATE books SET quantity = quantity + 1 WHERE book_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement updateTransactionStmt = conn.prepareStatement(updateTransactionQuery);
             PreparedStatement updateBookStmt = conn.prepareStatement(updateBookQuery)) {

            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int transactionId = rs.getInt("transaction_id");

                updateTransactionStmt.setInt(1, transactionId);
                updateTransactionStmt.executeUpdate();

                updateBookStmt.setInt(1, bookId);
                updateBookStmt.executeUpdate();
                System.out.println("Book returned successfully!");
            } else {
                System.out.println("No active issue record found for this book and user.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void viewTransactions() {
        String query = "SELECT t.transaction_id, u.name AS user_name, b.title AS book_title, " +
                "t.issue_date, t.return_date " +
                "FROM transactions t " +
                "JOIN users u ON t.user_id = u.user_id " +
                "JOIN books b ON t.book_id = b.book_id " +
                "ORDER BY t.issue_date DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=== Transaction History ===");
            System.out.printf("%-5s %-20s %-30s %-12s %-12s%n",
                    "ID", "User", "Book", "Issue Date", "Return Date");
            System.out.println("----------------------------------------------------------------------");

            while (rs.next()) {
                int transactionId = rs.getInt("transaction_id");
                String userName = rs.getString("user_name");
                String bookTitle = rs.getString("book_title");
                String issueDate = rs.getString("issue_date");
                String returnDate = rs.getString("return_date");

                System.out.printf("%-5d %-20s %-30s %-12s %-12s%n",
                        transactionId, userName, bookTitle, issueDate,
                        (returnDate != null ? returnDate : "Not Returned"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void viewIssuedBooks(int userId) {
        String query = "SELECT b.book_id, b.title, b.author, t.issue_date, t.return_date " +
                "FROM transactions t " +
                "JOIN books b ON t.book_id = b.book_id " +
                "WHERE t.user_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nYour Issued Books:");
            System.out.printf("%-5s %-30s %-20s %-12s %-12s\n", "ID", "Title", "Author", "Issue Date", "Return Date");
            System.out.println("--------------------------------------------------------------");

            boolean hasBooks = false;
            while (rs.next()) {
                hasBooks = true;
                System.out.printf("%-5d %-30s %-20s %-12s %-12s\n",
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDate("issue_date"),
                        rs.getDate("return_date") != null ? rs.getDate("return_date") : "Not Returned");
            }

            if (!hasBooks) {
                System.out.println("No books issued.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}




