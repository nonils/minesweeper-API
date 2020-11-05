package com.sebastian.bogado.minesweeper.service.impl;

import com.sebastian.bogado.minesweeper.controller.dto.CellResponseDTO;
import com.sebastian.bogado.minesweeper.controller.dto.CellsOpenedDTO;
import com.sebastian.bogado.minesweeper.exeptions.InexistantMinesweeperGame;
import com.sebastian.bogado.minesweeper.exeptions.InvalidMinesweeperOperation;
import com.sebastian.bogado.minesweeper.model.Board;
import com.sebastian.bogado.minesweeper.model.Cell;
import com.sebastian.bogado.minesweeper.model.CellHelper;
import com.sebastian.bogado.minesweeper.model.Level;
import com.sebastian.bogado.minesweeper.repository.BoardRepository;
import com.sebastian.bogado.minesweeper.repository.CellRepository;
import com.sebastian.bogado.minesweeper.service.MinesweeperService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MinesweeperServiceImpl implements MinesweeperService {
	private final BoardRepository boardRepository;
	private final CellRepository cellRepository;

	public MinesweeperServiceImpl(BoardRepository boardRepository, CellRepository cellRepository) {
		this.boardRepository = boardRepository;
		this.cellRepository = cellRepository;
	}

	@Override
	public Board createBoard(Level level) {
		Board board = new Board(level);
		return boardRepository.save(board);
	}

	@Override
	public Board createBoard(Integer cols, Integer rows, Integer mines) {
		Board board = new Board(rows, cols, mines);
		return boardRepository.save(board);
	}

	@Override
	public CellsOpenedDTO getBoard(Long id) {
		Board board = boardRepository.findById(id).orElseThrow(() -> new InexistantMinesweeperGame());
		initalizeCells(board);
		Set<CellResponseDTO> cells = getOpenedCells(board);
		return new CellsOpenedDTO(board, cells);

	}

	@Override
	public void flag(Integer column, Integer row, Long gameId) {
		Cell cell = cellRepository.findCellByColumnAndRow(gameId, column, row).orElseThrow(InexistantMinesweeperGame::new);
		if (cell.getOpen()) {
			throw new InvalidMinesweeperOperation();
		}
		cell.setFlagged(!cell.getFlagged());
		cellRepository.save(cell);
	}

	@Override
	public CellsOpenedDTO open(Integer column, Integer row, Long gameId) {
		Cell cell = cellRepository.findCellByColumnAndRow(gameId, column, row).orElseThrow(InexistantMinesweeperGame::new);
		if (cell.getOpen()) {
			throw new InvalidMinesweeperOperation();
		}
		Set<CellResponseDTO> cells = openCell(cell);
		Board board = cell.getBoard();
		Boolean gameFinished = board.win() || cell.isMine();
		board.setIsGameFinished(gameFinished);
		boardRepository.save(board);
		return new CellsOpenedDTO(board, cells);
	}

	private void initalizeCells(Board board) {
		Hibernate.initialize(board.getCells());
	}

	private Set<CellResponseDTO> openCell(Cell cell) {
		if (cell.getOpen() || cell.getFlagged()) {
			throw new InvalidMinesweeperOperation();
		}

		Board board = cell.getBoard();
		if (cell.getNeighborMines() == 0) {
			Hibernate.initialize(board.getCells());
			initalizeCells(board);
			Set<Cell> openCellsRecursive = openCellsRecursive(new HashSet<>(), cell);
			Set<CellResponseDTO> returnedCells = openCellsRecursive.stream().map(CellResponseDTO::new)
					.collect(Collectors.toSet());
			return returnedCells;
		}

		if (cell.isMine()) {
			return openMines(board);
		}

		cell.setOpen(Boolean.TRUE);
		board.decrementOpenToWin();
		return Collections.singleton(new CellResponseDTO(cell));
	}

	private Set<CellResponseDTO> openMines(Board board) {
		Set<Cell> mines = cellRepository.findMines(board.getId());
		Set<CellResponseDTO> minesResponse = new HashSet<>();
		for (Cell mine : mines) {
			mine.setOpen(Boolean.TRUE);
			minesResponse.add(new CellResponseDTO(mine));
		}
		cellRepository.saveAll(mines);
		return minesResponse;
	}

	private Set<Cell> openCellsRecursive(Set<Cell> cells, Cell current) {
		if (current.isMine() || current.getFlagged()) {
			return cells;
		}

		if (!current.getOpen()) {
			current.setOpen(Boolean.TRUE);
			current.getBoard().decrementOpenToWin();
			current = cellRepository.save(current);
			cells.add(current);
		}

		if (current.getNeighborMines() == 0) {
			List<Cell> neighbors = CellHelper.getNeighbors(current.getBoard(), current.getRowNumber(), current.getColumnNumber()).stream().filter(c-> !c.getOpen()).collect(Collectors.toList());
			for (Cell cell : neighbors) {
				if (!cells.contains(cell)) {
					cells.addAll(openCellsRecursive(cells, cell));
				}
			}
		}

		return cells;

	}

	private Set<CellResponseDTO> getOpenedCells(Board board) {
		return board.getCells().stream().filter(c -> c.getOpen()).map(c -> new CellResponseDTO(c))
				.collect(Collectors.toSet());
	}

}
