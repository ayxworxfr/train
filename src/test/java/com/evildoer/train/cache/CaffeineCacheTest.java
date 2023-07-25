package com.evildoer.train.cache;

import com.github.benmanes.caffeine.cache.*;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CaffeineCacheTest {

    /**
     * 手动加载
     */
    @Test
    public void test_load() {
        // 初始化缓存，设置了1分钟的写过期，100的缓存最大个数
        Cache<Integer, Integer> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
        int key = 1;
        // 使用getIfPresent方法从缓存中获取值。如果缓存中不存指定的值，则方法将返回 null：
        assertThat(cache.getIfPresent(key)).isNull();

        // 也可以使用 get 方法获取值，该方法将一个参数为 key 的 Function 作为参数传入。如果缓存中不存在该 key
        // 则该函数将用于提供默认值，该值在计算后插入缓存中：
        assertThat(cache.get(key, integer -> 2)).isEqualTo(2);

        // 校验key1对应的value是否插入缓存中
        assertThat(cache.getIfPresent(key)).isEqualTo(2);

        // 手动put数据填充缓存中
        int value1 = 2;
        cache.put(key, value1);

        // 使用getIfPresent方法从缓存中获取值。如果缓存中不存指定的值，则方法将返回 null：
        assertThat(cache.getIfPresent(1)).isEqualTo(2);

        // 移除数据，让数据失效
        cache.invalidate(1);
        assertThat(cache.getIfPresent(1)).isNull();
    }

    /**
     * 同步加载
     */
    @Test
    public void test_sync() {
        // 初始化缓存，设置了1分钟的写过期，100的缓存最大个数
        LoadingCache<Integer, Integer> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(new CacheLoader<Integer, Integer>() {
                    @Nullable
                    @Override
                    public Integer load(@NonNull Integer key) {
                        return getInDB(key);
                    }
                });

        int key1 = 1;
        // get数据，取不到则从数据库中读取相关数据，该值也会插入缓存中：
        Integer value1 = cache.get(key1);
        System.out.println(value1);

        // 支持直接get一组值，支持批量查找
        Map<Integer, Integer> dataMap
                = cache.getAll(Arrays.asList(1, 2, 3));
        System.out.println(dataMap);
    }

    /**
     * 异步加载
     */
    @Test
    @SneakyThrows
    public void test_async() {
        // 使用executor设置线程池
        AsyncCache<Integer, Integer> asyncCache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100).executor(Executors.newSingleThreadExecutor()).buildAsync();
        Integer key = 1;
        // get返回的是CompletableFuture
        CompletableFuture<Integer> future = asyncCache.get(key, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer key) {
                // 执行所在的线程不在是main，而是ForkJoinPool线程池提供的线程
                System.out.println("当前所在线程：" + Thread.currentThread().getName());
                int value = getInDB(key);
                return value;
            }
        });

        int value = future.get();
        System.out.println("当前所在线程：" + Thread.currentThread().getName());
        System.out.println(value);
    }

    /**
     * 模拟从数据库中读取key
     *
     * @param key
     * @return
     */
    private int getInDB(int key) {
        return key + 1;
    }
}