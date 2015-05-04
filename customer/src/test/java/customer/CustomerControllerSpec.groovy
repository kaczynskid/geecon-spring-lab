package customer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.hamcrest.Matchers.equalTo
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*

@WebAppConfiguration
@ContextConfiguration(classes = CustomerServiceApp, loader = SpringApplicationContextLoader)
class CustomerControllerSpec extends Specification {

    @Autowired WebApplicationContext wac

    MockMvc mvc

    def setup() {
        mvc = webAppContextSetup(wac).build()
    }

    def "Finds by ID"() {
        when:
            ResultActions result = mvc
                .perform(get('/customers/{id}', 1))
                .andDo(print())
        then:
            result
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.firstName', equalTo('John')))
                .andExpect(jsonPath('$.lastName', equalTo('Smith')))
    }

    def "Finds by credit card"() {
        when:
            ResultActions result = mvc
                .perform(get('/customers/byCreditCard/{number}', '9889766754322345'))
                .andDo(print())
        then:
            result
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.firstName', equalTo('Jane')))
                .andExpect(jsonPath('$.lastName', equalTo('Smith')))
    }
}
