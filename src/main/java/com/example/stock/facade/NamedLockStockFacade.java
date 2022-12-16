package com.example.stock.facade;

import com.example.stock.repository.LockRepository;
import com.example.stock.service.StockService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/*
* named 락은 분산 락을 구현할 때 사용
* pessmistic lcok timeout 구현하기 어려움
* named lock 해제를 잘해주는 것이 핵심
* */
@Component
public class NamedLockStockFacade {

    private LockRepository lockRepository;

    private StockService stockService;

    public NamedLockStockFacade(LockRepository lockRepository, StockService stockService) {
        this.lockRepository = lockRepository;
        this.stockService = stockService;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        try {
            //lock를 획득한 후 decrease 실행
            lockRepository.getLock(id.toString());
            stockService.decrease(id, quantity);
        } finally {
            //lock 해제
            lockRepository.releaseLock(id.toString());
        }
    }
}