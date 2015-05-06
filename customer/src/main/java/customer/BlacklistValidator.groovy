package customer

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.stereotype.Service

@Service
@RefreshScope
@CompileStatic
class BlacklistValidator {

    private final List<String> blacklist

    @Autowired BlacklistValidator(@Value('${customer.blacklist:}') String blacklist) {
        this.blacklist = blacklist.split(',').toList()
    }

    void validate(String value) throws ValueBlacklisted {
        if (blacklist && blacklist.contains(value)) {
            throw new ValueBlacklisted(value)
        }
    }
}

@CompileStatic
class ValueBlacklisted extends RuntimeException {

    ValueBlacklisted(String value) {
        super("'$value' is a blacklisted word")
    }
}
