package customer

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

@WebAppConfiguration
@ContextConfiguration(classes = CustomerServiceApp, loader = SpringApplicationContextLoader)
class CustomerControllerSpec extends Specification {

    @Autowired ObjectMapper mapper

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

    def "Creates new customers"() {
        when:
            ResultActions result = mvc
                .perform(post('/customers')
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(
                        new Customer(firstName: 'Andrzej', lastName: 'Grzesik',
                            creditCards: [new CreditCard(number: '2346395221459834')])
                    )))
                .andDo(print())
        then:
            result
                .andExpect(status().isCreated())
                .andExpect(header().string('Location', notNullValue()))
    }

    def "Validates customer names"() {
        when:
            ResultActions result = mvc
                .perform(post('/customers')
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(
                        new Customer(firstName: 'Johnny', lastName: 'Stalin',
                            creditCards: [new CreditCard(number: '9873395221459834')])
                    )))
                .andDo(print())
        then:
            result
                .andExpect(status().is4xxClientError())
    }
}
