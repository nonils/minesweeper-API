package com.sebastian.bogado.minesweeper.repository;

import com.sebastian.bogado.minesweeper.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
