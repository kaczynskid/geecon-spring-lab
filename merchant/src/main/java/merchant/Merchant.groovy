package merchant

import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.CompileStatic
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL

@Document
@JsonInclude(NON_NULL)
@CompileStatic
class Merchant {

    @Id String id

    String name

    PaybackPolicy paybackPolicy

}