package com.swufe.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class RateActivity extends AppCompatActivity {
    EditText rmb;
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rmb=findViewById(R.id.rmb);
        show=findViewById(R.id.showout);

    }
    public void onClick(View btn){
        String str=rmb.getText().toString();
        float r=0;
        if(str.length()>0){
            r=Float.parseFloat(str);
        }else{
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }
        float val=0;
     if(btn.getId()==R.id.button){
          val=r*(1/6.7f);
     }else if(btn.getId()==R.id.button2){
          val=r*(1/11.0f);
     }else if(btn.getId()==R.id.button3){
          val=r*500f;

     }  show.setText(String.valueOf(new DecimalFormat("##.##").format(val)));

    }
}
