package com.sebastian.bogado.minesweeper.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CellDTO {
	@ApiModelProperty(notes = "Column of the cell", required = true)
	private Integer column;
	@ApiModelProperty(notes = "Row of the cell", required = true)
	private Integer row;
}
