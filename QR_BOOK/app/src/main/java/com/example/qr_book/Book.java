package com.example.qr_book;

public class Book {
    public String uid;
    public String name;
    public String year;
    public String author;

    // Constructor vacio requerido por Firestore
    public Book() {}

    public Book(String uid, String name, String year, String author) {
        this.uid = uid;
        this.name = name;
        this.year = year;
        this.author = author;
    }
}