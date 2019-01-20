package org.sadtech.autoresponder.entity;

import java.util.Objects;

public class Person {

    private Integer id;
    private Unit unit;

    public Person(Integer id, Unit unit) {
        this.id = id;
        this.unit = unit;
    }

    public Person(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(unit, person.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, unit);
    }
}
