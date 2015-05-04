package config

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
@CompileStatic
class ConfigurationServiceApp {

    static def main(String[] args) {
        SpringApplication.run(ConfigurationServiceApp, args)
    }
}