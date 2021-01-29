package com.example.myapplication.validator;

import static com.example.myapplication.config.EnableEvent.ALL_PAY_EVENT;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PayEventValidator implements ConstraintValidator<PayEvent, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return Arrays.asList(ALL_PAY_EVENT).contains(value);
	}
}
