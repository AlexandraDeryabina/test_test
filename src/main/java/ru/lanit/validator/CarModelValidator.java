package ru.lanit.validator;

import ru.lanit.constraint.CarModelConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CarModelValidator implements
        ConstraintValidator<CarModelConstraint, String> {

    private int quantity;
    private String delimiter;

    @Override
    public void initialize(CarModelConstraint constraintAnnotation) {
        this.quantity = constraintAnnotation.value();
        this.delimiter = constraintAnnotation.delimiter();
    }

    @Override
    public boolean isValid(String model, ConstraintValidatorContext constraintValidatorContext) {
        // Если не сделать эту заглушку, будет падать ValidationException,
        // т.к. @NotNull не блокирует запуск кастомных валидаций
        if (model == null) {
            return true;
        }
        String[] modelPieces = model.split(delimiter);
        if (modelPieces.length < quantity) {
            return false;
        }

        if (modelPieces[0].length() == 0) {
            return false;
        }

        return true;
    }

}
