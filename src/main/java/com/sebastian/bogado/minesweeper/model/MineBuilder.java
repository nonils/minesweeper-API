package com.sebastian.bogado.minesweeper.model;

public class MineBuilder {
	private Integer row;
	private Integer column;
	private Board board;

	private MineBuilder() {

	}

	public static MineBuilder create() {
		return new MineBuilder();
	}

	public MineBuilder row(Integer row) {
		this.row = row;
		return this;
	}

	public MineBuilder column(Integer col) {
		column = col;
		return this;
	}

	public MineBuilder board(Board board) {
		this.board = board;
		return this;
	}

	public Cell build() {
		return new Cell(board, row, column, Cell.MINE_VALUE );
	}

}

