package hello.order.gauge;

import hello.order.OrderService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockConfig {

    @Bean
    public MyStockMetric myStockMetric(
            OrderService orderService,
            MeterRegistry meterRegistry
    ) {
        return new MyStockMetric(orderService, meterRegistry);
    }

    @Slf4j
    static class MyStockMetric {
        private final OrderService orderService;
        private final MeterRegistry meterRegistry;

        MyStockMetric(OrderService orderService, MeterRegistry meterRegistry) {
            this.orderService = orderService;
            this.meterRegistry = meterRegistry;
        }

        /**
         * 외부에서 메트릭을 확인할 떄마다 호출됨
         */
        @PostConstruct
        public void init() {
            Gauge.builder("my.stock", orderService, orderService -> {
                log.info("stock guage call");
                return orderService.getStock().get();
            }).register(meterRegistry);
        }
    }
}
