package payback

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

import static org.springframework.http.ResponseEntity.created
import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
@RequestMapping('/paybacks')
@CompileStatic
class PaybackController {

    private final PaybackService service

    @Autowired
    PaybackController(PaybackService service) {
        this.service = service
    }

    @RequestMapping(method = POST)
    ResponseEntity<Void> register(@RequestBody Purchase purchase, UriComponentsBuilder uriBuilder) {
        Payback result = service.registerPaybackFor(purchase)
        return created(uriBuilder.path('/paybacks/summary/{customerId}')
            .buildAndExpand(result.customerId).toUri()).build()
    }

    @RequestMapping(value = '/summary/{customerId}', method = GET)
    PaybackSummary summary(@PathVariable Long customerId) {
        return service.summaryFor(customerId)
    }

}
