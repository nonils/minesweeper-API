package com.sebastian.bogado.minesweeper.exeptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomException extends RuntimeException{
	private String message;
	private String[] messageArgs;
	private String description;
	private String[] descriptionArgs;
	private HttpStatus status;
}
