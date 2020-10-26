package com.sebastian.bogado.minesweeper.model;

import com.sebastian.bogado.minesweeper.exeptions.InvalidMinesweeperArgument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CellBuilderTest {

	@Test
	void testCreate() {
		CellBuilder cb = CellBuilder.create();
		Assertions.assertNotNull(cb);
	}

	@Test
	void testRow() {
		int value = 1;
		CellBuilder cb = CellBuilder.create();
		cb.row(value);
		Cell cell = cb.build();
		Assertions.assertEquals(value, cell.getRowNumber());
	}

	@Test
	void testColumn() {
		int value = 1;
		CellBuilder cb = CellBuilder.create();
		cb.column(value);
		Cell cell = cb.build();
		Assertions.assertEquals(value, cell.getColumnNumber());
	}

	@Test
	void testBoardElements() {

	}

	@Test
	void testBoard() {
		Board value = new Board();
		CellBuilder cb = CellBuilder.create();
		cb.board(value);
		Cell cell = cb.build();
		Assertions.assertEquals(value, cell.getBoard());
	}

	@Test
	void neighborsThrowException() {
		Board board = new Board();
		Assertions.assertThrows(InvalidMinesweeperArgument.class, () -> CellBuilder.create().neighbors(board));
	}

	@Test
	void neighborsWithoutException() {
		int value = 1;
		Cell[][] cells = new Cell[0][0];
		Board board = new Board();
		CellBuilder cb = CellBuilder.create();
		cb.column(value);
		cb.row(value);
		cb.board(board);
		cb.boardElements(cells);
		Cell cell = cb.build();
		Assertions.assertEquals(value, cell.getColumnNumber());
		Assertions.assertEquals(value, cell.getRowNumber());
		Assertions.assertEquals(board, cell.getBoard());
	}
}
