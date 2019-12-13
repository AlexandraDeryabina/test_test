package ru.lanit.constraint;

import ru.lanit.validator.CarModelValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CarModelValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CarModelConstraint {
    String message() default "Incorrect model. {value} or more words are needed with split '{delimiter}'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int value() default 2;
    String delimiter() default "-";
}
