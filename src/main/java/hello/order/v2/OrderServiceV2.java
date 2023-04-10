package hello.order.v2;

import hello.order.OrderService;
import io.micrometer.core.annotation.Counted;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class OrderServiceV2 implements OrderService {
    
    private AtomicInteger stock = new AtomicInteger(100);

    private int exceptionCount = 1;
    @Counted("my.order")
    @Override
    public void order() {
        log.info("주문");
        int i = stock.decrementAndGet();

        exceptionCount++;
        if (exceptionCount == 3) {
            exceptionCount = 1;
            throw new RuntimeException();
        }
    }

    @Counted("my.order")
    @Override
    public void cancel() {
        log.info("취소");
        int i = stock.incrementAndGet();
    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }
}
