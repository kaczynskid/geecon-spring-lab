package customer

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@CompileStatic
class CustomerServiceApp {

    static void main(String[] args) {
        SpringApplication.run(CustomerServiceApp, args)
    }
}
