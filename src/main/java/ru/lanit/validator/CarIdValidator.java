package ru.lanit.validator;

import ru.lanit.constraint.CarIdConstraint;
import ru.lanit.repository.CarRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CarIdValidator implements
        ConstraintValidator<CarIdConstraint, Long> {
    private CarRepository carRepository;

    public CarIdValidator(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void initialize(CarIdConstraint constraintAnnotation) {}

    @Override
    public boolean isValid(Long carId, ConstraintValidatorContext constraintValidatorContext) {
        if (carId == null) {
            return true;
        }
        return !carRepository.findById(carId).isPresent();
    }

}
