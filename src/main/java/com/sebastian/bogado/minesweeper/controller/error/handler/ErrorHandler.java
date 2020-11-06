package com.sebastian.bogado.minesweeper.controller.error.handler;

import com.sebastian.bogado.minesweeper.exeptions.CustomException;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class ErrorHandler {

	private final MessageSource messageSource;

	public ErrorHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<CustomError> handleBusinessException(CustomException customException) {
		return new ResponseEntity<>(new CustomError(messageSource.getMessage(customException.getMessage(), customException.getMessageArgs(), Locale.getDefault()),
				messageSource.getMessage(customException.getDescription(), customException.getDescriptionArgs(), Locale.getDefault()),
				customException.getStatus().value()), customException.getStatus());
	}
}
