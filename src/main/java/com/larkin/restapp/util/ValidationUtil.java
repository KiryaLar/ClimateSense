package com.larkin.restapp.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtil {
    public static String getErrorsAsString(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();

        var errors = bindingResult.getFieldErrors();
        for (var error : errors) {
            errorMessage.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        }

        return errorMessage.toString();
    }
}
