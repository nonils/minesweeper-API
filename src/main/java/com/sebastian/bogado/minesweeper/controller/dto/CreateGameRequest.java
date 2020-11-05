package com.sebastian.bogado.minesweeper.controller.dto;

import com.sebastian.bogado.minesweeper.model.Level;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateGameRequest {
	@NotNull
	@ApiModelProperty(notes = "Identifier of the current game", required = true)
	private Level level;
	private Integer columns;
	private Integer rows;
	private Integer mines;
}
