package com.sebastian.bogado.minesweeper.controller;

import com.sebastian.bogado.minesweeper.controller.dto.CellRequestDTO;
import com.sebastian.bogado.minesweeper.controller.dto.CellsOpenedDTO;
import com.sebastian.bogado.minesweeper.controller.dto.CreateGameRequest;
import com.sebastian.bogado.minesweeper.exeptions.InvalidMinesweeperArgument;
import com.sebastian.bogado.minesweeper.model.Level;
import com.sebastian.bogado.minesweeper.service.MinesweeperService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/minesweeper")
public class MinesweeperController {

	@Autowired
	private MinesweeperService minesweeperService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "resumeGame", nickname = "Resume game")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "Game id", required = true, dataType = "long", paramType = "query")
	})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = CellsOpenedDTO.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Failure")
	})
	public CellsOpenedDTO resumeGame(@RequestParam(name = "id") Long id) {
		CellsOpenedDTO board = minesweeperService.getBoard(id);
		return board;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "startGame", nickname = "Start new game")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created", response = Long.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 500, message = "Failure")
	})
	public Long startGame(@RequestBody CreateGameRequest createGameRequest) {
		if (!Level.CUSTOM.equals(createGameRequest.getLevel())) {
			return minesweeperService.createBoard(createGameRequest.getLevel());
		}
		validateCustomLevelRequest(createGameRequest);
		return minesweeperService.createBoard(  createGameRequest.getColumns(),
												createGameRequest.getRows(),
												createGameRequest.getMines());
	}

	private void validateCustomLevelRequest(CreateGameRequest createGameRequest) {
		if (createGameRequest.getColumns() == null || createGameRequest.getColumns() <= 0) {
			throw new InvalidMinesweeperArgument("The columns for a custom game must not be null and must be greater that 0");
		}
		if (createGameRequest.getRows() == null || createGameRequest.getRows() <= 0) {
			throw new InvalidMinesweeperArgument("The columns for a custom game must not be null and must be greater that 0");
		}
		if (createGameRequest.getMines() == null || createGameRequest.getMines() <= 0 || createGameRequest.getRows() * createGameRequest.getColumns() <= createGameRequest.getMines()) {
			throw new InvalidMinesweeperArgument("The mines for a custom game must not be null and must be greater that 0 and must be lower than " + createGameRequest.getRows() * createGameRequest.getColumns());
		}
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "leftClick", nickname = "Left click/Open cell")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = CellsOpenedDTO.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Failure")
	})
	public @ResponseBody
	CellsOpenedDTO leftClick(@RequestBody CellRequestDTO cell) {
		return minesweeperService.open(cell.getColumn(), cell.getRow(), cell.getGameId());
	}

	@PatchMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "rightClick", nickname = "Right click/flag")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = CellsOpenedDTO.class),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Failure")
	})
	public void rightClick(@RequestBody CellRequestDTO cell) {
		minesweeperService.flag(cell.getColumn(), cell.getRow(), cell.getGameId());
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ApiResponses(value = {@ApiResponse(code = 405, message = "Method not allowed")})
	public void delete() {
	}

}

