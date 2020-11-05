package com.sebastian.bogado.minesweeper.service.impl;

import com.sebastian.bogado.minesweeper.controller.dto.CellsOpenedDTO;
import com.sebastian.bogado.minesweeper.exeptions.InexistantMinesweeperGame;
import com.sebastian.bogado.minesweeper.exeptions.InvalidMinesweeperOperation;
import com.sebastian.bogado.minesweeper.model.Board;
import com.sebastian.bogado.minesweeper.model.Cell;
import com.sebastian.bogado.minesweeper.model.Level;
import com.sebastian.bogado.minesweeper.repository.BoardRepository;
import com.sebastian.bogado.minesweeper.repository.CellRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
class MinesweeperServiceImplTest {

	@Mock
	private BoardRepository boardRepository;
	@Mock
	private CellRepository cellRepository;
	@InjectMocks
	private MinesweeperServiceImpl minesweeperService;

	@BeforeEach
	private void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test()
	void testCreateBoardAndThrowsExceptionIfLevelIsCustom() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> minesweeperService.createBoard(Level.CUSTOM));
	}

	@Test()
	void testCreateBoard() {
		when(boardRepository.save(any())).thenAnswer((Answer) invocation -> {
			Board generatedBoard = (Board) invocation.getArguments()[0];
			generatedBoard.setId(1L);
			return generatedBoard;
		});
		Assertions.assertEquals(1L, minesweeperService.createBoard(Level.BEGINNER));
	}

	@Test
	void testCreateCustomBoard() {
		when(boardRepository.save(any())).thenAnswer((Answer) invocation -> {
			Board generatedBoard = (Board) invocation.getArguments()[0];
			generatedBoard.setId(1L);
			return generatedBoard;
		});
		Assertions.assertEquals(1L, minesweeperService.createBoard(20, 20,9));
	}

	@Test
	void getBoard() {
		Board board = new Board(Level.BEGINNER);
		board.setId(1L);
		when(boardRepository.findById(1L)).thenReturn(java.util.Optional.of(board));
		CellsOpenedDTO result = minesweeperService.getBoard(1L);
		Assertions.assertEquals(0, result.getCellsOpened().size());
		Assertions.assertEquals(false, result.getGameFinished());
		Assertions.assertEquals(false, result.getWin());
	}

	@Test
	void flag() {
		Cell c = new Cell();
		c.setOpen(false);
		c.setFlagged(true);
		when(cellRepository.findCellByColumnAndRow(1L, 1, 1)).thenReturn(Optional.of(c));
		minesweeperService.flag(1, 1, 1L);
		verify(cellRepository, times(1)).findCellByColumnAndRow(1L, 1, 1);
		verify(cellRepository, times(1)).save(any(Cell.class));
	}

	@Test
	void flagInvalidColumnLower0() {
		when(cellRepository.findCellByColumnAndRow(1L, -1, 2)).thenReturn(Optional.empty());
		Assertions.assertThrows(InexistantMinesweeperGame.class, () -> minesweeperService.flag(-1, 2, 1L));
	}

	@Test
	void flagInvalidColumnBiggerThanCapacity() {
		when(cellRepository.findCellByColumnAndRow(1L, 9, 2)).thenReturn(Optional.empty());
		Assertions.assertThrows(InexistantMinesweeperGame.class, () -> minesweeperService.flag(9, 2, 1L));
	}

	@Test
	void flagOpenedCell() {
		Cell c = new Cell();
		c.setOpen(true);
		when(cellRepository.findCellByColumnAndRow(1L, 1, 1)).thenReturn(Optional.of(c));
		Assertions.assertThrows(InvalidMinesweeperOperation.class, () -> minesweeperService.flag(1, 1, 1L));
	}

	@Test
	void flagFlaggedCell() {
		Cell c = new Cell();
		c.setOpen(false);
		c.setFlagged(true);
		when(cellRepository.findCellByColumnAndRow(1L, 1, 1)).thenReturn(Optional.of(c));
		minesweeperService.flag(1, 1, 1L);
		verify(cellRepository, times(1)).findCellByColumnAndRow(1L, 1, 1);
		verify(cellRepository, times(1)).save(any(Cell.class));
	}

	@Test
	void openAndLose() {
		Board b = new Board(Level.BEGINNER);
		b.setId(1L);
		Cell cell = b.getCells().stream().filter(Cell::isMine).findFirst().get();

		when(cellRepository.findMines(1L)).thenReturn(new HashSet<>(Collections.singletonList(cell)));
		when(cellRepository.findCellByColumnAndRow(1L, cell.getColumnNumber(), cell.getRowNumber())).thenReturn(Optional.of(cell));
		CellsOpenedDTO result = minesweeperService.open(cell.getColumnNumber(), cell.getRowNumber(), 1L);
		Assertions.assertFalse(result.getWin());
		Assertions.assertTrue(result.getGameFinished());
	}

	@Test
	void openAndWin() {
		Board b = new Board(Level.BEGINNER);
		b.setId(1L);
		b.getCells().forEach(c -> {
			if (!c.isMine()) {
				c.setOpen(true);
			}
		});
		Cell cell = b.getCells().stream().filter(c -> !c.isMine()).findFirst().get();
		cell.setOpen(false);
		b.setOpenToWin(1);
		when(cellRepository.findCellByColumnAndRow(1L, cell.getColumnNumber(), cell.getRowNumber())).thenReturn(Optional.of(cell));
		when(cellRepository.save(any())).thenAnswer((Answer) invocation -> invocation.getArguments()[0]);
		CellsOpenedDTO result = minesweeperService.open(cell.getColumnNumber(), cell.getRowNumber(), 1L);
		Assertions.assertTrue(result.getGameFinished());
		Assertions.assertTrue(result.getWin());
	}


	@Test
	void openAndContinue() {
		Board b = new Board(Level.BEGINNER);
		b.setId(1L);
		Cell cell = b.getCells().stream().filter(c -> !c.isMine()).findFirst().get();
		when(cellRepository.findCellByColumnAndRow(1L, cell.getColumnNumber(), cell.getRowNumber())).thenReturn(Optional.of(cell));
		when(cellRepository.save(any())).thenAnswer((Answer) invocation -> invocation.getArguments()[0]);
		CellsOpenedDTO result = minesweeperService.open(cell.getColumnNumber(), cell.getRowNumber(), 1L);
		Assertions.assertFalse(result.getGameFinished());
		Assertions.assertFalse(result.getWin());
	}
}

