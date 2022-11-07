package com.github.example;

import com.github.liuanxin.query.util.IdUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class IdTest {

    private static final int COUNT = 1000000;
    private static final int CPU_NUM = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(
            CPU_NUM,
            CPU_NUM + 1,
            5L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>()
    );
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("start : " + FORMATTER.format(LocalDateTime.ofInstant(new Date(start).toInstant(), ZONE_ID)));

        List<Callable<Long>> callList = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            callList.add(IdUtil::nextId);
        }
        List<Future<Long>> futures = THREAD_POOL.invokeAll(callList, 1, TimeUnit.MINUTES);
        Set<Long> set = ConcurrentHashMap.newKeySet();
        for (Future<Long> future : futures) {
            set.add(future.get());
        }
        long end = System.currentTimeMillis();
        System.out.printf("time  : %sms\n", ((end - start) / 1000.0));
        System.out.println("end   : " + FORMATTER.format(LocalDateTime.ofInstant(new Date(end).toInstant(), ZONE_ID)));
        System.out.println("------------------");
        System.out.println("all   : " + COUNT);
        System.out.println("real  : " + set.size());

        int i = 0;
        for (Long s : set) {
            if (i >= 20) {
                return;
            }
            i++;
            System.out.println(s);
        }
        System.out.println("------------------");
        boolean flag = THREAD_POOL.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println(flag);
    }
}
