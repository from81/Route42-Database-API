package com.comp6442.route42.geosearch;

public class Pair<T_key, T_value> {
    private T_key key;
    private T_value value;

    Pair(T_key key, T_value value) {
        this.key = key;
        this.value = value;
    }

    public T_key getKey(){
        return this.key;
    }

    public T_value getValue(){
        return this.value;
    }
}
