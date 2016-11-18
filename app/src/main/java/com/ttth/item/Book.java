package com.ttth.item;

import java.util.ArrayList;

/**
 * Created by Administrator on 06/04/2016.
 */
public class Book {
    private int id;
    private String seriAuthor;
    private String title;
    private String date;
    private ArrayList<Book>arrBook;
    public Book(int id, String seriAuthor, String title, String date) {
        this.id = id;
        this.seriAuthor = seriAuthor;
        this.title = title;
        this.date = date;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                        "\n seriAuthor"+seriAuthor+
                "\n title=" + title +
                "\n date=" + date;
    }

    public ArrayList<Book> getArrBook() {
        return arrBook;
    }

    public int getId() {
        return id;
    }

    public String getSeriAuthor() {
        return seriAuthor;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
