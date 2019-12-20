package ru.lanit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lanit.dto.request.PersonDto;
import ru.lanit.dto.response.PersonWithCarsDto;
import ru.lanit.exception.PersonNotFoundException;
import ru.lanit.service.PersonService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody PersonDto dto) {
        personService.save(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/personwithcars", params = "personid")
    @ResponseBody
    public ResponseEntity getPersonWithCars(
            @Valid @NotNull @RequestParam("personid") Long personId) {
        try {
            if (personId == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(personService.findPersonWithCarsByPersonId(personId));
        } catch (PersonNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}

