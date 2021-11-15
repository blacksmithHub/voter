package com.example.libor.voter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sqlitelib.DataBaseHelper;
import com.sqlitelib.SQLite;

import java.util.ArrayList;
import java.util.Arrays;

public class adminPage extends AppCompatActivity {

    EditText name, post, num;
    Button add, cand, logout;
    Intent list, out;
    String positionError;

    ArrayList<String> candidates;

    private DataBaseHelper dbhelper = new DataBaseHelper(adminPage.this, "voterDatabase", 2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        name = (EditText)findViewById(R.id.txtName);
        post = (EditText)findViewById(R.id.txtPosition);
        num = (EditText)findViewById(R.id.txtNum);

        add = (Button)findViewById(R.id.add);
        cand = (Button)findViewById(R.id.canditates);
        logout = (Button)findViewById(R.id.logout);

        candidates = new ArrayList();

        out = new Intent(adminPage.this, MainActivity.class);
        list = new Intent(adminPage.this, listview.class);

        name();
        post();
        num();

        add();
        cand();
        out();

        refreshall();


    }

    private list getList()
    {
        list list =new list();
        list.setList(candidates);
        return list;
    }

    private void sendData()
    {
        list.putExtra("list",this.getList());
        startActivity(list);
    }

    private void setdefault(){
        name.setText("");
        post.setText("");
        num.setText("");
    }

    private void refreshall(){
        setdefault();
        reload();
        reloadList();
    }

    private void reloadList(){
        try {
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            Cursor list = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='tblcandidates'", null);
            list.moveToNext();
            if (list.getCount() == 0) {
                SQLite.FITCreateTable("voterDatabase", this, "tblcandidates",
                        " id INTEGER(90) AUTO-INCREMENT PRIMARY KEY," +
                                "name VARCHAR(90), position VARCHAR(90), number INTEGER(90)");
            }else {
                list = db.rawQuery("SELECT * FROM tblcandidates", null);
                String value[] = new String[list.getCount()];
                int ctrl = 0;
                candidates.clear();
                while (list.moveToNext()) {
                    String strFor = "";
                    strFor += System.lineSeparator() + "Name: " + list.getString(list.getColumnIndex("name"));
                    strFor += System.lineSeparator() + "Position: " + list.getString(list.getColumnIndex("position"));
                    strFor += System.lineSeparator() + "Number: " + list.getString(list.getColumnIndex("number"));
                    value[ctrl] = strFor;
                    candidates.add(strFor);
                    ctrl++;
                }
            }
        } catch (Exception e) {
            Toast.makeText(adminPage.this,""+e,Toast.LENGTH_LONG).show();
        }
    }

    private void reload(){
        try {
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            Cursor candids = db.rawQuery("SELECT * FROM sqlite_master WHERE type='table' AND name='tblcandidates'", null);
            candids.moveToNext();
            if (candids.getCount() == 0) {
                SQLite.FITCreateTable("voterDatabase", this, "tblcandidates",
                        " id INTEGER AUTO-INCREMENT PRIMARY KEY," +
                        "name VARCHAR(90), position VARCHAR(90), number INTEGER");
            }

        } catch (Exception e) {
            Toast.makeText(adminPage.this,""+e,Toast.LENGTH_LONG).show();
        }
    }

    private void out(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void cand(){
        cand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
    }

    private void add(){
        final SQLiteDatabase db = dbhelper.getWritableDatabase();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    try {
                        String query = "Select position FROM tblcandidates WHERE position = '" + post.getText().toString() + "'";
                        Cursor cursor = db.rawQuery(query, null);

                        if (cursor.moveToFirst()) {
                            cursor.moveToFirst();
                            positionError = cursor.getString(0);
                        }
                    } catch (Exception e) {
                        positionError = "default";
                    }

    if(post.getText().toString().equals(positionError)){
        Toast.makeText(adminPage.this,""+ post.getText().toString() + "already registered!",Toast.LENGTH_LONG).show();
    }else{
        try {
            String sqlStr = "INSERT INTO tblcandidates (name, position, number) VALUES ('"
                    + name.getText().toString() + "', '" + post.getText().toString() + "', '" +
                    +Integer.parseInt(num.getText().toString()) + "')";
            db.execSQL(sqlStr);
        }catch (Exception e){
                    Toast.makeText(adminPage.this,"" + e,Toast.LENGTH_LONG).show();
                }
        refreshall();
        //Toast.makeText(adminPage.this,"inserted",Toast.LENGTH_LONG).show();
    }



            }
        });
    }

    private void num(){
        num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(num.length() != 0) {
                } else {
                    if(num.getText().length() == 0) {
                        num.setError("This field cannot be blank");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void post(){
        post.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(post.length() != 0) {
                } else {
                    if(post.getText().length() == 0) {
                        post.setError("This field cannot be blank");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void name(){
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(name.length() != 0) {
                } else {
                    if(name.getText().length() == 0) {
                        name.setError("This field cannot be blank");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
