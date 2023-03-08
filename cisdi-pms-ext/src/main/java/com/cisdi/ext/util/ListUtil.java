package com.cisdi.ext.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author dlt
 * @date 2023/3/8 周三
 */
public class ListUtil {
    /**
     *
     * @param list 待分割的链表
     * @param size 需要分割的长度
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> partition(List<T> list,int size){
        if (list == null){
            throw new IllegalArgumentException("待分割的链表为空！");
        }
        if (0 >= size){
            throw new IllegalArgumentException("分割长度必须大于0！");
        }
        //待返回链表
        List<List<T>> resultLists = new ArrayList<>();

        Iterator<T> iterator = list.iterator();
        List<T> element = null;

        //迭代固定长度size
        while (iterator.hasNext()){
            if (null == element){
                element = new ArrayList<>();
            }
            element.add(iterator.next());
            if (element.size() == size){
                resultLists.add(element);
                element = null;
            }
        }

        //处理可能出现的最后一段数据
        if (element != null){
            resultLists.add(element);
        }

        return resultLists;
    }

    public static void main(String[] args) {
        List<String> strings = Arrays.asList("1", "2", "3", "4", "5");
        List<List<String>> partition = partition(strings, 2);
        System.out.println();

//        List<String> strings = new ArrayList<>();
//        strings.add("1");
//        strings.add("2");
//        strings.add("3");
//        strings.add("4");
//        strings.add("5");
//        List<String> newStrings = strings;
//        strings = null;
//        System.out.println();
    }
}
