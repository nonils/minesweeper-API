package com.sebastian.bogado.minesweeper.exeptions;

import org.springframework.http.HttpStatus;

public class InvalidMinesweeperOperation extends CustomException {

	public InvalidMinesweeperOperation() {
		super("invalid.minesweeper.operation.message", null,"invalid.minesweeper.operation.description", null, HttpStatus.BAD_REQUEST);
	}

}
