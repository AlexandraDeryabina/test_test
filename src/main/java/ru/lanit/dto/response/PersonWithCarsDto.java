package ru.lanit.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.lanit.dto.request.CarDto;
import ru.lanit.serializer.RuJsonDateSerializer;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class PersonWithCarsDto {
    private long id;

    @NotNull
    private String name;

    @JsonSerialize(using = RuJsonDateSerializer.class)
    private LocalDate birthdate;

    private List<CarDto> cars;

    public PersonWithCarsDto() {
    }

    public PersonWithCarsDto(Long id, String name, LocalDate birthdate, List<CarDto> cars) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.cars = cars;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<CarDto> getCars() {
        return cars;
    }

    public void setCars(List<CarDto> cars) {
        this.cars = cars;
    }
}
