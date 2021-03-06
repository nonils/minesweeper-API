package com.sebastian.bogado.minesweeper.service;

import com.sebastian.bogado.minesweeper.controller.dto.CellsOpenedDTO;
import com.sebastian.bogado.minesweeper.model.Board;
import com.sebastian.bogado.minesweeper.model.Level;

public interface MinesweeperService {
	Board createBoard(Integer cols, Integer rows, Integer mines);

	CellsOpenedDTO getBoard(Long id);

	Board createBoard(Level level);

	CellsOpenedDTO open(Integer column, Integer row, Long gameId);

	void flag(Integer column, Integer row, Long gameId);
}
