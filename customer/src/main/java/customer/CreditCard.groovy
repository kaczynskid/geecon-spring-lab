package customer

import groovy.transform.CompileStatic
import org.springframework.data.jpa.domain.AbstractPersistable

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = 'number'))
@CompileStatic
class CreditCard extends AbstractPersistable<Long> {

    Long accountId

    String number

}
