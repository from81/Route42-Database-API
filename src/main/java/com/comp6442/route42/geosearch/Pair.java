package com.comp6442.route42.geosearch;

public class Pair<T_node, T_distance> {
    private T_node node;
    private T_distance distance;

    Pair(T_node node, T_distance distance) {
        this.node = node;
        this.distance = distance;
    }

    public T_node getNode(){
        return this.node;
    }

    public T_distance getDistance(){
        return this.distance;
    }
}
