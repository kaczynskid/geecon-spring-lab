package merchant

import groovy.transform.CompileStatic

import java.math.MathContext

import static java.math.MathContext.DECIMAL32

@CompileStatic
abstract class Percentages {

    static final MathContext PERCENTAGE = DECIMAL32

    static final BigDecimal FIVE_PERCENT = new BigDecimal('0.05', PERCENTAGE)

    static final BigDecimal TEN_PERCENT = new BigDecimal('0.1', PERCENTAGE)

    static final BigDecimal TWENTY_PERCENT = new BigDecimal('0.2', PERCENTAGE)

    private Percentages() {}
}