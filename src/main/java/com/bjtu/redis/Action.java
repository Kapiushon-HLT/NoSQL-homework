package com.bjtu.redis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public class Action {
    private String name;
    private ArrayList<String> retrieve;
    private ArrayList<String> save;

    public String getName() {
        return name;
    }

    public ArrayList<String> getRetrieve() {
        return retrieve;
    }

    public ArrayList<String> getSave() {
        return save;
    }

    public Action(JSONObject obj) {
        retrieve=new ArrayList<>();
        save=new ArrayList<>();
        name=obj.getString("name");
        JSONArray array=obj.getJSONObject("feature_retrieve").getJSONArray("counters");
        for(Object x : array)
        {
            JSONObject xx=(JSONObject)x;
            retrieve.add(xx.getString("counterName"));
        }
        if(obj.containsKey("save_counter"))
        {
            array=obj.getJSONObject("save_counter").getJSONArray("counters");
            for(Object x : array)
            {
                JSONObject xx=(JSONObject)x;
                save.add(xx.getString("counterName"));
            }
        }
    }

    @Override
    public String toString() {
        return "Action{" +
                "name='" + name + '\'' +
                ", retrieve=" + retrieve +
                ", save=" + save +
                '}';
    }
}
