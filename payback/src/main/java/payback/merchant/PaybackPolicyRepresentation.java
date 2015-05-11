package payback.merchant;

import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL32;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import payback.Purchase;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaybackPolicyRepresentation {

    private BigDecimal percentage;

    private Set<DayOfWeek> daysOfWeek;

    private BigDecimal minAmount;

    private BigDecimal maxPayback;

    public BigDecimal paybackFor(BigDecimal amount, LocalDateTime timestamp) {
        Purchase purchase = new Purchase();
        purchase.setAmount(amount);
        purchase.setTimestamp(timestamp);
        return paybackFor(purchase);
    }

    public BigDecimal paybackFor(Purchase purchase) {
        return Optional.of(purchase)
            .filter(it -> percentage != null && it.getAmount() != null && it.getTimestamp() != null)
            .filter( it -> daysOfWeek == null || daysOfWeek.contains(it.getTimestamp().getDayOfWeek()) )
            .filter( it ->  minAmount == null || minAmount.compareTo(it.getAmount()) <= 0 )
            .map(it -> percentage.multiply(it.getAmount(), DECIMAL32))
            .map(it -> maxPayback != null && maxPayback.compareTo(it) < 0 ? maxPayback : it)
            .orElse(ZERO);
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public Set<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public BigDecimal getMaxPayback() {
        return maxPayback;
    }

    @Override
    public String toString() {
        return "PaybackPolicyRepresentation{" +
                "percentage=" + percentage +
                ", daysOfWeek=" + daysOfWeek +
                ", minAmount=" + minAmount +
                ", maxPayback=" + maxPayback +
                '}';
    }
}
