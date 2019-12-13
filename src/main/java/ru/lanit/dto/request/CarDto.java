package ru.lanit.dto.request;

import ru.lanit.constraint.CarIdConstraint;
import ru.lanit.constraint.CarModelConstraint;
import ru.lanit.constraint.PersonAgeConstraint;
import ru.lanit.constraint.PersonIdConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CarDto {

    @NotNull
    @CarIdConstraint
    private Long id;

    @NotNull
    @CarModelConstraint
    private String model;

    @NotNull
    @Positive
    private Integer horsepower;

    @NotNull
    @PersonIdConstraint(exists = true)
    @PersonAgeConstraint
    private Long ownerId;

    public CarDto() {}

    public CarDto(Long id, String model, Integer horsepower, Long ownerId) {
        this.id = id;
        this.model = model;
        this.horsepower = horsepower;
        this.ownerId = ownerId;
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

    public Integer getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Integer horsepower) {
        this.horsepower = horsepower;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
