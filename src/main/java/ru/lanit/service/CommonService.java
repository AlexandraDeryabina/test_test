package ru.lanit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanit.dto.response.StatisticsDto;

@Service
public class CommonService {
    private PersonService personService;
    private CarService carService;

    @Autowired
    public CommonService(PersonService personService, CarService carService) {
        this.personService = personService;
        this.carService = carService;
    }

    public StatisticsDto getStatistics() {
        return new StatisticsDto(
                personService.count(),
                carService.count(),
                carService.uniqueVendorCount()
        );
    }

    public void clearAll() {
        carService.clearAll();
        personService.clearAll();
    }
}
