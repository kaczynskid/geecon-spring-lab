package payback

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@CompileStatic
class PaybackServiceApp {

    static void main(String[] args) {
        SpringApplication.run(PaybackServiceApp, args)
    }
}
