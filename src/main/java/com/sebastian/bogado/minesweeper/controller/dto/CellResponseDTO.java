package com.sebastian.bogado.minesweeper.controller.dto;

import com.sebastian.bogado.minesweeper.model.Cell;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CellResponseDTO extends CellDTO {

	private Integer value;

	public CellResponseDTO(Cell cell) {
		super(cell.getColumnNumber(),cell.getRowNumber());
		value = cell.getNeighborMines();
	}


}

