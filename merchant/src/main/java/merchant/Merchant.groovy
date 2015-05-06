package merchant

import groovy.transform.CompileStatic
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
@CompileStatic
class Merchant {

    @Id String id

    String name

    PaybackPolicy paybackPolicy

}
