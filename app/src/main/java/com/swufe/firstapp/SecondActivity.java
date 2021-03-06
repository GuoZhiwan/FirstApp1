package com.swufe.firstapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    TextView score;
    TextView score2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        score = findViewById(R.id.score);
        score2 = findViewById(R.id.score2);
    }
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        String scorea=((TextView)findViewById(R.id.score)).getText().toString();
        String scoreb=((TextView)findViewById(R.id.score2)).getText().toString();
        outState.putString("teama_score",scorea);
        outState.putString("teamb_score",scoreb);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea=savedInstanceState.getString("teama_score");
        String scoreb=savedInstanceState.getString("teamb_score");
        ((TextView)findViewById(R.id.score)).setText(scorea);
        ((TextView)findViewById(R.id.score2)).setText(scoreb);
    }

    public void btnAdd1(View btn) {
        if(btn.getId()==R.id.btn_1) {
            showScore(1);
        }else{
            showScore2(1);
        }
    }

    public void btnAdd2(View btn) {
        if(btn.getId()==R.id.btn_2) {
            showScore(2);
        }else{
            showScore2(2);
        }
    }

    public void btnAdd3(View btn) {
        if(btn.getId()==R.id.btn_3) {
            showScore(3);
        }else{
            showScore2(3);
        }
    }

    public void btnReset(View btn) {
        score.setText("0");
        score2.setText("0");
    }
    private void showScore(int inc){
        Log.i("show","inc="+inc);
        String oldscore=(String)score.getText();
        int newscore=Integer.parseInt(oldscore)+inc;
        score.setText(""+newscore);

    }
    private void showScore2(int inc){
        Log.i("show","inc="+inc);
        String oldscore=(String)score2.getText();
        int newscore=Integer.parseInt(oldscore)+inc;
        score2.setText(""+newscore);

    }
}