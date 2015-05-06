package customer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic
import org.springframework.data.jpa.domain.AbstractPersistable

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = 'number'))
@JsonIgnoreProperties('new')
@CompileStatic
class CreditCard extends AbstractPersistable<Long> {

    Long accountId

    String number

}
