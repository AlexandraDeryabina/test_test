package ru.lanit.integration.simple;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.lanit.config.AppConfig;
import ru.lanit.controller.CarController;
import ru.lanit.controller.PersonController;
import ru.lanit.dto.request.CarDto;
import ru.lanit.dto.request.PersonDto;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class ControllerIntegrationTest {
    private long personId = 400;
    private String personName = "PersonName";
    private LocalDate birthDate = LocalDate.now().minusYears(19);

    private long carId = 400;
    private String model = "VW-Polo";
    private int horsepower = 101;

    @Autowired
    PersonController personController;

    @Autowired
    CarController carController;

    @Test
    public void createPerson_personController_successfullyCreated() {
        PersonDto personDto = new PersonDto(
                personId,
                personName,
                birthDate
        );

        assertEquals(personController.create(personDto), ResponseEntity.ok().build());
    }

    @Test
    public void createCar_carController_successfullyCreated() {
        CarDto carDto = new CarDto(
                carId,
                model,
                horsepower,
                personId
        );

        assertEquals(carController.create(carDto), ResponseEntity.ok().build());
    }

    @Test
    public void getPersonWithCars_personController_success() {
        PersonDto personDto = new PersonDto(
                personId + 1,
                personName,
                birthDate
        );

        CarDto carDto = new CarDto(
                carId + 1,
                model,
                horsepower,
                personId + 1
        );

        assertEquals(personController.create(personDto), ResponseEntity.ok().build());
        assertEquals(carController.create(carDto), ResponseEntity.ok().build());

        assertEquals(personController.getPersonWithCars(personId + 1).getStatusCode(), HttpStatus.OK);
        assertEquals(personController.getPersonWithCars(personId + 2).getStatusCode(), HttpStatus.NOT_FOUND);
    }
}