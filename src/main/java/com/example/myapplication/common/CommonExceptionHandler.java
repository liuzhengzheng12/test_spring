package com.example.myapplication.common;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseStatus(HttpStatus.OK)
	public CommonResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		BindingResult bindingResult = exception.getBindingResult();
		List<String> fieldErrorList = new ArrayList<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			fieldErrorList.add(fieldError.getDefaultMessage());
		}
		String msg = "ParameterValidException! " + String.join(", ", fieldErrorList);

		return CommonResult.validateFailed(msg);
	}

	@ExceptionHandler({ConstraintViolationException.class})
	@ResponseStatus(HttpStatus.OK)
	public CommonResult<String> handleConstraintViolationException(ConstraintViolationException ex) {
		return CommonResult.validateFailed(ex.getMessage());
	}
}
