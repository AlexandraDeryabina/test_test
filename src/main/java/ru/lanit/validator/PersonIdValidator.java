package ru.lanit.validator;

import ru.lanit.constraint.PersonIdConstraint;
import ru.lanit.repository.PersonRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PersonIdValidator implements
        ConstraintValidator<PersonIdConstraint, Long> {

    private PersonRepository personRepository;
    private boolean exists;

    public PersonIdValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void initialize(PersonIdConstraint constraintAnnotation) {
        this.exists = constraintAnnotation.exists();
    }

    @Override
    public boolean isValid(Long personId, ConstraintValidatorContext constraintValidatorContext) {
        // Если не сделать эту заглушку, будет падать IllegalArgumentException в findById,
        // т.к. @NotNull не блокирует запуск кастомных валидаций
        if (personId == null) {
            return true;
        }

        if (exists) {
            return personRepository.findById(personId).isPresent();
        } else {
            return !personRepository.findById(personId).isPresent();
        }
    }
}
