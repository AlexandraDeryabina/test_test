package ru.lanit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.dto.request.CarDto;
import ru.lanit.entity.Car;
import ru.lanit.repository.CarRepository;
import ru.lanit.repository.PersonRepository;

import java.util.Set;
import java.util.TreeSet;

@Service
public class CarService {
    private CarRepository carRepository;
    private PersonRepository personRepository;

    private final String delimiter = "-";

    @Autowired
    public CarService(CarRepository carRepository, PersonRepository personRepository) {
        this.carRepository = carRepository;
        this.personRepository = personRepository;
    }

    @Transactional
    public CarDto save(CarDto carDto) {
        Car car = new Car(
                carDto.getId(),
                carDto.getModel(),
                carDto.getHorsepower()
                );
        car.setPerson(personRepository.findById(carDto.getOwnerId()).get());
        carRepository.saveAndFlush(car);
        return carDto;
    }

    public Long count() {
        return carRepository.count();
    }

    public Long uniqueVendorCount() {
        Set<String> vendorList = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);;
        for (Car car:carRepository.findAll()) {
            vendorList.add(car.getModel().split(delimiter)[0]);
        }
        return vendorList.stream().count();
    }

    public void clearAll() {
        carRepository.deleteAll();
    }
}
