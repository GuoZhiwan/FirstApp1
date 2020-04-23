package com.swufe.firstapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MyListctivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_listctivity);
        ListView listView=findViewById(R.id.mylist);
        String data[]={"111","222","333"};
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data);
        listView.setAdapter(adapter);

    }
}
