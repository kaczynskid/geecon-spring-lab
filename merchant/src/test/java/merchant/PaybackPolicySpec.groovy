package merchant

import spock.lang.Specification

import java.math.MathContext
import java.time.LocalDateTime

import static java.math.BigDecimal.ONE
import static java.math.BigDecimal.TEN
import static java.math.BigDecimal.ZERO
import static java.math.MathContext.DECIMAL32
import static java.time.LocalDateTime.now
import static merchant.Percentages.TEN_PERCENT

class PaybackPolicySpec extends Specification {

    static final BigDecimal HUNDRED = new BigDecimal('100', DECIMAL32)

    def "Calculates payback"() {
        expect:
            new PaybackPolicy()
                .paybackFor(TEN, now()) == ZERO

            new PaybackPolicy(percentage: TEN_PERCENT)
                .paybackFor(null, now()) == ZERO

            new PaybackPolicy(percentage: TEN_PERCENT)
                .paybackFor(TEN, null) == ZERO

            new PaybackPolicy(percentage: TEN_PERCENT)
                .paybackFor(TEN, now()) == ONE

            new PaybackPolicy(percentage: TEN_PERCENT, minAmount: HUNDRED)
                .paybackFor(TEN, now()) == ZERO

            new PaybackPolicy(percentage: TEN_PERCENT, minAmount: HUNDRED)
                .paybackFor(HUNDRED, now()) == TEN

            new PaybackPolicy(percentage: TEN_PERCENT, maxPayback: ONE)
                .paybackFor(HUNDRED, now()) == ONE

            new PaybackPolicy(percentage: TEN_PERCENT, maxPayback: TEN)
                .paybackFor(HUNDRED, now()) == TEN

            new PaybackPolicy(percentage: TEN_PERCENT, daysOfWeek: [now().plusDays(1).dayOfWeek])
                .paybackFor(HUNDRED, now()) == ZERO

            new PaybackPolicy(percentage: TEN_PERCENT, daysOfWeek: [now().dayOfWeek])
                .paybackFor(HUNDRED, now()) == TEN
    }

}