package org.sadtech.autoresponder.test;

import org.sadtech.autoresponder.entity.Unit;

import java.util.Objects;

public class TestUnit extends Unit {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestUnit)) return false;
        if (!super.equals(o)) return false;
        TestUnit testUnit = (TestUnit) o;
        return Objects.equals(message, testUnit.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), message);
    }
}
