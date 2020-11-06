package com.sebastian.bogado.minesweeper.exeptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException{
	private String message;
	private String[] messageArgs;
	private String description;
	private String[] descriptionArgs;
	private HttpStatus status;
}
