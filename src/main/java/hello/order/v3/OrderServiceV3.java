package hello.order.v3;

import hello.order.OrderService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class OrderServiceV3 implements OrderService {

    private final MeterRegistry meterRegistry;

    private AtomicInteger stock = new AtomicInteger(100);

    public OrderServiceV3(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void order() {
        Timer.builder("my.order")
                .tag("class", this.getClass().getName())
                .tag("method", "order")
                .description("order")
                .register(meterRegistry)
                .record(() -> {
                    log.info("주문");
                    int i = stock.decrementAndGet();
                    sleep();
                });
    }

    @Override
    public void cancel() {
        Timer.builder("my.order")
                .tag("class", this.getClass().getName())
                .tag("method", "cancle")
                .description("order")
                .register(meterRegistry)
                .record(() -> {
                    log.info("취소");
                    int i = stock.incrementAndGet();
                    sleep();
                });
    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }

    private void sleep() {
        try {
            Thread.sleep(2000 + new Random().nextInt(2000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
