package com.sebastian.bogado.minesweeper.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CellRequestDTO extends CellDTO {
	@ApiModelProperty(notes = "Identifier of the current game", required = true)
	private Long gameId;
}

