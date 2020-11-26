package org.example.domain;

import java.util.Objects;

/**
 * An always positive integer
 */
public class Quantity implements Comparable<Quantity> {
    private final int value;

    public Quantity(int value) {
        if ( value<0 ) {
            throw new IllegalArgumentException("Quantity must be bigger or equal to 0");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Quantity o) {
        return value-o.value;
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

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}