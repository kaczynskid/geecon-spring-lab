package config

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
@CompileStatic
class ConfigurationServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(ConfigurationServiceApp, args)
    }
}