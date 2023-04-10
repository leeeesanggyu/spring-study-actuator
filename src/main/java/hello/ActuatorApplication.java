package hello;

import hello.order.gauge.StockConfig;
import hello.order.gauge.StockConfigV2;
import hello.order.v2.OrderConfigV2;
import hello.order.v3.OrderConfigV3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Import(OrderConfigV0.class)
//@Import(OrderConfigV1.class)
//@Import(OrderConfigV2.class)
//@Import({
//        OrderConfigV3.class,
//        StockConfig.class
//})
@Import({
        OrderConfigV3.class,
        StockConfigV2.class
})
@SpringBootApplication(scanBasePackages = "hello.controller")
public class ActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActuatorApplication.class, args);
    }

    @Bean
    public InMemoryHttpExchangeRepository httpExchangeRepository() {
        InMemoryHttpExchangeRepository inMemoryHttpExchangeRepository = new InMemoryHttpExchangeRepository();
        inMemoryHttpExchangeRepository.setCapacity(1);
        return inMemoryHttpExchangeRepository;
    }

}
