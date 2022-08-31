package com.cisdi.data.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtils {

    /**
     * 集合按长度分组
     * @param list 集合
     * @param size 分割大小，100则为每100条数据为一组
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> partition(final List<T> list, final int size) {
        if (list == null) {
            throw new IllegalArgumentException("List must not be null");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        List<List<T>> result = new ArrayList<>();
        Iterator<T> it = list.iterator();
        List<T> subList = null;
        while (it.hasNext()) {
            if (subList == null) {
                subList = new ArrayList<>();
            }
            T t = it.next();
            subList.add(t);
            if (subList.size() == size) {
                result.add(subList);
                subList = null;
            }
        }
        //补充最后一页
        if (subList != null) {
            result.add(subList);
        }
        return result;
    }

    /**
     * 集合按长度分组
     * @param list 集合
     * @param size  分割大小，100则为每100条数据为一组
     * @return <T>
     */
    public static <T> List<List<T>> split(List<T> list, int size) {
        if (list == null) {
            throw new IllegalArgumentException("List must not be null");
        }
        if (size <= 0) {
            throw new RuntimeException("Size must be greater than 0");
        }
        return Stream.iterate(0, n -> n + 1).limit((list.size() + size - 1) / size).
                map(a -> list.stream().skip((long) a * size).limit(size).collect(Collectors.toList())).
                collect(Collectors.toList());
    }

    public static void main(String[] args) {
//        String[] abc = {"1","2","3","4","5","6","7","8","9"};
//        List<String> res = Arrays.asList(abc);
//        List<List<String>> resList = ListUtils.split(res,7);
//        List<List<String>> resList1 = ListUtils.partition(res,8);
//        System.out.println("111111111111");
        AtomicInteger index = new AtomicInteger(0);
        for (int i = 0; i < 10; i++) {
            System.out.println(index.getAndIncrement());
        }
    }
}
