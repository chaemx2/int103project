-- สร้างฐานข้อมูล
CREATE DATABASE library;

-- ใช้ฐานข้อมูลที่สร้างขึ้น
USE library;

-- สร้างตารางสำหรับผู้ใช้
CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    isAdmin BOOLEAN NOT NULL
);

-- สร้างตารางสำหรับหนังสือ
CREATE TABLE books (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    amount INTEGER NOT NULL
);

-- สร้างตารางสำหรับการยืมหนังสือ
CREATE TABLE borrow_records (
    userId VARCHAR(50) NOT NULL,
    bookId VARCHAR(50) NOT NULL,
    borrowDate DATE NOT NULL,
    dueDate DATE NOT NULL,
    PRIMARY KEY (userId, bookId),
    FOREIGN KEY (userId) REFERENCES users(username),
    FOREIGN KEY (bookId) REFERENCES books(id)
);

-- สร้างตารางสำหรับหมวดหมู่หนังสือ
CREATE TABLE categories (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);