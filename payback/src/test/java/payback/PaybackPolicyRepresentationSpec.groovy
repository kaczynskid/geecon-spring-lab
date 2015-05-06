package payback

import spock.lang.Specification

import static java.math.BigDecimal.*
import static java.math.MathContext.DECIMAL32
import static java.time.LocalDateTime.now

class PaybackPolicyRepresentationSpec extends Specification {

    static final BigDecimal HUNDRED = new BigDecimal('100', DECIMAL32)

    static final BigDecimal TEN_PERCENT = new BigDecimal('0.1', DECIMAL32)

    def "Calculates payback"() {
        expect:
            new PaybackPolicyRepresentation()
                .paybackFor(TEN, now()) == ZERO

            new PaybackPolicyRepresentation(percentage: TEN_PERCENT)
                .paybackFor(null, now()) == ZERO

            new PaybackPolicyRepresentation(percentage: TEN_PERCENT)
                .paybackFor(TEN, null) == ZERO

            new PaybackPolicyRepresentation(percentage: TEN_PERCENT)
                .paybackFor(TEN, now()) == ONE

            new PaybackPolicyRepresentation(percentage: TEN_PERCENT, minAmount: HUNDRED)
                .paybackFor(TEN, now()) == ZERO

            new PaybackPolicyRepresentation(percentage: TEN_PERCENT, minAmount: HUNDRED)
                .paybackFor(HUNDRED, now()) == TEN

            new PaybackPolicyRepresentation(percentage: TEN_PERCENT, maxPayback: ONE)
                .paybackFor(HUNDRED, now()) == ONE

            new PaybackPolicyRepresentation(percentage: TEN_PERCENT, maxPayback: TEN)
                .paybackFor(HUNDRED, now()) == TEN

            new PaybackPolicyRepresentation(percentage: TEN_PERCENT, daysOfWeek: [now().plusDays(1).dayOfWeek])
                .paybackFor(HUNDRED, now()) == ZERO

            new PaybackPolicyRepresentation(percentage: TEN_PERCENT, daysOfWeek: [now().dayOfWeek])
                .paybackFor(HUNDRED, now()) == TEN
    }
}
