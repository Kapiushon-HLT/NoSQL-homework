package com.bjtu.redis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public class Counter {
    private String name;//名字
    private String index;//索引
    private int value;
    private String type;//存储类型
    private ArrayList<String> key;//统计key
    private String Freq;//对不同周期进行统计


    //将JSON对象的数据赋给实体类
    public Counter(JSONObject obj){
        name=obj.getString("counterName");
        index=obj.getString("counterIndex");
        JSONArray array = obj.getJSONArray("keyFields");
        key=new ArrayList<>();
        for (Object s: array) {
            key.add((String)s);
        }
        type=obj.getString("type");
        value=0;

        if(obj.containsKey("valueFields"))
        {
            value=obj.getInteger("valueFields");
        }
        if(obj.containsKey("FREQFields"))
        {
            Freq=obj.getString("FREQFields");
        }
    }

    public String getName() {
        return name;
    }

    public String getIndex() {
        return index;
    }

    public ArrayList<String> getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public String getFreq() {
        return Freq;
    }


}
