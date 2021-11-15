package com.example.libor.voter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class listview extends AppCompatActivity {

    ListView listview;
    Intent out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        out = new Intent(listview.this, adminPage.class);

        listview = (ListView)findViewById(R.id.list);

        receiveData();

    }

    private void receiveData(){
        Intent i=this.getIntent();
        list list = (list) i.getSerializableExtra("list");
        listview.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, list.getList()));
    }

    @Override
    public void onBackPressed() {
        out.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(out);
        listview.super.finish();
    }
}
