package com.swufe.firstapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InfoListActivity extends ListActivity implements Runnable, AdapterView.OnItemClickListener {
    private String TAG="infoList";
    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;//存放文字、图片等信息
    private SimpleAdapter listItemAdapter;//适配器
    public void run(){
        List<HashMap<String,String>> retlist=new ArrayList<HashMap<String,String>>();

        //获取网络数据，放入list带回到主线程中
        Document doc= null;
        try {
            doc = Jsoup.connect("https://it.swufe.edu.cn/index/tzgg.htm").get();
            Log.i(TAG, "run: "+ doc.title());
            Elements script =doc.getElementsByTag("script");
            Element script1=script.get(9);
            //获取td中的数据
            Elements title=script1.getElementsByTag("title");
            Elements html=script1.getElementsByTag("href");
            HashMap<String,String>map=new HashMap<String,String>();
            for(int i=0;i<title.size();i++){
                Element title1=title.get(i);
                String result=title.text();
                Log.i(TAG, "run: result="+result);
                map.put("ItemTitle",result);

            }
            for (int i=0;i<html.size();i++) {
                Element html1 = html.get(i);
                String address = html.text();
                map.put("ItemDetail",address);
            }
                retlist.add(map);
            } catch (IOException ex) {
            ex.printStackTrace();
        }

        Message msg = handler.obtainMessage(5);
        msg.obj=retlist;
        handler.sendMessage(msg);
    }
    private void initListView(){
        listItems=new ArrayList<HashMap<String, String>>();
        for(int i=0;i<10;i++){
            HashMap<String,String> map=new HashMap<String,String>();
            map.put("ItemTitle","title "+i);
            map.put("ItemDetail","address"+i);
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        listItemAdapter=new SimpleAdapter(this,listItems,
                R.layout.list_item,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail}
        );

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        MyAdapter myAdapter=new MyAdapter(this,R.layout.list_item,listItems);
        this.setListAdapter(myAdapter);
        Thread t = new Thread(this);
        t.start();

        handler=new Handler(){
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==5){
                    List<HashMap<String,String>> list2=(List<HashMap<String,String>>)msg.obj;
                    listItemAdapter=new SimpleAdapter(InfoListActivity.this,list2,
                            R.layout.list_item,
                            new String[]{"ItemTitle","ItemDetail"},
                            new int[]{R.id.itemTitle,R.id.itemDetail}
                    );
                    setListAdapter(listItemAdapter);

                }
                super.handleMessage(msg);
            }
        };
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: parent="+parent);
        Log.i(TAG, "onItemClick: view="+view);
        Log.i(TAG, "onItemClick: position="+position);
        Log.i(TAG, "onItemClick: id="+id);
        HashMap<String,String>map=(HashMap<String,String>)getListView().getItemAtPosition(position);
        String titleStr=map.get("ItemTitle");
        String detailStr=map.get("ItemDetail");
        Log.i(TAG, "onItemClick: "+titleStr);
        Log.i(TAG, "onItemClick: "+detailStr);
        TextView title=view.findViewById(R.id.itemTitle);
        TextView detail=view.findViewById(R.id.itemDetail);
        String title2=String.valueOf(title.getText());
        String detail2=String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2"+title2);
        Log.i(TAG, "onItemClick: detail2"+detail2);
        //打开新的页面传入
        Intent rateCalc=new Intent(this,InfoQueryActivity.class);
        rateCalc.putExtra("title",titleStr);
        rateCalc.putExtra("detail",detailStr);
        startActivity(rateCalc);

    }
}

