# Library Management System

## Overview

The **Library Management System** is a Java-based application using **JDBC** and **MySQL** to manage books, users, and transactions efficiently. This system provides administrative functionalities for managing books and users while allowing users to issue, return, and view their issued books.

## Features

### Admin Features
- Add, delete, and view books  
- Add, delete, and view users  
- View all transactions  

### User Features
- Issue and return books  
- View their issued books  

### Database Integration
- Uses **MySQL** as the backend database  
- **JDBC** for database connectivity  

## Technologies Used

- **Programming Language**: Java  
- **Database**: MySQL  
- **Libraries**: JDBC  
- **IDE**: IntelliJ IDEA  
- **Version Control**: Git & GitHub  

## Setup Instructions

### Prerequisites
- Install **Java JDK 8+**  
- Install **MySQL Server** and create a database  
- Install **IntelliJ IDEA** or any preferred Java IDE  
- Install **Git** and configure it  

### Clone the Repository
```sh
git clone https://github.com/kushal0717/Library-Management-System.git
cd Library-Management-System
```

### Database Setup
Open MySQL and create a database:
```sh
CREATE DATABASE LibraryDB;
Import the SQL file provided in the project (if available) or manually create tables:

USE LibraryDB;

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    available BOOLEAN DEFAULT TRUE
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    book_id INT,
    issue_date DATE,
    return_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);
```
Configure Database Connection
Update the Database.java file with your MySQL credentials:
 

# Compile and run App.java

### Usage
Admin Login: Access administrative functionalities.

User Login: Issue and return books.

View Issued Books: Users can check which books they have borrowed.

### Contributing
Feel free to fork the repository and submit pull requests with improvements.

### License
This project is open-source and available under the MIT License.

### Contact
For any queries or contributions, reach out via GitHub Issues.
