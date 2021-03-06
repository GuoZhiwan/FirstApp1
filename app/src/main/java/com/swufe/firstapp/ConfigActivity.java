package com.swufe.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ConfigActivity extends AppCompatActivity {
    public final String TAG="ConfigActivity";
    EditText dollarText;
    EditText euroText;
    EditText wonText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Intent config=getIntent();
        float dollar2=config.getFloatExtra("dollar_rate_key",0.0f);
        float euro2=config.getFloatExtra("euro_rate_key",0.0f);
        float won2=config.getFloatExtra("won_rate_key",0.0f);
        Log.i(TAG, "onCreate:dollar2= "+dollar2);
        Log.i(TAG, "onCreate:euro2= "+euro2);
        Log.i(TAG, "onCreate:won2= "+won2);
        dollarText=(EditText) findViewById(R.id.dollar_rate);
        euroText=(EditText) findViewById(R.id.euro_rate);
        wonText=(EditText) findViewById(R.id.won_rate);
        //显示数据到控件
        dollarText.setText(String.valueOf(dollar2));
        euroText.setText(String.valueOf(euro2));
        wonText.setText(String.valueOf(won2));
    }
    public void Save(View btn){
        Log.i("cfg", "Save: ");
        //获取新的数据
        float newDollar=Float.parseFloat(dollarText.getText().toString());
        float newEuro=Float.parseFloat(euroText.getText().toString());
        float newWon=Float.parseFloat(wonText.getText().toString());
        Log.i(TAG, "Save:获取到新的值");
        Log.i(TAG, "Save:newDollar= "+newDollar);
        Log.i(TAG, "Save:newEuro="+newEuro);
        Log.i(TAG, "Save:newWon="+newWon);
        //保存到bundle或放入到extra
        Intent intent=getIntent();
        Bundle bd1=new Bundle();
        bd1.putFloat("key_dollar",newDollar);
        bd1.putFloat("key_euro",newEuro);
        bd1.putFloat("key_won",newWon);
        intent.putExtras(bd1);
        setResult(2,intent);

        //返回到调用页面
        finish();
    }
}
