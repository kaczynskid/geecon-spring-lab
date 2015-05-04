package customer

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.ResponseEntity.notFound
import static org.springframework.http.ResponseEntity.ok
import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@RequestMapping('customers')
@CompileStatic
class CustomerController {

    private final CustomerService service

    @Autowired
    CustomerController(CustomerService service) {
        this.service = service
    }

    @RequestMapping(value = '/{id}', method = GET)
    ResponseEntity<Customer> findById(@PathVariable('id') Long customerId) {
        return customerResponse(service.findById(customerId))
    }

    @RequestMapping(value = '/byCreditCard/{number}')
    ResponseEntity<Customer> findByCreditCardNumber(@PathVariable('number') String creditCardNumber) {
        return customerResponse(service.findByCreditCardNumber(creditCardNumber))
    }

    private ResponseEntity<Customer> customerResponse(Optional<Customer> customer) {
        return customer
            .map { ok(it) }
            .orElseGet { notFound().build() }
    }
}
