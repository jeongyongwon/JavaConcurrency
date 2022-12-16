package com.example.stock.facade;


import com.example.stock.service.OptimisticLockStockService;
import org.springframework.stereotype.Service;

@Service //따로 락을 잡지 않지만, 실패시 로직을 개발자가 직접 작성해줘야하는 단점이 있다
public class OptimisticLockStockFacade {

    private OptimisticLockStockService optimisticLockStockService;

    public OptimisticLockStockFacade(OptimisticLockStockService optimisticLockStockService) {
        this.optimisticLockStockService = optimisticLockStockService;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while (true) {
            try {
                optimisticLockStockService.decrease(id, quantity);

                break;
            } catch (Exception e) {
                Thread.sleep(50);  //optimistic lock이 실패할 시에 다시 요청하는 로직
            }
        }
    }
}
