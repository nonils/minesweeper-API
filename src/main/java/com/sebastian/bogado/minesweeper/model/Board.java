package com.sebastian.bogado.minesweeper.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL)
	private List<Cell> cells;
	@NotNull
	private Integer openToWin;
	@NotNull
	private Boolean isGameFinished;
	@Enumerated(EnumType.STRING)
	private Level level;
	private Integer columns;
	private Integer rows;
	private Integer mines;

	public Board(Level level) {
		if (Level.CUSTOM.equals(level)) {
			throw new IllegalArgumentException("The custom level could not be used for create a new board with this constructor. Please, use the Board(Integer rows, Integer cols, Integer mines)");
		}
		this.level = level;
		this.columns = level.getColumns();
		this.rows = level.getRows();
		this.mines = level.getMines();
		openToWin = (this.columns * this.rows) - this.mines;
		this.cells = generateBoard();
		this.isGameFinished = false;
	}

	public Board(Integer rows, Integer cols, Integer mines) {
		this.level = Level.CUSTOM;
		this.columns = cols;
		this.rows = rows;
		this.mines = mines;
		openToWin = (this.columns * this.rows) - this.mines;
		this.cells = generateBoard();
		this.isGameFinished = false;
	}

	private List<Cell> generateBoard() {
		Cell[][] elements = new Cell[this.columns][this.rows];
		generateMines(elements);
		fillBoard(elements);
		List<Cell> boardCells = new ArrayList<>();
		for (Cell[] cells : elements) {
			boardCells.addAll(Arrays.asList(cells));
		}
		return boardCells;
	}

	private void fillBoard(Cell[][] elements) {
		for (int col = 0; col < this.columns; col++) {
			for (int row = 0; row < this.rows; row++) {
				if (elements[col][row] == null) {
					Cell cell = CellBuilder.create().board(this)
							.column(col).row(row).boardElements(elements)
							.neighbors(this).build();
					elements[col][row] = cell;
				}
			}
		}
	}

	private void generateMines(Cell[][] elements) {
		Integer minesLeftToBeCreated = this.mines;
		ThreadLocalRandom randomizer = ThreadLocalRandom.current();
		while (minesLeftToBeCreated > 0) {
			Integer mineRow = randomizer.nextInt(this.rows);
			Integer mineCol = randomizer.nextInt(this.columns);
			if (elements[mineCol][mineRow] == null) {
				Cell mine = MineBuilder.create().board(this).column(mineCol).row(mineRow).build();
				elements[mineCol][mineRow] = mine;
				minesLeftToBeCreated--;
			}

		}
	}

	public Cell getCell(Integer column, Integer row) {
		return cells.stream().filter(cell -> cell.getColumnNumber().equals(column) && cell.getRowNumber().equals(row)).findFirst()
				.get();
	}

	public Boolean win() {
		return openToWin == 0;
	}

	public void decrementOpenToWin() {
		openToWin--;
	}

}
