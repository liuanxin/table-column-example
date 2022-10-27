package com.github.liuanxin.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateUtil {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final String CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final String SUR_NAME = "赵钱孙李周吴郑王冯陈杨朱秦尤许何吕施张孔曹严华谢邹章云苏潘范彭林孔邓韦昌马许于乔马高";
    private static final String GIRL_NAME = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月媛艳凡佳嘉琼勤珍莉桂娣妍茜秋珊莎锦羽希宁欣育柔竹亚宜枝思丽";
    private static final String BOY_NAME = "伟刚勇毅俊峰强军平东文辉力明永健世广志义兴良海山仁波宁贵福龙元全国胜学祥发武新利承乐绍功松善厚庆朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树德行时泰";

    public static String toPhone() {
        String temp = "358";
        return "1" + temp.charAt(RANDOM.nextInt(temp.length())) + RANDOM.nextInt(10)
                + String.format("%04d", RANDOM.nextInt(10000))
                + String.format("%04d", RANDOM.nextInt(10000));
    }

    public static String toEmail() {
        StringBuilder sbd = new StringBuilder();
        int length = RANDOM.nextInt(6, 18);
        for (int i = 0; i < length; i++) {
            sbd.append(CHAR.charAt(RANDOM.nextInt(CHAR.length())));
        }
        String[] emailArr = "@gmail.com,@msn.com,@live.com,@qq.com,@163.com,@126.com".split(",");
        sbd.append(emailArr[RANDOM.nextInt(emailArr.length)]);
        return sbd.toString();
    }

    public static String toName() {
        StringBuilder sbd = new StringBuilder();
        sbd.append(SUR_NAME.charAt(RANDOM.nextInt(SUR_NAME.length())));
        boolean male = RANDOM.nextBoolean();
        int num = RANDOM.nextInt(2) + 1;
        for (int i = 0; i < num; i++) {
            String name = (male ? BOY_NAME : GIRL_NAME);
            sbd.append(name.charAt(RANDOM.nextInt(name.length())));
        }
        return sbd.toString();
    }

    public static String toCode(String prefix) {
        String num = String.format("%05d", RANDOM.nextInt(100000));
        String today = new SimpleDateFormat("yyyy-MM-dd").format(toDate(120));
        String no = today + "-" + num;
        return (prefix == null || prefix.isEmpty()) ? (today + "-" + no) : (prefix + "-" + no);
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
        return RANDOM.nextInt(1, max);
    }

    public static long toLong(long max) {
        return RANDOM.nextLong(1, max);
    }

    public static BigDecimal toDecimal(double max, int scale) {
        return BigDecimal.valueOf(RANDOM.nextDouble(0.01D, max)).setScale(scale, RoundingMode.DOWN);
    }

    public static Date toDate(int ago) {
        long ms = System.currentTimeMillis();
        long r = RANDOM.nextInt(ago * 24 * 60 * 60) * 1000L;
        return new Date(ms - r);
    }
}
