package com.sebastian.bogado.minesweeper.exeptions;

public class InvalidMinesweeperOperation extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public InvalidMinesweeperOperation() {
		super("This action cannot be performed!");
	}

}
