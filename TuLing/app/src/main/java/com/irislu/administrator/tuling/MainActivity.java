package com.irislu.administrator.tuling;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity implements HttpGetDataListener,View.OnClickListener {
    private HttpData httpData;
    private List<ListData> lists;
    private ListView lv;
    private EditText sendText;
    private Button send_btn;
    private String content_str;
    private TextAdapter adapter;
    private String []welcome_array;
    private double currentTime,oldTime = 0;

    @Override
    public void getDataInfo(String info) {
        System.out.println(info);
        parseText(info);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }
    private void initView() {
        sendText = (EditText) findViewById(R.id.sendText);
        send_btn = (Button) findViewById(R.id.send_btn);
        lv = (ListView) findViewById(R.id.lv);
        lists = new ArrayList<>();
        send_btn.setOnClickListener(this);
        adapter = new TextAdapter(lists,this);
        lv.setAdapter(adapter);
        ListData listData;
        listData = new ListData(getRandomWelcomeTips(),ListData.RECEIVE,getTime());
        lists.add(listData);
    }


    public void parseText(String info) {


        try {
            JSONObject jb = new JSONObject(info);
            System.out.println(jb.getString("code"));
            System.out.println(jb.getString("text"));
            ListData listData = new ListData(jb.getString("text"),ListData.RECEIVE,getTime());
            lists.add(listData);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        getTime();
        content_str = sendText.getText().toString();
        sendText.setText("");


            String dropk = content_str.replace(" ", "");
            String droph = dropk.replace("\n", "");
            ListData listData = new ListData(content_str, ListData.SEND,getTime());
            lists.add(listData);
            if (lists.size() > 30) {
                for (int i = 0; i < lists.size(); i++) {
                    lists.remove(i);
                }
            }
            adapter.notifyDataSetChanged();

            httpData = (HttpData) new HttpData("{“key”:“APIKEY”,“info”:" + droph + "}", this).execute();


    }
    private String getRandomWelcomeTips() {
        String welcome_tip = null;
        welcome_array = this.getResources().getStringArray(R.array.welcome_tips);
        int index = (int) (Math.random()*(welcome_array.length-1));
        welcome_tip = welcome_array[index];
        return welcome_tip;
    }
    private String getTime() {
        currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        if (currentTime - oldTime >= 5*60*1000) {
            oldTime = currentTime;
            return str;
        }else {
            return "";
        }

    }
}

