package com.sebastian.bogado.minesweeper.exeptions;

public class InexistantMinesweeperGame extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public InexistantMinesweeperGame() {
		super("Couldn't find the game");
	}

}

