package com.ttth.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.ttth.item.Author;
import com.ttth.item.Book;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 04/04/2016.
 */
public class SqliteManager {
    private static final String TABLE_NAME = "AUTHOR";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_SERI = "SERI";
    private static final String COLUMN_NAME = "NAME";
    private static final String TABLE_BOOK = "BOOK";
    private static final String COLUMN_BOOK_ID = "ID";
    private static final String COLUMN_BOOK_SERI = "SERIAUTHOR";
    private static final String COLUMN_BOOK_TITLE = "TITLE";
    private static final String COLUMN_BOOK_DATE = "DATE";
    private Context mContext;
    private SQLiteDatabase database;
    public static final String PATH = Environment.getDataDirectory().getPath()+"/data/com.ttth.quanlysach/database/QUANLYSACH.sqlite";
    public SqliteManager(Context mContext) {
        this.mContext = mContext;
        readDatabase(mContext);
    }

    public void readDatabase(Context mContext) {
        try {
            InputStream inputStream = mContext.getAssets().open("QUANLYSACH.sqlite");
            File file = new File(PATH);
            if (!file.exists()){
                File parent = file.getParentFile();
                parent.mkdirs();
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                byte []b= new byte[1024];
                int count = inputStream.read(b);
                while (count!=-1){
                    outputStream.write(b,0,count);
                    count = inputStream.read(b);
                }
                outputStream.close();
            }
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void openDatabase(){
        database = mContext.openOrCreateDatabase(PATH,mContext.MODE_APPEND,null);
    }
    public void closeDatabase(){
        database.close();
    }
    public ArrayList<Author> getData(){
        ArrayList<Author>arrAuthor = new ArrayList<Author>();
        openDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null,null);
        cursor.moveToFirst();
        int indextID = cursor.getColumnIndex(COLUMN_ID);
        int indextSeri = cursor.getColumnIndex(COLUMN_SERI);
        int intdextName = cursor.getColumnIndex(COLUMN_NAME);
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(indextID);
            String seri = cursor.getString(indextSeri);
            String name = cursor.getString(intdextName);
            Author author = new Author(id,seri,name);
            arrAuthor.add(author);
            cursor.moveToNext();
        }
        closeDatabase();
        return arrAuthor;
    }
    public ArrayList<Book> getDataBook(){
        ArrayList<Book>arrBook = new ArrayList<Book>();
        openDatabase();
        Cursor cursor = database.query(TABLE_BOOK, null, null, null, null, null, null);
        cursor.moveToFirst();
        int indextID = cursor.getColumnIndex(COLUMN_BOOK_ID);
        int indextSeriAuthor = cursor.getColumnIndex(COLUMN_BOOK_SERI);
        int indextTitle = cursor.getColumnIndex(COLUMN_BOOK_TITLE);
        int indextDate = cursor.getColumnIndex(COLUMN_BOOK_DATE);
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(indextID);
            String seriAuthor = cursor.getString(indextSeriAuthor);
            String title = cursor.getString(indextTitle);
            String date = cursor.getString(indextDate);
            Book book = new Book(id,seriAuthor,title,date);
            arrBook.add(book);
            Log.e("==============", "" + id + seriAuthor + title + date);
            cursor.moveToNext();
        }
        closeDatabase();
        return arrBook;
    }
    public long insertData(String seri,String name){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SERI, seri);
        contentValues.put(COLUMN_NAME, name);
        openDatabase();
        long id = database.insert(TABLE_NAME, null, contentValues);
        closeDatabase();
        return id;
    }
    public long insertBook(String seri,String title,String date){
        ContentValues contentValuesBook = new ContentValues();
        contentValuesBook.put(COLUMN_BOOK_SERI,seri);
        contentValuesBook.put(COLUMN_BOOK_TITLE, title);
        contentValuesBook.put(COLUMN_BOOK_DATE, date);
        openDatabase();
        long id = database.insert(TABLE_BOOK,null,contentValuesBook);
        closeDatabase();
        return id;
    }
//    public ArrayList<Author> loadBookOfAuthor(String loadData){
//        ArrayList<Author>arrAuthor = new ArrayList<Author>();
//        String query = "Select"+
//    }
    public int updateData(Author author){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SERI,author.getSeri());
        contentValues.put(COLUMN_NAME,author.getName());
        openDatabase();
        String[] selectionArg = {author.getId()+""};
        int row = database.update(TABLE_NAME, contentValues, COLUMN_ID +"=?", selectionArg);
        closeDatabase();
        return row;
    }
    public int deleteData(int id){
        openDatabase();
        String[] selectionArg={id+""};
        int row = database.delete(TABLE_NAME,COLUMN_ID+"=?",selectionArg);
        closeDatabase();
        return row;
    }
}
