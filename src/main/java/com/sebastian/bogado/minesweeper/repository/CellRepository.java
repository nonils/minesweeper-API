package com.sebastian.bogado.minesweeper.repository;

import com.sebastian.bogado.minesweeper.model.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface CellRepository extends JpaRepository<Cell, Long> {
	@Query("SELECT c FROM Cell c WHERE c.rowNumber = :row and c.columnNumber = :column and c.board.id = :boardId")
	Optional<Cell> findCellByColumnAndRow(@Param("boardId") Long id, @Param("column") Integer column, @Param("row") Integer row);

	@Query("SELECT DISTINCT c FROM Cell c WHERE c.neighborMines=-1 and c.board.id= :boardId")
	Set<Cell> findMines(@Param("boardId") Long id);
}
