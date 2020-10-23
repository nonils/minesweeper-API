package com.sebastian.bogado.minesweeper.model;

import com.sebastian.bogado.minesweeper.exeptions.InvalidMinesweeperArgument;

import java.util.List;

public class CellBuilder {
	private Integer row;
	private Integer column;
	private Integer mineNeighbors;
	private Cell[][] boardWithMines;
	private Board board;

	private CellBuilder() {
		mineNeighbors = 0;
	}

	public static CellBuilder create() {
		return new CellBuilder();
	}

	public CellBuilder row(Integer row) {
		this.row = row;
		return this;
	}

	public CellBuilder column(Integer col) {
		column = col;
		return this;
	}

	public CellBuilder boardElements(Cell[][] minedBoard) {
		boardWithMines = minedBoard;
		return this;
	}

	public CellBuilder board(Board board) {
		this.board = board;
		return this;
	}

	public CellBuilder neighbors(Board board) {
		if (row == null || column == null || boardWithMines == null) {
			throw new InvalidMinesweeperArgument("Missing argument to build board!");
		}
		List<Cell> neighbors = CellHelper.getNeighbors(boardWithMines, board.getLevel(), row, column);
		for (Cell neighbor : neighbors) {
			if (neighbor.isMine()) {
				mineNeighbors++;
			}
		}
		return this;
	}

	public Cell build() {
		return new Cell(board, row, column, mineNeighbors);
	}

}
