package customer

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@SpringBootApplication
@CompileStatic
class CustomerServiceApp {

    static void main(String[] args) {
        SpringApplication.run(CustomerServiceApp, args)
    }
}

@Configuration
@Profile('cloud')
@EnableDiscoveryClient
@EnableCircuitBreaker
@CompileStatic
class CloudConfig {

}
