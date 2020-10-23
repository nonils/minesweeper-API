package com.sebastian.bogado.minesweeper.model;

public enum Level {
	BEGINNER(8, 8, 10), INTERMEDIATE(16, 16, 40), HARD(24, 24, 99),
	CUSTOM(null, null, null);

	private final Integer rows;
	private final Integer columns;
	private final Integer mines;

	Level(Integer columns, Integer rows, Integer mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
	}

	public Integer getRows() {
		return rows;
	}

	public Integer getColumns() {
		return columns;
	}

	public Integer getMines() {
		return mines;
	}
}
