package com.swufe.firstapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class RateActivity extends AppCompatActivity {
    EditText rmb;
    TextView show;
    public final String TAG="RateActivity";
    public float dollarRate=1.0f;
    public float euroRate=2.0f;
    public float wonRate=3.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rmb=(EditText) findViewById(R.id.rmb);
        show=(TextView) findViewById(R.id.showout);


    }
    public void onClick(View btn){
        String str=rmb.getText().toString();

        float r=0;
        if(str.length()>0){
            r=Float.parseFloat(str);
        }else{
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }
        if(btn.getId()==R.id.button){
            show.setText(String.format("%.2f",r*dollarRate));
        }else if (btn.getId()==R.id.button2){
            show.setText(String.format("%.2f",r*euroRate));
        }else{
            show.setText(String.format("%.2f",r*wonRate));
        }
    /*    float val=0;
     if(btn.getId()==R.id.button){
          val=r*(1/6.7f);
     }else if(btn.getId()==R.id.button2){
          val=r*(1/11.0f);
     }else if(btn.getId()==R.id.button3){
          val=r*500f;

     }  show.setText(String.valueOf(new DecimalFormat("##.##").format(val)));

*/
    }
    public void openOne(View btn){
        //打开一个页面
        /*Log.i("open","openone");
        Intent hello=new Intent(RateActivity.this,SecondActivity.class);

        Intent web=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));

        Intent call=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:13134643234"));
        startActivity(call);
        finish();
         */
        Intent config=new Intent(this,ConfigActivity.class);
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);
        Log.i(TAG, "openOne:dollar_rate_key="+dollarRate);
        Log.i(TAG, "openOne:euro_rate_key="+euroRate);
        Log.i(TAG, "openOne:won_rate_key="+wonRate);
        //startActivity(config);
        startActivityForResult(config,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==2){
            Bundle bundle=data.getExtras();
            dollarRate=bundle.getFloat("key_dollar",0.1f);
            euroRate=bundle.getFloat("ket_euro",0.1f);
            wonRate=bundle.getFloat("key_won",0.1f);
            Log.i(TAG, "onActivityResult:dollarRate ="+dollarRate);
            Log.i(TAG, "onActivityResult:euroRate ="+euroRate);
            Log.i(TAG, "onActivityResult:wonRate ="+wonRate);
            
            
        }
    }
}
