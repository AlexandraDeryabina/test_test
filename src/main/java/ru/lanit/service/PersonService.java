package ru.lanit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.dto.request.CarDto;
import ru.lanit.dto.request.PersonDto;
import ru.lanit.dto.response.PersonWithCarsDto;
import ru.lanit.entity.Car;
import ru.lanit.entity.Person;
import ru.lanit.exception.PersonNotFoundException;
import ru.lanit.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    private PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PersonDto save(PersonDto personDto) {
        Person person = new Person(
                personDto.getId(),
                personDto.getName(),
                personDto.getBirthdate());
        repository.saveAndFlush(person);
        return personDto;
    }

    @Transactional
    public PersonWithCarsDto findPersonWithCarsByPersonId(Long personId) throws PersonNotFoundException {
        Person person = getById(personId);
        List<CarDto> carDtoList = new ArrayList<>();
        for (Car car: person.getCarList()) {
            carDtoList.add(new CarDto(
                    car.getId(),
                    car.getModel(),
                    car.getHorsepower(),
                    personId
            ));
        }
        return new PersonWithCarsDto(
                person.getId(),
                person.getName(),
                person.getBirthdate(),
                carDtoList
        );
    }

    public Long count() {
        return repository.count();
    }

    public void clearAll() {
        repository.deleteAll();
    }

    public Person getById(Long personId) throws PersonNotFoundException {
        return repository.findById(personId).orElseThrow(PersonNotFoundException::new);
    }
}
