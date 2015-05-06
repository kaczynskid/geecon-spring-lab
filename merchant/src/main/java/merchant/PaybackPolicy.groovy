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

}
