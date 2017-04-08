package com.irislu.administrator.tuling;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import javax.xml.transform.Result;


/**
 * Created by Administrator on 2016-5-25.
 */
public class HttpData  extends AsyncTask<String,Void,String>{
    public static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String API_KEY = "2ff560a4c730e527f260c5c11e9b9c9d";
    private String response;
    private HttpURLConnection connection;
    private String info;//请求数据
    private HttpGetDataListener listener;

    public HttpData(String info,HttpGetDataListener listener) {
        this.info = info;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL mUrl = new URL(URL);
            connection = (HttpURLConnection) mUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码

            out.write(setParams(info).toString());
            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(),"utf-8");
                response.append(lines);
            }
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.getDataInfo(s);
    }

    public static JSONObject setParams(String info) {
            /*try{
                info = URLEncoder.encode(info,"UTF-8");
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "{key:"+API_KEY+","+"info:"+info+"}";**/

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key",API_KEY);
            jsonObject.put("info", info);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}