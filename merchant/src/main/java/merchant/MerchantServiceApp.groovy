package merchant

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.rest.SpringBootRepositoryRestMvcConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration

@SpringBootApplication
@CompileStatic
class MerchantServiceApp {

    static void main(String[] args) {
        SpringApplication.run(MerchantServiceApp, args)
    }
}

@Configuration
class RepositoryRestMvcConfigurer extends SpringBootRepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        super.configureRepositoryRestConfiguration(config)
        config.exposeIdsFor(Merchant)
    }
}
