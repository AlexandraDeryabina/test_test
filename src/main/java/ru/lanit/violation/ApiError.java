package ru.lanit.violation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.validation.Errors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ApiError {
    private final Errors errors;

    public ApiError(Errors errors) {
        this.errors = errors;
    }
}
