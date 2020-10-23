package com.sebastian.bogado.minesweeper.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Cell implements Serializable {

	public static final Integer MINE_VALUE = -1;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "board_id", nullable = false)
	private Board board;
	private Integer rowNumber;
	private Integer columnNumber;
	private Integer neighborMines;
	private Boolean flagged;
	private Boolean open;

	public Cell(Board board, Integer row, Integer column, Integer mineDistance) {
		this.board = board;
		this.rowNumber = row;
		this.columnNumber = column;
		neighborMines = mineDistance;
		open = false;
		flagged = false;
	}

	public boolean isMine() {
		return MINE_VALUE.equals(neighborMines);
	}

}
