package payback

import groovy.transform.CompileStatic
import org.springframework.data.mongodb.repository.MongoRepository

@CompileStatic
interface PaybackRepository extends MongoRepository<Payback, String> {

    List<Payback> findByCustomerId(Long customerId)

}
