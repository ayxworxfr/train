package com.evildoer.train.utils;

/**
 * @author: evildoer
 * @date: 2023/7/22
 * @description:
 */
public class Log {
    /**
     * 日志打印
     *
     * @param pattern 输出内容
     * @param args 参数
     */
    public static void print(String pattern, Object... args) {
        StringBuilder builder = new StringBuilder();
        int start = 0;
        for (Object arg : args) {
            int j = pattern.indexOf("{}", start);
            if (j == -1) {
                break;
            }
            builder.append(pattern, start, j).append(arg.toString());
            start = j + 2;
        }
        builder.append(pattern.substring(start));

        System.out.println(builder);
    }
}