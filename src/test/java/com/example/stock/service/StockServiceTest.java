package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockServiceTest {

    @Autowired
//    private StockService stockService;
    private PessimisticLockStockService stockService;

    @Autowired
    private StockRepository stockRepository;

    //데이터가 없으니 가상의 데이터를 넣어주는 기능
    @BeforeEach
    public void insert() {
        Stock stock = new Stock(1L, 100L);

        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    public void delete() {
        stockRepository.deleteAll();
    }

    @Test
    public void decrease_test() {
        stockService.decrease(1L, 1L);

        Stock stock = stockRepository.findById(1L).orElseThrow();
        // 100 - 1 = 99

        assertEquals(99, stock.getQuantity());
    }

    @Test
    public void concurrent_order_100() throws InterruptedException {
        int threadCount = 100; //쓰레드 100개

        //ExecutorService 비동기요청을 단순하게 사용할 수 있는 자바 API
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        //100개의 요청
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decrease(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); //다른 쓰레드에서 작업하는 것을 끝날때까지 대기하는 클래스이다.

        Stock stock = stockRepository.findById(1L).orElseThrow();

        // 100 - (100 * 1) = 0
        //실제로는 90개가 남았다
        //이유는 race condition 때문이다
        /*
        * 예를들어 id값이 1번인 row부터 삭제하게 될껀데,
        * 쓰레드가 동시에 1번에 여러개 붙어서 내가 경합 과정에서 요청이 누락된다고 생각하면 쉽다
        * */
        assertEquals(0, stock.getQuantity());
    }

}