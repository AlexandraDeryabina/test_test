package ru.lanit.entity;

import javax.persistence.*;

@Entity
public class Car {
    @Id
    private Long id;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int horsepower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Person person;

    public Car() {
    }

    public Car(Long id, String model, int horsepower) {
        this.id = id;
        this.model = model;
        this.horsepower = horsepower;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
