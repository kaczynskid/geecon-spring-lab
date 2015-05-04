package payback

import groovy.transform.CompileStatic

import java.time.LocalDateTime

@CompileStatic
class Purchase {

    BigDecimal amount

    String creditCardNumber

    String merchantId

    LocalDateTime timestamp

}