package com.ttth.quanlysach;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ttth.database.SqliteManager;
import com.ttth.item.Author;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener{
    private SqliteManager manager;
    private ArrayList<Author>arrAuthor;
    private Dialog addAuthorDialog;
    private TextView tvAddAuthor,tvName,tvManageBook,tvClear,tvSaveAuthor;
    private Button btnCancel;
    private EditText edtSeriAuthor,edtNameAuthor;
    private boolean isCheck = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new SqliteManager(this);
        arrAuthor = new ArrayList<Author>();
        arrAuthor.addAll(manager.getData());
        initDialog();
        initView();
    }

    private void initView() {
        tvAddAuthor = (TextView) this.findViewById(R.id.tvAddAuthor);
        tvName = (TextView) this.findViewById(R.id.tvName);
        tvManageBook = (TextView) this.findViewById(R.id.tvManageBook);
        btnCancel = (Button) this.findViewById(R.id.btnCancel);
        tvAddAuthor.setOnClickListener(this);
        tvName.setOnClickListener(this);
        tvManageBook.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void initDialog() {
        addAuthorDialog = new Dialog(this,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);

        LayoutInflater inflater = getLayoutInflater();
        View addView = inflater.inflate(R.layout.add_author_dialog, null);

        edtSeriAuthor = (EditText) addView.findViewById(R.id.edtSeriAuthor);
        edtNameAuthor = (EditText) addView.findViewById(R.id.edtNameAuthor);
        tvClear = (TextView) addView.findViewById(R.id.tvClear);
        tvSaveAuthor = (TextView) addView.findViewById(R.id.tvSaveAuthor);

        addAuthorDialog.setCancelable(false);
        addAuthorDialog.setContentView(addView);
        tvClear.setOnClickListener(this);
        tvSaveAuthor.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvAddAuthor:
                addAuthorDialog.show();
                break;
            case R.id.tvName:
                Intent intent = new Intent(this,ListAuthorActivity.class);
                startActivity(intent);
                break;
            case R.id.tvManageBook:
                Intent intentMain = new Intent(this,BookManager.class);
                startActivity(intentMain);
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.tvSaveAuthor:
                addAuthor();
                break;
            case R.id.tvClear:
                edtNameAuthor.setText("");
                edtSeriAuthor.setText("");
                break;
        }
    }
    private void addAuthor(){
        String seriAuthor= edtSeriAuthor.getText().toString();
        String nameAuthor = edtNameAuthor.getText().toString();
        if (seriAuthor.isEmpty()||nameAuthor.isEmpty()) {
            Toast.makeText(this, getResources().getText(R.string.empty), Toast.LENGTH_LONG).show();
        }else {
//            if (isCheck){
//                for (Author author:arrAuthor) {
//                    if (seriAuthor.equals(author.getSeri())) {
//                        Toast.makeText(this,"mã tác giải bị trùng",Toast.LENGTH_LONG).show();
//                    }else if (nameAuthor.equals(author.getName())){
//                        Toast.makeText(this,"tên tác giải bị trùng",Toast.LENGTH_LONG).show();
//                    }
//                }
                manager.insertData(seriAuthor,nameAuthor);
                Toast.makeText(this, getResources().getText(R.string.add_success),Toast.LENGTH_LONG).show();
                addAuthorDialog.dismiss();
//            }
        }
    }
}
