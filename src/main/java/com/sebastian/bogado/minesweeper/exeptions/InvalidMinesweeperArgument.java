package com.sebastian.bogado.minesweeper.exeptions;

import org.springframework.http.HttpStatus;

public class InvalidMinesweeperArgument extends CustomException {

	public InvalidMinesweeperArgument(String message) {
		super("invalid.minesweeper.argument.message", new String[]{message},"invalid.minesweeper.argument.description", null, HttpStatus.BAD_REQUEST);
	}

}
