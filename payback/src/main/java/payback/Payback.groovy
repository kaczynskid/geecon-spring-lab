package payback

import groovy.transform.CompileStatic
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
@CompileStatic
class Payback {

    @Id String id

    String customerId

    BigDecimal amount

    Purchase purchase
}
