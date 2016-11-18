package com.ttth.quanlysach;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ttth.database.SqliteManager;
import com.ttth.item.Author;
import com.ttth.item.Book;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import adapter.AuthorAdapter;
import adapter.BookAdapter;

/**
 * Created by Administrator on 06/04/2016.
 */
public class BookManager extends Activity implements View.OnClickListener{
    public static final  String KEY_SERIAUTHOR = "seri author" ;
    private Spinner spAuthor;
    private EditText edtNameBook;
    private Button btnSelectDate;
    private TextView tvAddBook,tvDate;
    private ListView lvListBook;
    private SqliteManager manager;
    private ArrayList<Author>arrAuthor;
    private ArrayList<Book>arrBook;
    private ArrayList<Book>arrBookOfAuthor;
    private Calendar calendar;
    private BookAdapter bookAdapter;
    private String seri,seriAuthor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_manage_book);
        manager = new SqliteManager(this);
        arrAuthor = new ArrayList<Author>();
        arrBook = new ArrayList<Book>();
        arrAuthor.addAll(manager.getData());
        arrBook.addAll(manager.getDataBook());
        Log.e("+++++++++++++++++", "" + arrBook);
        initView();
        loadAuthor();
    }

    private void initView() {
        spAuthor = (Spinner) this.findViewById(R.id.spAuthor);
        edtNameBook = (EditText) this.findViewById(R.id.edtNameBook);
        tvDate = (TextView) this.findViewById(R.id.tvDate);
        btnSelectDate = (Button) this.findViewById(R.id.btnSelectDate);
        tvAddBook = (TextView) this.findViewById(R.id.tvAddBook);
        lvListBook = (ListView) this.findViewById(R.id.lvListBook);

        bookAdapter = new BookAdapter(this,android.R.layout.simple_list_item_1,arrBook);
        lvListBook.setAdapter(bookAdapter);
        tvAddBook.setOnClickListener(this);
        btnSelectDate.setOnClickListener(this);

        spAuthor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seri = arrAuthor.get(position).getSeri();
                arrBookOfAuthor = new ArrayList<Book>();
                arrBookOfAuthor.clear();
                for (int i = 0; i < arrBook.size(); i++) {
                    if (arrAuthor.get(position).getSeri().equals(arrBook.get(i).getSeriAuthor())){
                        arrBookOfAuthor.add(arrBook.get(i));
                    }
                }
                bookAdapter = new BookAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrBookOfAuthor);
                lvListBook.setAdapter(bookAdapter);
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadAuthor() {
        ArrayAdapter<Author> dataAuthor = new ArrayAdapter<Author>(this,android.R.layout.simple_spinner_item,arrAuthor);
        dataAuthor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAuthor.setAdapter(dataAuthor);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSelectDate:
                showDatePikerDialog();
                break;
            case R.id.tvAddBook:
                addBook();
                break;
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String mDay = String.valueOf(dayOfMonth);
            String mMonth = String.valueOf(monthOfYear+1);
            String mYear  = String.valueOf(year);
            tvDate.setText(mDay + "-" + mMonth + "-" + mYear);
        }
    };

    private void showDatePikerDialog() {
        calendar = Calendar.getInstance(TimeZone.getDefault());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.AppTheme,dateSetListener,year,month,day);
        datePickerDialog.setCancelable(false);
        datePickerDialog.setTitle("Chọn ngày xuất bản");
        datePickerDialog.show();
    }
    private void addBook(){
        String nameBook = edtNameBook.getText().toString();
        if (nameBook.isEmpty()){
            Toast.makeText(this,"Tên sách trống",Toast.LENGTH_LONG).show();
        }else {
            manager.insertBook(seri, nameBook, "" + tvDate.getText());
            Log.e("Book", "" + seri + nameBook + tvDate.getText());
            arrBook.clear();
            arrBook.addAll(manager.getDataBook());
            initView();
            bookAdapter.notifyDataSetChanged();
            Toast.makeText(this,"Thêm thành công",Toast.LENGTH_LONG).show();
        }
    }

}
