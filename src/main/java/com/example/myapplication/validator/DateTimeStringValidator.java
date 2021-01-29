package com.example.myapplication.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateTimeStringValidator implements ConstraintValidator<DateTimeString, String> {

	private DateTimeFormatter formatter;

	@Override
	public void initialize(DateTimeString constraintAnnotation) {
		this.formatter = DateTimeFormatter.ofPattern(constraintAnnotation.pattern());
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			LocalDate.parse(value, formatter);

			return true;
		} catch (Exception exception) {
			return false;
		}
	}
}
