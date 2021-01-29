package com.example.myapplication.validator;

import static com.example.myapplication.config.EnableEvent.ALL_CANCEL_EVENT;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CancelEventValidator implements ConstraintValidator<CancelEvent, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return Arrays.asList(ALL_CANCEL_EVENT).contains(value);
	}
}
