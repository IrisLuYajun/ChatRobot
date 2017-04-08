package com.irislu.administrator.tuling;

/**
 * Created by Administrator on 2016-5-25.
 */
public class ListData {
    private String content;
    public static final int SEND = 1;
    public static final int RECEIVE = 2;
    private int flag;
    private String time;
    public ListData(String content,int flag,String time) {
        this.content = content;
        this.flag = flag;
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
