package merchant

import groovy.transform.CompileStatic

import java.math.MathContext
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.stream.Stream

import static java.math.BigDecimal.ZERO
import static java.math.BigDecimal.ZERO
import static java.math.MathContext.DECIMAL32

@CompileStatic
class PaybackPolicy {

    BigDecimal percentage

    Set<DayOfWeek> daysOfWeek

    BigDecimal minAmount

    BigDecimal maxPayback

    BigDecimal paybackFor(BigDecimal amount, LocalDateTime timestamp) {
        return Optional.of([amount: amount, timestamp: timestamp] as PurchaseData)
            .filter { percentage && it.amount && it.timestamp }
            .filter { !daysOfWeek || daysOfWeek.contains(it.timestamp.dayOfWeek) }
            .filter { !minAmount || minAmount.compareTo(it.amount) <= 0 }
            .map { percentage.multiply(it.amount, DECIMAL32) }
            .map { maxPayback && maxPayback.compareTo(it) < 0 ? maxPayback : it }
            .orElse(ZERO)
    }

}

class PurchaseData {

    BigDecimal amount

    LocalDateTime timestamp
}
