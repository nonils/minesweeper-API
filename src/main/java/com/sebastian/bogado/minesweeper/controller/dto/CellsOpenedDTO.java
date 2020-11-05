package com.sebastian.bogado.minesweeper.controller.dto;

import com.sebastian.bogado.minesweeper.model.Board;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CellsOpenedDTO {
	private Long gameId;
	private Integer columns;
	private Integer rows;
	private Boolean gameFinished;
	private Boolean win;
	private Set<CellResponseDTO> cellsOpened;

	public CellsOpenedDTO(Board board, Set<CellResponseDTO> cells) {
		this.gameId = board.getId();
		this.columns = board.getColumns();
		this.rows = board.getRows();
		this.cellsOpened = cells;
		win = board.win();
		gameFinished = board.getIsGameFinished();
	}

}

