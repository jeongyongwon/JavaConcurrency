package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private StockRepository stockRepository;

    public  StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /*
    * Transactional 동작 방식
    *
    * Wrapping 한 클래스를 새로 생성함
    *
    * 감소 메서드를 호출하여 트랜잭션이 완료되었고
    * , DB에 저장하기 전에 다른 쓰레드가 메서드를 실행할 수 있다
    *
    * 그럼 다른 쓰레드는 결국 수정전 상태로 수정을 다시 하는 과정
    *
    * 그러나 synchronized 역시 단일 프로세스에서만 보장하기 때문에
    *
    * 다중 서버 환경에서는 race condition이 발생하게 됨
    * */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized void decrease(Long id, Long quantity) {

        //재고 감소 이후 저장
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
