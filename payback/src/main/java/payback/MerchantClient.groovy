package payback

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

import java.time.DayOfWeek
import java.time.LocalDateTime

import static java.math.BigDecimal.ZERO
import static java.math.MathContext.DECIMAL32

@FeignClient("merchants-service")
@CompileStatic
interface MerchantClient {

    @RequestMapping('/merchants/{id}')
    MerchantRepresentation findOne(@PathVariable('id') String id)

}

@EqualsAndHashCode
@CompileStatic
class MerchantRepresentation {

    String id

    String name

    PaybackPolicyRepresentation paybackPolicy

}

@EqualsAndHashCode
@CompileStatic
class PaybackPolicyRepresentation {

    BigDecimal percentage

    Set<DayOfWeek> daysOfWeek

    BigDecimal minAmount

    BigDecimal maxPayback

    BigDecimal paybackFor(BigDecimal amount, LocalDateTime timestamp) {
        return paybackFor(new Purchase(amount: amount, timestamp: timestamp))
    }

    BigDecimal paybackFor(Purchase purchase) {
        return Optional.of(purchase)
            .filter { percentage && it.amount && it.timestamp }
            .filter { !daysOfWeek || daysOfWeek.contains(it.timestamp.dayOfWeek) }
            .filter { !minAmount || minAmount.compareTo(it.amount) <= 0 }
            .map { percentage.multiply(it.amount, DECIMAL32) }
            .map { maxPayback && maxPayback.compareTo(it) < 0 ? maxPayback : it }
            .orElse(ZERO)
    }

}
