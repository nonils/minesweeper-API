package com.sebastian.bogado.minesweeper.exeptions;

import org.springframework.http.HttpStatus;

public class InexistantMinesweeperGame extends CustomException {

	public InexistantMinesweeperGame() {
		super("inexistant.minesweeper.game.message", null, "inexistant.minesweeper.game.description", null, HttpStatus.NOT_FOUND);
	}

}

