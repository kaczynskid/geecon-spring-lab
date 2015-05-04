package customer

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import static java.util.Optional.ofNullable

@Service
@CompileStatic
class CustomerService {

    private final CustomerRepository repository

    @Autowired CustomerService(CustomerRepository repository) {
        this.repository = repository
    }

    Optional<Customer> findById(Long id) {
        return ofNullable(repository.findOne(id))
    }

    Optional<Customer> findByCreditCardNumber(String creditCardNumber) {
        return ofNullable(repository.findByCreditCardsNumber(creditCardNumber))
    }
}
