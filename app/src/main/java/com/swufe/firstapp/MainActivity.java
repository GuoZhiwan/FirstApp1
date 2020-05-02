package com.swufe.firstapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  {

    TextView out;
    EditText inp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        out=findViewById(R.id.showText);
        inp=findViewById(R.id.inpText);
        Button btn=findViewById(R.id.btn1);
        //btn.setOnClickListener(this);
       /* btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            Log.i("mail","onclick called....");
                String str=inp.getText().toString();
                out.setText("Hello "+str);
            }
        });*/
    }
        public void btnClick(View btn){
        String str=inp.getText().toString();
        double c=Double.parseDouble(str);
        double f=c*9/5+32;
        Log.i("main",f+"℉");
        out.setText(f+"℉");
        }
}
