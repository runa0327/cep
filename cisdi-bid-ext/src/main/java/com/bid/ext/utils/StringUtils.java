package com.bid.ext.utils;

import com.qygly.shared.util.SharedUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtils {

    /**
     * 将 ids 字符串解析为 ID 列表。
     *
     * @param ids 以逗号分隔的 ID 字符串。
     * @return 去除空格后的 ID 列表。
     */
    public static List<String> parseIds(String ids) {
        if (SharedUtil.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return Arrays.stream(ids.split(","))
                .map(String::trim)
                .filter(id -> !id.isEmpty())
                .collect(Collectors.toList());
    }
}
