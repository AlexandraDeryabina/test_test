package ru.lanit.validator;

import ru.lanit.constraint.PersonAgeConstraint;
import ru.lanit.entity.Person;
import ru.lanit.repository.PersonRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Optional;

public class PersonAgeValidator implements
        ConstraintValidator<PersonAgeConstraint, Long> {

    private PersonRepository personRepository;
    private int age;

    public PersonAgeValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void initialize(PersonAgeConstraint constraintAnnotation) {
        this.age = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Long personId, ConstraintValidatorContext constraintValidatorContext) {
        // Если не сделать эту заглушку, будет падать IllegalArgumentException в findById,
        // т.к. @NotNull не блокирует запуск кастомных валидаций
        if (personId == null) {
            return true;
        }

        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (!optionalPerson.isPresent()) {
            return true;
        }

        if (LocalDate.now().minusYears(age).isAfter(optionalPerson.get().getBirthdate())) {
            return true;
        }

        return false;
    }
}
