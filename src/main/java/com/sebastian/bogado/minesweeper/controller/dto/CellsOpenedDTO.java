package com.sebastian.bogado.minesweeper.controller.dto;

import com.sebastian.bogado.minesweeper.model.Board;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CellsOpenedDTO {
	private Boolean gameFinished;
	private Boolean win;
	private Set<CellResponseDTO> cellsOpened;

	public CellsOpenedDTO(Board board, Set<CellResponseDTO> cells) {
		this.cellsOpened = cells;
		win = board.win();
		gameFinished = board.getIsGameFinished();
	}

}

