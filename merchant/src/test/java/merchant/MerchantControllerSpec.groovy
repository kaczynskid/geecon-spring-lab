package merchant

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.web.context.WebApplicationContext
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import static java.math.MathContext.DECIMAL32
import static merchant.Percentages.TWENTY_PERCENT
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

@Stepwise
@WebAppConfiguration
@ContextConfiguration(classes = MerchantServiceApp, loader = SpringApplicationContextLoader)
class MerchantControllerSpec extends Specification {

    @Autowired @Qualifier('halObjectMapper') ObjectMapper mapper

    @Autowired WebApplicationContext wac

    MockMvc mvc

    @Shared String merchantId

    def setup() {
        mvc = webAppContextSetup(wac).build()
    }

    def "Finds all"() {
        when:
            ResultActions result = mvc
                .perform(get('/merchants'))
                .andDo(print())
        then:
            result
                .andExpect(status().isOk())
    }

    def "Finds one"() {
        when:
            ResultActions result = mvc
                .perform(get('/merchants/oracle'))
                .andDo(print())
        then:
            result
                .andExpect(status().isOk())
    }

    def "Creates one"() {
        when:
            ResultActions result = mvc
                .perform(post('/merchants')
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(
                        new Merchant(
                            name: 'geecon',
                            paybackPolicy: new PaybackPolicy(percentage: TWENTY_PERCENT))
                    )))
                .andDo(print())
        then:
            result
                .andExpect(status().isCreated())
                .andExpect(header().string('Location', notNullValue()))

            (merchantId = result.andReturn().response.getHeader('Location').split('/').last()) != null
    }

    def "Updates created one"() {
        when:
            ResultActions result = mvc
                .perform(patch('/merchants/{id}', merchantId)
                    .contentType(APPLICATION_JSON)
                    .content(mapper.writeValueAsString(
                        new Merchant(paybackPolicy: new PaybackPolicy(
                            percentage: TWENTY_PERCENT,
                            minAmount: new BigDecimal('100', DECIMAL32)))
                    )))
                .andDo(print())
        then:
            result
                .andExpect(status().isNoContent())
    }

    def "Finds created one"() {
        when:
            ResultActions result = mvc
                .perform(get('/merchants/{id}', merchantId))
                .andDo(print())
        then:
            result
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.name', equalTo('geecon')))
                .andExpect(jsonPath('$.paybackPolicy.percentage', equalTo(Double.valueOf(0.2))))
                .andExpect(jsonPath('$.paybackPolicy.minAmount', equalTo(100)))
    }
}
