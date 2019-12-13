package ru.lanit.constraint;

import ru.lanit.validator.PersonIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PersonIdValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonIdConstraint {
    String message() default "Need to exists in database: {exists}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    boolean exists() default false;
}
