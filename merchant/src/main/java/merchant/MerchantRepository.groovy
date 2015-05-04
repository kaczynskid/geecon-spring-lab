package merchant

import groovy.transform.CompileStatic
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
@CompileStatic
interface MerchantRepository extends MongoRepository<Merchant, String> {

}