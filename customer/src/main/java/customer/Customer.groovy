package customer

import groovy.transform.CompileStatic
import org.springframework.data.jpa.domain.AbstractPersistable

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToMany

@Entity
@CompileStatic
class Customer extends AbstractPersistable<Long> {

    String firstName

    String lastName

    @OneToMany
    @JoinColumn(name = "accountId")
    Set<CreditCard> creditCards = []

}
