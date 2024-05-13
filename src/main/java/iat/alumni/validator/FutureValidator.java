package iat.alumni.validator;

import java.time.LocalDateTime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Future;

public class FutureValidator implements ConstraintValidator<Future, LocalDateTime> {

	@Override
    public void initialize(Future constraintAnnotation) {}
	
	@Override
	public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
		return value == null || value.isAfter(LocalDateTime.now());
	}

}