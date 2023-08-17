/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 21-Apr-2023
*
*/

package com.jema.app.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.dto.BoardUniversityDTO;
import com.jema.app.entities.BoardUniversity;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.BoardUniversityService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Board University Controller")
@RestController
public class BoardUniversityController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(BoardUniversityController.class);

	@Autowired
	BoardUniversityService boardUniversityService;

	/*
	 * ======================== Board University ADD =================
	 */
	@CrossOrigin
	@PostMapping(value = BOARD_UNIVERSITY_ADD, produces = "application/json")
	public ResponseEntity<?> addBoardUniversity(@Valid @RequestBody BoardUniversityDTO boardUniversityDTO) {
		logger.info("Request:In Board University Controller for Add Board University :{} ", boardUniversityDTO);
		GenericResponse genericResponse = new GenericResponse();

		BoardUniversity boardUniversity = new BoardUniversity();
		BeanUtils.copyProperties(boardUniversityDTO, boardUniversity);
		boardUniversity.setCreateTime(new Date());
		boardUniversity.setUpdateTime(new Date());
		Long id = boardUniversityService.save(boardUniversity);
		boardUniversityDTO.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(boardUniversityDTO, "Successfully added", HttpStatus.OK), HttpStatus.OK);

	}

	/*
	 * ======================== Delete Board University ========================
	 */

	@ApiOperation(value = "Delete Board University", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = BOARD_UNIVERSITY_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In Board University Controller Delete Board University of ids  :{} ", idsArrays);

		int status = boardUniversityService.delete(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Successfully deleted", HttpStatus.OK), HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid BoardUniversity");

			return new ResponseEntity<>(response.getResponse("", "Invalid BoardUniversity", HttpStatus.OK),
					HttpStatus.OK);
		}
	}

	/*
	 * ======================== Board University Edit/Update ====================
	 */
	@CrossOrigin
	@PutMapping(value = BOARD_UNIVERSITY_UPDATE, produces = "application/json")
	public ResponseEntity<?> updateBoardUniversity(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody BoardUniversityDTO boardUniversityDTO) {
		logger.info("Request:In BoardUniversity Controller for Update BoardUniversity :{} ", boardUniversityDTO);
		GenericResponse genericResponse = new GenericResponse();

		BoardUniversity mBoardUniversity = boardUniversityService.findById(id);

		if (mBoardUniversity != null) {

			BoardUniversity boardUniversity = new BoardUniversity();
			BeanUtils.copyProperties(boardUniversityDTO, boardUniversity);
			boardUniversity.setId(id);
			boardUniversity.setCreateTime(mBoardUniversity.getCreateTime());
			boardUniversity.setUpdateTime(new Date());

			Long mID = boardUniversityService.save(boardUniversity);
			boardUniversityDTO.setId(mID);
			return new ResponseEntity<GenericResponse>(genericResponse.getResponse(boardUniversityDTO,
					"Board University successfully Updated", HttpStatus.OK), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Board University", HttpStatus.OK),
					HttpStatus.OK);
		}

	}

	/*
	 * ======================== Get All Board University =================
	 */

	@ApiOperation(value = "Get All Board University", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = BOARD_UNIVERSITY_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllBoardUniversity() {
		logger.info("Rest request to get all BoardUniversity");

		List<BoardUniversity> mBoardUniversity = boardUniversityService.findAll();
		return onSuccess(mBoardUniversity, Constants.BOARD_UNIVERSITY_FETCHED);
	}

}
