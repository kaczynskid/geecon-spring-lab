package payback

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus
import payback.customer.CustomerRepresentation
import payback.merchant.MerchantRepresentation

import static java.math.BigDecimal.ZERO
import static java.math.MathContext.DECIMAL32
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE

@Component
@CompileStatic
class PaybackService {

    private final PaybackRepository repository
    private final IntegrationClient integration

    @Autowired PaybackService(PaybackRepository repository, IntegrationClient integration) {
        this.repository = repository
        this.integration = integration
    }

    Payback registerPaybackFor(Purchase purchase) {
        CustomerRepresentation customer = integration
            .findCustomerByCreditCard(purchase.creditCardNumber)
            .orElseThrow { new PaybackUnavailable('Customer service not available!') }

        MerchantRepresentation merchant = integration
            .findMerchantById(purchase.merchantId)
            .orElseThrow { new PaybackUnavailable('Merchant service not available!') }

        BigDecimal amount = merchant.paybackPolicy.paybackFor(purchase)

        return repository.save(new Payback(customerId: customer.id, amount: amount, purchase: purchase))
    }

    PaybackSummary summaryFor(Long customerId) {
        PaybackSummary summary = new PaybackSummary(customer: integration.findBustomerById(customerId)
            .orElse(new CustomerRepresentation(lastName: 'Customer data unavailable!')))

        repository.findByCustomerId(customerId).each {
            summary.amount = summary.amount.add(it.amount, DECIMAL32)
            if (!summary.merchants.collect { it.id } .contains(it.purchase.merchantId)) {
                summary.merchants.add(integration.findMerchantById(it.purchase.merchantId)
                    .orElse(new MerchantRepresentation(name: 'Merchant data unavailable!')))
            }
        }
        return summary
    }

}

@CompileStatic
class PaybackSummary {

    CustomerRepresentation customer

    BigDecimal amount = ZERO

    Set<MerchantRepresentation> merchants = []

}

@ResponseStatus(SERVICE_UNAVAILABLE)
class PaybackUnavailable extends RuntimeException {

    PaybackUnavailable(String msg) {
        super(msg)
    }
}
