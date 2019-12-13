package ru.lanit.constraint;

import ru.lanit.validator.CarIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CarIdValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CarIdConstraint {
    String message() default "Need to exists in database: false";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
