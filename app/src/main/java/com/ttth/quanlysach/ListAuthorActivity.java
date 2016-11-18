package com.ttth.quanlysach;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ttth.database.SqliteManager;
import com.ttth.item.Author;

import java.util.ArrayList;

import adapter.AuthorAdapter;

/**
 * Created by Administrator on 05/04/2016.
 */
public class ListAuthorActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener{
    private ArrayList<Author>arrAuthor;
    private AuthorAdapter authorAdapter;
    private ListView lvListAuthor;
    private SqliteManager manager;
    private Dialog updateDialog;
    private EditText edtUpdateSeri,edtUpdateName;
    private TextView tvUpdateClear,tvUpdate;
    private int id1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_author);
        manager = new SqliteManager(this);
        arrAuthor = new ArrayList<Author>();
        arrAuthor.addAll(manager.getData());
        Log.e("@@@@@@@",""+arrAuthor);
        initView();
        initDialog();
    }

    private void initDialog() {
        updateDialog = new Dialog(this,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        LayoutInflater inflater = getLayoutInflater();
        View updateView = inflater.inflate(R.layout.edit_author_dialog,null);
        edtUpdateSeri = (EditText) updateView.findViewById(R.id.edtUpdateSeri);
        edtUpdateName = (EditText) updateView.findViewById(R.id.edtUpdateName);
        tvUpdateClear = (TextView) updateView.findViewById(R.id.tvUpdateClear);
        tvUpdate = (TextView) updateView.findViewById(R.id.tvUpdate);
        updateDialog.setCancelable(false);
        updateDialog.setContentView(updateView);
        tvUpdateClear.setOnClickListener(this);
        tvUpdate.setOnClickListener(this);
    }

    private void initView() {
        authorAdapter = new AuthorAdapter(this,android.R.layout.simple_list_item_1,arrAuthor);
        lvListAuthor = (ListView) this.findViewById(R.id.lvListAuthor);
        lvListAuthor.setAdapter(authorAdapter);
        lvListAuthor.setOnItemClickListener(this);
        lvListAuthor.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvUpdateClear:
                edtUpdateSeri.setText("");
                edtUpdateName.setText("");
                break;
            case R.id.tvUpdate:
                updateAuthor();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        updateDialog.show();
        edtUpdateSeri.setText(arrAuthor.get(position).getSeri());
        edtUpdateName.setText(arrAuthor.get(position).getName());
        id1 = arrAuthor.get(position).getId();
    }
    private void updateAuthor(){
        String updateSeri = edtUpdateSeri.getText().toString();
        String updateName = edtUpdateName.getText().toString();
        if (updateSeri.isEmpty()||updateName.isEmpty()){
            Toast.makeText(this, getResources().getText(R.string.empty), Toast.LENGTH_LONG).show();
        }else {
            Author author = new Author(id1,updateSeri,updateName);
            manager.updateData(author);
            arrAuthor.clear();
            arrAuthor.addAll(manager.getData());
            authorAdapter.notifyDataSetChanged();
            updateDialog.dismiss();
            Toast.makeText(this, getResources().getText(R.string.update_success),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        String nameAuthor = arrAuthor.get(position).getSeri()+"-"+arrAuthor.get(position).getName();
        AlertDialog.Builder builder = new AlertDialog.Builder(ListAuthorActivity.this);
        builder.setTitle(getResources().getText(R.string.remove));
        builder.setMessage(getResources().getString(R.string.delete) + nameAuthor + "] ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               int id = arrAuthor.get(position).getId();
                manager.deleteData(id);
                arrAuthor.clear();
                arrAuthor.addAll(manager.getData());
                authorAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return true;
    }
}
