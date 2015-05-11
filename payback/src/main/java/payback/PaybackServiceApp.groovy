package payback

import com.fasterxml.jackson.datatype.jsr310.JSR310Module
import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.mongodb.core.convert.CustomConversions

import java.time.LocalDateTime

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

@SpringBootApplication
@CompileStatic
class PaybackServiceApp {

    static void main(String[] args) {
        SpringApplication.run(PaybackServiceApp, args)
    }

    @Bean
    JSR310Module jsr310Module() {
        return new JSR310Module()
    }

    @Bean
    CustomConversions customConversions() {
        return new CustomConversions([
            LocalDateTimeToStringConverter.instance,
            StringToLocalDateTimeConverter.instance
        ])
    }
}

@Singleton
@WritingConverter
class LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {

    @Override
    String convert(LocalDateTime dateTime) {
        return dateTime.format(ISO_LOCAL_DATE_TIME)
    }
}

@Singleton
@ReadingConverter
class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    LocalDateTime convert(String string) {
        return LocalDateTime.parse(string, ISO_LOCAL_DATE_TIME)
    }
}

@Configuration
@Profile('cloud')
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
@CompileStatic
class CloudConfig {

}
