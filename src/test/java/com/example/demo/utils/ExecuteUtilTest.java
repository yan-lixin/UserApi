package com.example.demo.utils;
 
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class ExecuteUtilTest {
 
    private EasyRandom easyRandom = new EasyRandom();
 
    // 测试数据
    private List<String> mockDataList;

    private int total = 300;

    private AtomicInteger atomicInteger;

    @Before
    public void init() {
        // 构造total条数据
        mockDataList = easyRandom.objects(String.class, total).collect(Collectors.toList());

    }

    @Test
    public void test_call_return_list_partition_async() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        atomicInteger = new AtomicInteger(0);
        Stopwatch stopwatch = Stopwatch.createStarted();
        // 分批执行
        int size = 3;
        List<Integer> resultList = ExecuteUtil.partitionCall2ListAsync(mockDataList, size, executorService, (eachList) -> someCall(2L, eachList));

        Stopwatch stop = stopwatch.stop();
        log.info("执行时间: {} 秒", stop.elapsed(TimeUnit.SECONDS));

        Assert.assertEquals(total, resultList.size());
        // 正好几轮
        int turns;
        if (total % size == 0) {
            turns = total / size;
        } else {
            turns = total / size + 1;
        }
        log.info("共调用了{}次", turns);
        Assert.assertEquals(turns, atomicInteger.get());

        // 顺序也一致
        for(int i =0; i< mockDataList.size();i++){
            Assert.assertEquals((Integer) mockDataList.get(i).length(), resultList.get(i));
        }
    }


    /**
     * 模拟一次调用
     */
    private List<Integer> someCall(Long id, List<String> strList) {

        log.info("当前-->{}，strList.size：{}", atomicInteger.incrementAndGet(), strList.size());
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return strList.stream()
                .map(String::length)
                .collect(Collectors.toList());
    }
 
 
}