package org.example.statemachine;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataStorage {
    private Map<String, Object> data;

    public DataStorage(){
        data = new HashMap<>();
    }
    public void add(String key, Object value){
        data.put(key, value); // добавить значения
    }
    public Object get(String key){
        return data.get(key);
    }
    public void delete(String key){
        data.remove(key);
    }
    public void clear(){
        data.clear();
    }
}
