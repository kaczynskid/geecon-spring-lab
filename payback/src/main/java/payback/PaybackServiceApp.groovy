package payback

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@SpringBootApplication
@CompileStatic
class PaybackServiceApp {

    static void main(String[] args) {
        SpringApplication.run(PaybackServiceApp, args)
    }
}

@Configuration
@Profile('cloud')
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
@EnableZuulProxy
@CompileStatic
class CloudConfig {

}
