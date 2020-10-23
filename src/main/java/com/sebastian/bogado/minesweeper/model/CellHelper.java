package com.sebastian.bogado.minesweeper.model;

import java.util.ArrayList;
import java.util.List;

public class CellHelper {
	public static List<Cell> getNeighbors(Board board, Integer row, Integer column) {
		List<Cell> cells = new ArrayList<>();
		Level lev = board.getLevel();

		Integer firstRow = 0;
		Integer lastRow = lev.getRows() - 1;
		Integer firstColumn = 0;
		Integer lastColumn = lev.getColumns() - 1;

		Integer top = row > firstRow ? row - 1 : firstRow;
		Integer bottom = row < lastRow ? row + 1 : lastRow;
		Integer left = column > firstColumn ? column - 1 : firstColumn;
		Integer right = column < lastColumn ? column + 1 : lastColumn;
		for (int rowIndex = top;
		     rowIndex <= bottom;
		     rowIndex++) {
			for (int colIndex = left;
			     colIndex <= right;
			     colIndex++) {
				if (rowIndex != row || colIndex != column) {
					Cell neighbor = board.getCell(colIndex, rowIndex);
					if (neighbor != null) {
						cells.add(neighbor);
					}
				}

			}
		}
		return cells;
	}

	public static List<Cell> getNeighbors(Cell[][] elements, Level lev, Integer row, Integer column) {
		List<Cell> cells = new ArrayList<>();

		Integer firstRow = 0;
		Integer lastRow = lev.getRows() - 1;
		Integer firstColumn = 0;
		Integer lastColumn = lev.getColumns() - 1;

		Integer top = row > firstRow ? row - 1 : firstRow;
		Integer bottom = row < lastRow ? row + 1 : lastRow;
		Integer left = column > firstColumn ? column - 1 : firstColumn;
		Integer right = column < lastColumn ? column + 1 : lastColumn;
		for (int rowIndex = top;
		     rowIndex <= bottom;
		     rowIndex++) {
			for (int colIndex = left;
			     colIndex <= right;
			     colIndex++) {
				if (rowIndex != row || colIndex != column) {
					Cell neighbor = elements[colIndex][rowIndex];
					if (neighbor != null) {
						cells.add(neighbor);
					}
				}

			}
		}
		return cells;
	}
}

