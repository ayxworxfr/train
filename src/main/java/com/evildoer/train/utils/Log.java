package com.evildoer.train.utils;

/**
 * Log
 *
 * @author evildoer
 * @date 2023/7/22
 * @version v1.0
 */
public class Log {
    /**
     * 日志打印
     *
     * @param pattern 输出内容
     * @param args 参数
     */
    public static void print(Object pattern, Object... args) {
        String result = pattern.toString();
        StringBuilder builder = new StringBuilder();
        int start = 0;
        for (Object arg : args) {
            int j = result.indexOf("{}", start);
            if (j == -1) {
                break;
            }
            builder.append(result, start, j).append(arg.toString());
            start = j + 2;
        }
        builder.append(result.substring(start));

        System.out.println(builder);
    }
}