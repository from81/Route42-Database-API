package com.comp6442.route42.geosearch;

import java.util.Comparator;
import java.util.*;
import java.util.Random;

public class QuickSelect {

    public static <T> T select(List<T> list, int left, int right, int n, Comparator<? super T> cmp){
//        System.out.println(" select : (left=" + left + ", right=" + right + ", n=" + n +")");
        while(true){
            if(left == right) {
//                System.out.println(" left=" + left + " == right=" + right + " return " + list.get(left));
                return list.get(left);
            }
            int pivot = pivotIndex(left, right);
            pivot = partition(list, left, right, pivot, cmp);
            if(n == pivot){
//                System.out.println(" n=" + n + " == pivot=" + pivot + " return " + list.get(n));
                return list.get(n);
            }
            else if(n < pivot){
//                System.out.println(" n=" + n + " < pivot=" + pivot + " right=" + (pivot - 1));
                right = pivot - 1;
            }
            else{
//                System.out.println(" n=" + n + " >= pivot=" + pivot + " left=" + (pivot + 1));
                left = pivot + 1;
            }
        }
    }

    private static <T> int partition(List<T> list, int left, int right, int pivot, Comparator<? super T> cmp){
//        System.out.println(" partition : (left=" + left + ", right=" + right + ", pivot=" + pivot +")");
        T pivotValue = list.get(pivot);
//        System.out.println(" pivotValue=" + pivotValue);
//        System.out.print(" swap : (pivot=" + pivot + ", right=" + right + ")");
        swap(list, pivot, right);
        int store = left;
        for (int i = left; i < right; i++){
            if(cmp.compare(list.get(i), pivotValue) < 0){
//                System.out.print(" swap : (store=" + store + ", i=" + i + ")");
                swap(list, store, i);
                ++store;
            }
            else{
//                System.out.print(" pass : (store=" + store + ", i=" + i + ")");
//                System.out.println(" " + store + "-th:" + list.get(store) + " >=< " + i + "-th:" + list.get(i));
            }
        }
//        System.out.print(" swap : (right=" + right + ", store=" + store + ")");
        swap(list, right, store);
//        System.out.println(" return store=" + store);
        return store;
    }

    private static <T> void swap(List<T> list, int i, int j){
//        System.out.println(" " + i + "-th:" + list.get(i) + " <=> " + j + "-th:" + list.get(j));
        T value = list.get(i);
        list.set(i, list.get(j));
        list.set(j, value);
    }

    private static int pivotIndex(int left, int right){
        return left + (right-left)/2;
    }
}
