package ru.lanit.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.lanit.constraint.PersonIdConstraint;
import ru.lanit.deserializer.RuJsonDateDeserializer;
import ru.lanit.serializer.RuJsonDateSerializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

public class PersonDto {

    @NotNull
    @PersonIdConstraint
    private Long id;

    @NotNull
    private String name;

    @JsonDeserialize(using = RuJsonDateDeserializer.class)
    @JsonSerialize(using = RuJsonDateSerializer.class)
    @NotNull
    @Past
    private LocalDate birthdate;

    public PersonDto() {}

    public PersonDto(Long id, String name, LocalDate birthdate) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
