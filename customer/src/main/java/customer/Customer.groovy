package customer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.data.jpa.domain.AbstractPersistable

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.validation.constraints.NotNull

import static javax.persistence.CascadeType.ALL

@Entity
@JsonIgnoreProperties('new')
@CompileStatic
class Customer extends AbstractPersistable<Long> {

    @NotNull String firstName

    @NotNull String lastName

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "accountId")
    @NotEmpty Set<CreditCard> creditCards = []

    @Override
    public void setId(Long id) {
        super.setId(id)
    }

    Customer mergeWith(Customer other) {
        this.firstName = other.firstName ?: this.firstName
        this.lastName= other.lastName ?: this.lastName
        return this
    }

}
