package com.ttth.item;

/**
 * Created by Administrator on 04/04/2016.
 */
public class Author {
    private int id;
    private String seri;
    private String name;

    public Author(int id, String seri, String name) {
        this.id = id;
        this.seri = seri;
        this.name = name;
    }

    @Override
    public String toString() {
        return
                "" + id +
                "  " + seri +
                "  " + name ;

    }

    public int getId() {
        return id;
    }

    public String getSeri() {
        return seri;
    }

    public String getName() {
        return name;
    }
}
