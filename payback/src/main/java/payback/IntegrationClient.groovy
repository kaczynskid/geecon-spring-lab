package payback

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired

import static java.util.Optional.empty
import static java.util.Optional.of

@CompileStatic
class IntegrationClient {

    private final CustomerClient customerClient
    private final MerchantClient merchantClient

    @Autowired
    IntegrationClient(CustomerClient customerClient, MerchantClient merchantClient) {
        this.merchantClient = merchantClient
        this.customerClient = customerClient
    }

    @HystrixCommand(fallbackMethod = 'findBustomerByIdFallback')
    Optional<CustomerRepresentation> findBustomerById(Long customerId) {
        return of(customerClient.findById(customerId))
    }

    Optional<CustomerRepresentation> findBustomerByIdFallback(Long customerId) {
        return empty()
    }

    @HystrixCommand(fallbackMethod = 'findCustomerByCreditCardFallback')
    Optional<CustomerRepresentation> findCustomerByCreditCard(String creditCardNumber) {
        return of(customerClient.findByCreditCardNumber(creditCardNumber))
    }

    Optional<CustomerRepresentation> findCustomerByCreditCardFallback(String creditCardNumber) {
        return empty()
    }

    @HystrixCommand(fallbackMethod = 'findMerchantByIdFallback')
    Optional<MerchantRepresentation> findMerchantById(String metchantId) {
        return of(merchantClient.findOne(metchantId))
    }

    Optional<MerchantRepresentation> findMerchantByIdFallback(String metchantId) {
        return empty()
    }
}
