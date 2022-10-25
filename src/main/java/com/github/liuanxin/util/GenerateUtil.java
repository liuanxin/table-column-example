package com.github.liuanxin.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class GenerateUtil {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final AtomicLong ATOMIC_LONG = new AtomicLong(100000);

    private static final String CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final List<String> NAMES = Arrays.asList(
            "林木", "孔剑", "邓舷", "邓龄", "许涛", "于山", "乔恒", "邹航", "龚海", "张拯",
            "李心水", "曾雨华", "谭高义", "杜鸿光", "蔡正谊", "文德元", "张明旭", "龙修平", "贺德运", "马展鹏",
            "赵冬", "苏俞", "高娣", "丁咏", "田凤", "程荷", "丁璇", "潘斐", "冯水", "张婉",
            "孔晓芬", "常冰之", "罗文茵", "钟柔惠", "丁卿云", "李芸莹", "钟雪柳", "钱文英", "乔思萌", "孔海萍"
    );

    private static final List<String> SHOP_NAMES = Arrays.asList(
            "谷之商铺", "帝狼商铺", "红世峰商铺", "燃点商铺", "零距离食品城", "亿廊商铺",
            "艾贝特服装", "天阳商铺", "牛转乾坤商铺", "奥威通华商铺", "光艺商铺", "亮化商铺"
    );

    public static String toName() {
        return NAMES.get(RANDOM.nextInt(NAMES.size()));
    }

    public static String toShop() {
        return SHOP_NAMES.get(RANDOM.nextInt(SHOP_NAMES.size()));
    }

    public static String toCode(String prefix) {
        long num = ATOMIC_LONG.incrementAndGet();
        return (prefix == null || prefix.isEmpty()) ? String.valueOf(num) : (prefix + "-" + num);
    }

    public static String toVarchar(int count) {
        StringBuilder sbd = new StringBuilder();
        int size = CHAR.length();
        for (int i = 0; i < count; i++) {
            sbd.append(CHAR.charAt(RANDOM.nextInt(size)));
        }
        return sbd.toString();
    }

    public static boolean toBoolean() {
        return RANDOM.nextBoolean();
    }

    public static int toInt(int max) {
        return Math.abs(RANDOM.nextInt(max));
    }

    public static long toLong(long max) {
        return Math.abs(RANDOM.nextLong(max));
    }

    public static BigDecimal toDecimal(double max) {
        return new BigDecimal(String.valueOf(Math.abs(RANDOM.nextDouble(max))));
    }

    public static Date toDate(int ago) {
        long ms = System.currentTimeMillis();
        long r = RANDOM.nextInt(ago * 24 * 60 * 60) * 1000L;
        return new Date(ms - r);
    }
}
