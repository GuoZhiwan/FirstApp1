package com.swufe.firstapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RateListActivity extends ListActivity implements Runnable {
    String data[]={"wait......"};
    Handler handler;
    private String logDate="";
    private final String DATE_SP_KEY="lastRateDateStr";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp=getSharedPreferences("myrate", Context.MODE_PRIVATE);
        logDate=sp.getString(DATE_SP_KEY,"");

        //setContentView(R.layout.activity_rate_list);
        final List<String> list1=new ArrayList<String>();
        for(int i=1;i<100;i++){
            list1.add("item"+i);
        }

        final ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data);
        setListAdapter(adapter);
        Thread t=new Thread(this);
        t.start();


        handler=new Handler(){
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==5){
                    List<String> list2=(List<String>)msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_expandable_list_item_1,list2);
                    setListAdapter(adapter);

                }
                super.handleMessage(msg);
            }
        };

    }
    public void run() {
        List<String> retlist = new ArrayList<String>();
        String curDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        Log.i("run", "curDateStr:" + curDateStr + "logDate" + logDate);
        if (curDateStr.equals(logDate)) {
            //如果相等，不从网络中获得数据
            Log.i(TAG, "run: 日期相等，从数据库中获取数据");
            RateManager manager=new RateManager(RateListActivity.this);
            for(RateItem item:manager.listall()){
                retlist.add(item.getCurName()+"-->"+item.getCurRate());
            }

        } else {
            Log.i(TAG, "run: 日期不相等，从网络中获取在线数据");

            //获取网络数据，放入list带回到主线程中
            Document doc = null;
            try {
                List<RateItem> rateList = new ArrayList<RateItem>();
                doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
                Log.i(TAG, "run: " + doc.title());
                Elements tables = doc.getElementsByTag("table");
                Element table6 = tables.get(1);
                //获取td中的数据
                Elements tds = table6.getElementsByTag("td");

                for (int i = 0; i < tds.size(); i += 8) {
                    Element td1 = tds.get(i);
                    Element td2 = tds.get(i + 5);
                    String str1 = td1.text();
                    String val = td2.text();
                    Log.i(TAG, "run: " + td1.text() + "==>" + td2.text());
                    retlist.add(str1 + "==>" + val);
                    rateList.add(new RateItem(str1, val));
                }
                //把数据写入数据库中
                RateManager manager = new RateManager(RateListActivity.this);
                manager.deleteAll();
                Log.i(TAG, "run: 删除所有记录");
                manager.addAll(rateList);
                Log.i(TAG, "run: 添加所有记录");


            } catch (IOException e) {
                e.printStackTrace();
            }
            //更新日期
            SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(DATE_SP_KEY, curDateStr);
            edit.commit();
            Log.i(TAG, "run: 更新日期结束：" + curDateStr);
        }
            Message msg = handler.obtainMessage(5);
            msg.obj = retlist;
            handler.sendMessage(msg);
    }
}
