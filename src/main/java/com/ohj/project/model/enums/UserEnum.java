package com.ohj.project.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户枚举
 *
 * @author ohj
 */
public enum UserEnum {

    ONLINE("已登陆", "1"),
    OFFLINE("未登陆", "0");

    private final String text;

    private final String value;

    UserEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
