package ru.lanit.unit.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lanit.entity.Car;
import ru.lanit.entity.Person;
import ru.lanit.exception.PersonNotFoundException;
import ru.lanit.repository.PersonRepository;
import ru.lanit.service.PersonService;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerUnitTest {

    private long personId = 1;
    private String personName = "PersonName";
    private LocalDate birthDate = LocalDate.now().minusYears(19);

    private long carId = 1;
    private String model = "VW-Polo";
    private int horsepower = 101;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService = new PersonService(personRepository);

    @Test
    public void createPersonByMockito_shouldReturnMockedPerson() {
        Person person = new Person(
                personId,
                personName,
                birthDate
        );

        Mockito
                .when(personRepository.findById(personId))
                .thenReturn(java.util.Optional.of(person));

        try {
            Person mockedPerson = personService.getById(personId);
            assertEquals(person, mockedPerson);

        } catch (PersonNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getPersonWithCars_shouldReturn2Cars() {
        Person person = new Person(
                personId,
                personName,
                birthDate
        );

        ArrayList<Car> cars = new ArrayList<>();

        Car car1 = new Car(
                carId,
                model,
                horsepower
        );

        Car car2 = new Car(
                carId + 1,
                model + "Sedan",
                horsepower + 10
        );
        car1.setPerson(person);
        car2.setPerson(person);
        cars.add(car1);
        cars.add(car2);
        person.setCarList(cars);

        Mockito
                .when(personRepository.findById(personId))
                .thenReturn(java.util.Optional.of(person));

        try {
            assertEquals(
                    personService.findPersonWithCarsByPersonId(personId).getId(),
                    personId
            );

            assertEquals(
                    personService.findPersonWithCarsByPersonId(personId).getCars().size(),
                    2
            );

        } catch (PersonNotFoundException e) {
            fail(e.getMessage());
        }

    }
}