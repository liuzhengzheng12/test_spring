package com.example.myapplication.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {CancelEventValidator.class})
public @interface CancelEvent {

	String message() default "The event must be cancel_free_trial or cancel_subscription!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
