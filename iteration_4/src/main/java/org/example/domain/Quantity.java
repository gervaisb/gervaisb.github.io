package org.example.domain;

import java.util.Objects;

public class Quantity {

    public static Quantity of(int value) {
        return new Quantity(value);
    }

    private Quantity(int value) {
        if ( value<0 ) {
            throw new IllegalArgumentException("Quantity must be bigger or equal to 0");
        }
        this.value = value;
    }


    final int value;

    public Quantity plus(Quantity other) {
        return new Quantity(this.value + other.value);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
