package customer

import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository

@CompileStatic
interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByCreditCardsNumber(String creditCardNumber)

}
