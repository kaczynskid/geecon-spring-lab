package payback

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

import static org.springframework.web.bind.annotation.RequestMethod.GET

@RequestMapping('customers')
@CompileStatic
interface CustomerClient {

    @RequestMapping(value = '/{id}', method = GET)
    CustomerRepresentation findById(@PathVariable('id') Long customerId)

    @RequestMapping(value = '/byCreditCard/{number}', method = GET)
    CustomerRepresentation findByCreditCardNumber(@PathVariable('number') String creditCardNumber)

}

@JsonIgnoreProperties(ignoreUnknown = true)
class CustomerRepresentation {

    Long id

    String firstName

    String lastName

    Set<CreditCardRepresentation> creditCards = []

}

@JsonIgnoreProperties(ignoreUnknown = true)
class CreditCardRepresentation {

    Long id

    Long accountId

    String number
}
