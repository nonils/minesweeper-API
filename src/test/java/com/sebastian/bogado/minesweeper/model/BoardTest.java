package com.sebastian.bogado.minesweeper.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sebastian.bogado.minesweeper.model.Level.CUSTOM;
import static com.sebastian.bogado.minesweeper.model.Level.HARD;

public class BoardTest {
	@Test
	@DisplayName("Test that when I use Custom as Level to Create a New Board doesn't work")
	public void testUseCustomLevelToCreateNewBoard() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Board(CUSTOM);
		});
	}

	@Test
	@DisplayName("Test that when I use a different level that Custom  to Create a New Board works")
	public void testUseDifferentLevelToCustom() {
		Board board = new Board(HARD);
		Assertions.assertEquals(HARD.getMines().longValue(), board.getCells().stream().filter(Cell::isMine).count());
		Assertions.assertEquals(HARD.getColumns() * HARD.getRows(), board.getCells().size());
		Assertions.assertEquals(HARD.getColumns() * HARD.getRows() - HARD.getMines(), board.getOpenToWin());
	}

	@Test
	@DisplayName("Test that when I use a different level that Custom  to Create a New Board works")
	public void testUseAtribsForCreateCustom() {
		Integer cols = 16;
		Integer rows = 16;
		Integer mines = 30;
		Board board = new Board(rows, cols, mines);
		Assertions.assertEquals(mines.longValue(), board.getCells().stream().filter(Cell::isMine).count());
		Assertions.assertEquals(cols * rows, board.getCells().size());
		Assertions.assertEquals(rows * cols - mines, board.getOpenToWin());
	}
}
