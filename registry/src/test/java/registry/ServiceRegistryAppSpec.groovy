package registry

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

import static org.springframework.http.HttpStatus.OK

@WebAppConfiguration
@ContextConfiguration(classes = ServiceRegistryApp, loader = SpringApplicationContextLoader)
@IntegrationTest('server.port=0')
class ServiceRegistryAppSpec extends Specification {

    @Value('${local.server.port}')
    int port = 0;

    def "Catalog loads"() {
        when:
            ResponseEntity<Map> entity = new TestRestTemplate()
                .getForEntity("http://localhost:" + port + "/eureka/apps", Map.class);
        then:
            entity.getStatusCode() == OK
    }

    def "Admin loads"() {
        when:
            ResponseEntity<Map> entity = new TestRestTemplate()
                .getForEntity("http://localhost:" + port + "/env", Map.class);
        then:
            entity.getStatusCode() == OK
    }
}
