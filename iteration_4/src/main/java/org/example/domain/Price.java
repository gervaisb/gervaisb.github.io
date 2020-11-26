package org.example.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class Price {
    private final BigDecimal amount;
    private final Currency unit;

    public static Price euros(int amount) {
        return euros(BigDecimal.valueOf(amount));
    }

    public static Price euros(BigDecimal amount) {
        return of(amount, Currency.getInstance("EUR"));
    }

    public static Price of(BigDecimal amount, Currency unit) {
        return new Price(amount, unit);
    }

    private Price(BigDecimal amount, Currency unit) {
        if ( amount.compareTo(BigDecimal.ZERO)<0 ) {
            throw new IllegalArgumentException("Price amount must be greater or equal to 0");
        }
        this.amount = amount;
        this.unit = unit;
    }

    public Price multiply(Quantity quantity) {
        BigDecimal multiplicand = BigDecimal.valueOf(quantity.value);
        return new Price(this.amount.multiply(multiplicand), this.unit);
    }

    public Price plus(Price other) {
        if ( this.unit.equals(other.unit) ) {
            BigDecimal sum = this.amount.add(other.amount);
            return new Price(sum, this.unit);
        } else {
            throw new IllegalArgumentException("Cannot add prices of different units");
        }
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
