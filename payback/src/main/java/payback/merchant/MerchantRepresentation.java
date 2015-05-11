package payback.merchant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantRepresentation {

    String id;

    String name;

    PaybackPolicyRepresentation paybackPolicy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaybackPolicyRepresentation getPaybackPolicy() {
        return paybackPolicy;
    }

    public void setPaybackPolicy(PaybackPolicyRepresentation paybackPolicy) {
        this.paybackPolicy = paybackPolicy;
    }

    @Override
    public String toString() {
        return "MerchantRepresentation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", paybackPolicy=" + paybackPolicy +
                '}';
    }
}
