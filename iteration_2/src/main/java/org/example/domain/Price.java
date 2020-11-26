package org.example.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class Price {
    private final BigDecimal amount;
    private final Currency unit;

    public Price(BigDecimal amount, Currency unit) {
        if ( amount.compareTo(BigDecimal.ZERO)<0 ) {
            throw new IllegalArgumentException("Price amount must be greater or equal to 0");
        }

        this.amount = amount;
        this.unit = unit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return amount.equals(price.amount) &&
                unit.equals(price.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, unit);
    }
}
