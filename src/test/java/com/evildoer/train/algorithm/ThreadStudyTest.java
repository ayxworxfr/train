package com.evildoer.train.algorithm;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThreadStudyTest {
    class PrintRunnable implements Runnable {
        private int count = 0;
        private char[] source = "ab".toCharArray();
        @Override
        public void run() {
            int sourceCount = source.length;
            while (true) {
                synchronized (this) {
                    this.notify();
                    if (count >= 5) {
                        break;
                    }
                    System.out.println(source[count % sourceCount]);
                    count++;
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Test
    public void test() {
        PrintRunnable runnable = new PrintRunnable();
        Thread thread = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread.start();
        thread2.start();
    }

    @Test
    @SneakyThrows
    public void calculateNum() {
        String path = "C:\\develope\\project\\train\\src\\test\\java\\com\\evildoer\\train\\algorithm\\ThreadStudyTest.java";
        String target = "test";
        File file = new File(path);

        ExecutorService executors = Executors.newFixedThreadPool(3, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("thread-pool-" + Thread.currentThread());
                return thread;
            }
        });
        AtomicLong count = new AtomicLong(0);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            List<Future> taskList = new LinkedList<>();
            while ((line = br.readLine()) != null) {
//                if (taskList.size() >= 10) {
//                    for (Future future : taskList) {
//                        future.get();
//                    }
//                }
                String tmp = line;
                taskList.add(executors.submit(() -> countNum(tmp, target, count)));
            }
//            for (Future future : taskList) {
//                future.get();
//            }
        } catch (IOException e) {
            System.err.println("read failed.");
        }
        executors.shutdown();
        executors.awaitTermination(3, TimeUnit.MINUTES);
        System.out.println(count.get());
    }

    private long countNum(String line, String target, AtomicLong count) {
        if (line == null || target == null) {
            return 0;
        }
        Matcher matcher = Pattern.compile(target).matcher(line);

        int i = 0;
        while (matcher.find()) {
            i++;
        }
        return count.addAndGet(i);
    }
}