package com.swufe.firstapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfoQueryActivity extends AppCompatActivity {
    public final String TAG = "InfoQueryActivity";
    EditText str;
    String keyWord = str.getText().toString();
    public String updateDate = "";
    Handler handler;
    TextView show;
    Bundle bundle=new Bundle();
    private ArrayList<String> listItems;
    private SimpleAdapter listItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_query);

        str = findViewById(R.id.info_text);
        //获取sp里的数据
        SharedPreferences sharedPreferences = getSharedPreferences("info", Activity.MODE_PRIVATE);
        updateDate = sharedPreferences.getString("update_date", "");
        int up_date = Integer.getInteger(updateDate.substring(8));
        //获取当前系统时间
        Calendar RealToday = Calendar.getInstance();
        Date realTodayTime=RealToday.getTime();
        Calendar today = Calendar.getInstance();
        //设置为一周之前的时间，用于比较
        today.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH) - 7);
        Date before_today = today.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String RealTime=sdf.format(realTodayTime);
        final String todayStr = sdf.format(before_today);
        final String before_todayStr = (sdf.format(before_today).substring(8));
        final int before_todayInt = Integer.parseInt(before_todayStr);
        if (up_date < before_todayInt) {
            Log.i(TAG, "onCreate: 需要更新");
            //开启子线程
            Thread t = new Thread((Runnable) this);
            t.start();
        } else {
            Log.i(TAG, "onCreate: 不需要更新");
        }
        //保存到sp中
        SharedPreferences sp = getSharedPreferences("info", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("update_date",RealTime);
        editor.apply();
    }
    public Bundle getFromInfo(){

        Document doc= null;
        try {
            doc = Jsoup.connect("https://it.swufe.edu.cn/index/tzgg.htm").get();
            Log.i(TAG, "run: "+ doc.title());
            Elements scripts =doc.getElementsByTag("scripts");
            Element script6=scripts.get(6);
            //获取td中的数据
            Elements spans= script6.getElementsByTag("span");
            for(int i=0;i<spans.size();i+=2) {
                Element span = spans.get(i);
                String str1 = span.text();
                ArrayList list1 = new ArrayList<String>();
                list1.add(str1);
                bundle.putParcelableArrayList("list1", list1);
                Pattern pn = Pattern.compile(keyWord + "\\w|\\w" + keyWord + "\\w|\\w" + keyWord);
                Matcher mr = null;
                mr = pn.matcher(str1);
                if (mr.find()) {
                    ArrayList list2 = new ArrayList<String>();
                    list2.add(str1);
                    bundle.putParcelableArrayList("list2", list2);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bundle;
    }
    public void  run(){
        ArrayList list = bundle.getParcelableArrayList("list2");
        List list3= (List<String>) list.get(0);
        for(Object s:list3){
            listItems.add(s.toString());
        }
        ListView listView=findViewById(R.id.mylist);
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,listItems);
        listView.setAdapter(adapter);

    }
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){


    }
}

