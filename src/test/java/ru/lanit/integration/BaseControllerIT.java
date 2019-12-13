package ru.lanit.integration;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.repository.CarRepository;
import ru.lanit.repository.PersonRepository;

@RunWith(SpringRunner.class)
//@ContextConfiguration()
@Transactional
public abstract class BaseControllerIT {
    protected PersonRepository personRepository;

    protected CarRepository carRepository;

    @Autowired
    public BaseControllerIT(PersonRepository personRepository, CarRepository carRepository) {
        this.personRepository = personRepository;
        this.carRepository = carRepository;
    }
}