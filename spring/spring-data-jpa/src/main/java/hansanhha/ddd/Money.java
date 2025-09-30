package hansanhha.ddd;


import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;


@Embeddable
@Getter
public class Money {

    private final Long amount;
    private final String currency;

    public Money() {
        this(0L, "KRW");
    }

    public Money(Long amount, String currency) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        this.amount = amount;
        this.currency = currency;
    }

    public Money add(Money money) {
        if (!this.getCurrency().equals(money.getCurrency())) {
            throw new IllegalArgumentException("Currency does not match");
        }
        return new Money(this.amount + money.getAmount(), money.getCurrency());
    }

    public Money multiply(int count) {
        return  new Money(this.amount * count, this.currency);
    }
}
