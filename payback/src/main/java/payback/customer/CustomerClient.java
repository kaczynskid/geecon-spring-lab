package payback.customer;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import groovy.transform.CompileStatic;

@FeignClient("customer-service")
@CompileStatic
public interface CustomerClient {

    @RequestMapping(value = "/customers/{id}", method = GET)
    CustomerRepresentation findById(@PathVariable("id") Long customerId);

    @RequestMapping(value = "/customers/byCreditCard/{number}", method = GET)
    CustomerRepresentation findByCreditCardNumber(@PathVariable("number") String creditCardNumber);

}
