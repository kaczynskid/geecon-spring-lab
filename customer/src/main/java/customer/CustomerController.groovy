package customer

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

import javax.servlet.http.HttpServletResponse

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.ResponseEntity.*
import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
@RequestMapping('/customers')
@CompileStatic
class CustomerController {

    private final CustomerService service

    @Autowired
    CustomerController(CustomerService service) {
        this.service = service
    }

    @RequestMapping(value = '/{id}', method = GET)
    ResponseEntity<Customer> findById(
            @PathVariable('id') Long customerId) {
        return customerResponse(service.findById(customerId))
    }

    @RequestMapping(value = '/byCreditCard/{number}')
    ResponseEntity<Customer> findByCreditCardNumber(
            @PathVariable('number') String creditCardNumber) {
        return customerResponse(service.findByCreditCardNumber(creditCardNumber))
    }

    private ResponseEntity<Customer> customerResponse(Optional<Customer> customer) {
        return customer
            .map { ok(it) }
            .orElseGet { notFound().build() }
    }

    @RequestMapping(method = POST)
    ResponseEntity<Void> create(
            @RequestBody Customer customer, UriComponentsBuilder uriBuilder) {
        Customer result = service.create(customer)
        return created(uriBuilder.path('/customers/{id}')
            .buildAndExpand(result.getId()).toUri()).build()
    }

    @ExceptionHandler(ValueBlacklisted)
    void handleValueBlacklisted(ValueBlacklisted ex, HttpServletResponse response) {
        response.sendError(BAD_REQUEST.value(), ex.getMessage())
    }
}
