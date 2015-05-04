package merchant

import com.mongodb.Mongo
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

import static merchant.Percentages.FIVE_PERCENT
import static merchant.Percentages.TEN_PERCENT

@Configuration
@CompileStatic
class TestMongoConfig {

    @Bean(destroyMethod = 'close')
    Mongo mongo() {
        return new EmbeddedMongoBuilder().build()
    }

}

@Component
@CompileStatic
class TestMongoInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired MerchantRepository repository

    @Override
    void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        repository.save([
            new Merchant(id: 'oracle', name: 'Oracle', paybackPolicy: new PaybackPolicy(percentage: FIVE_PERCENT)),
            new Merchant(id: 'pivotal', name: 'Pivotal', paybackPolicy: new PaybackPolicy(percentage: TEN_PERCENT))
        ])
    }
}
