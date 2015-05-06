package dashboard

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard
import org.springframework.cloud.netflix.turbine.EnableTurbine

@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbine
@EnableEurekaClient
@CompileStatic
class DashboardApp {

    public static void main(String[] args) {
        SpringApplication.run(DashboardApp, args)
    }

}
