package registry

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer
import org.springframework.core.io.ClassPathResource

@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
@CompileStatic
class ServiceRegistryApp {

    public static void main(String[] args) {
        System.err.println(new ClassPathResource("static/eureka/css/wro.css").getURL());
        SpringApplication.run(ServiceRegistryApp, args)
    }
}