package payback.merchant;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("merchant-service")
public interface MerchantClient {

    @RequestMapping(value = "/merchants/{id}", method = GET)
    MerchantRepresentation findOne(@PathVariable("id") String id);

}
