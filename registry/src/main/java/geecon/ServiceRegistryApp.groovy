package geecon

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@CompileStatic
class ServiceRegistryApp {

    static def main(String[] args) {
        SpringApplication.run(ServiceRegistryApp, args)
    }
}