/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Mar-2023
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
import org.springframework.web.server.ResponseStatusException;

import com.jema.app.dto.BranchDTO;
import com.jema.app.dto.BranchView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.Branch;
import com.jema.app.entities.Chemical;
import com.jema.app.repositories.BranchRepository;
import com.jema.app.response.DepartmentErrorResponse;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.BranchService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Branch Controller")
@RestController
public class BranchController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(BranchController.class);

	@Autowired
	BranchService branchService;
	
	@Autowired
	BranchRepository bracnBranchRepository;
	
	/*
	 * ======================== Branch ADD ==================================
	 */
	@CrossOrigin
	@PostMapping(value = BRANCH_ADD, produces = "application/json")
	public ResponseEntity<?> addBranch(@Valid @RequestBody BranchDTO branchDTO) {
	    logger.info("Request: In Branch Controller for Add Branch: {}", branchDTO);
	    GenericResponse genericResponse = new GenericResponse();

	    try {
	        // Check if the code or name already exists before saving
	        if (branchService.isEmailOrNameExists(branchDTO.getEmail(), branchDTO.getName())) {
	            DepartmentErrorResponse customResponse = new DepartmentErrorResponse();
	            customResponse.setStatus(HttpStatus.CONFLICT.value());
	            customResponse.setError(HttpStatus.CONFLICT.getReasonPhrase());
	            customResponse.setMessage("Branch with the same email or name already exists.");
	            customResponse.setTimestamp(new Date());
	            return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
	        }

	        Branch branch = new Branch();
	        BeanUtils.copyProperties(branchDTO, branch);
	        branch.setCreateTime(new Date());
	        branch.setUpdateTime(new Date());
	        Long id = branchService.save(branch);
	        branchDTO.setId(id);

	        return new ResponseEntity<GenericResponse>(
	                genericResponse.getResponse(branchDTO, "Branch successfully added", HttpStatus.OK), HttpStatus.OK);
	    } catch (IllegalArgumentException e) {
	        // Handle the IllegalArgumentException and send a custom error response
	        DepartmentErrorResponse customResponse = new DepartmentErrorResponse();
	        customResponse.setStatus(HttpStatus.CONFLICT.value());
	        customResponse.setError(HttpStatus.CONFLICT.getReasonPhrase());
	        customResponse.setMessage(e.getMessage());
	        customResponse.setTimestamp(new Date());
	        return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
	    } catch (Exception e) {
	        // Handle any other unexpected exceptions and send a custom error response
	        DepartmentErrorResponse customResponse = new DepartmentErrorResponse();
	        customResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        customResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	        customResponse.setMessage("An unexpected error occurred.");
	        customResponse.setTimestamp(new Date());
	        return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	/*
	 * ======================== Branch Edit/Update ========================
	 */
	@CrossOrigin
	@PutMapping(value = BRANCH_UPDATE, produces = "application/json")
	public ResponseEntity<?> updateBranch(@PathVariable(name = "id", required = true) Long id,
	                                      @Valid @RequestBody BranchDTO branchDTO) {
	    logger.info("Request: In Branch Controller for Update Branch: {}", branchDTO);
	    GenericResponse genericResponse = new GenericResponse();

	    Branch existingBranch = branchService.findById(id);

	    if (existingBranch != null) {
	        Branch branch = new Branch();
	        BeanUtils.copyProperties(branchDTO, branch);
	        branch.setId(id);
	        branch.setCreateTime(existingBranch.getCreateTime());
	        branch.setUpdateTime(new Date());

	        try {
	            // Update and save the branch using the new method
	            Branch updatedBranch = branchService.updateBranch(id, branchDTO.getName(), branchDTO.getEmail());

	            // Return the updated branch
	            return ResponseEntity.ok(updatedBranch);
	        } catch (ResponseStatusException e) {
	            // Handle the uniqueness or other validation error
	            return new ResponseEntity<>(genericResponse.getResponse("", e.getReason(), HttpStatus.BAD_REQUEST),
	                    HttpStatus.BAD_REQUEST);
	        }
	    } else {
	        return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Branch", HttpStatus.BAD_REQUEST),
	                HttpStatus.BAD_REQUEST);
	    }
	}


	/*
	 * ======================== Get All Branch's ==================================
	 */

	@ApiOperation(value = "Get All Branch with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = BRANCH_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllBranch(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all barnch {} ", pageRequestDTO);

//		Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.DESC, pageRequestDTO.getSort()).ignoreCase());
//		Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sortBy);
//
//		Page<Branch> page;
//		Long recordsCount = 0l;
//		if (pageRequestDTO.getKeyword() == null || pageRequestDTO.getKeyword().trim().isEmpty()) {
//			page = branchService.findAll(pageable);
//		} else {
//			page = branchService.findAllByName(pageRequestDTO.getKeyword().trim(), pageable);
//		}
//		recordsCount = branchService.getCount(pageRequestDTO.getKeyword().trim());
//		Object obj = (new PageResponseDTO()).getRespose(page.getContent(), recordsCount);

		Long recordsCount = 0l;

		List<BranchView> dataList = branchService.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);

		return onSuccess(obj, Constants.BRANCH_FETCHED);
	}

	/*
	 * ======================== Find One Branch ========================
	 */

	@ApiOperation(value = "Find Branch By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = BRANCH_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In BranchController Find branch By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		Branch branch = branchService.findById(id);
		String msg = branch != null ? "Branch Found" : "No Branch found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);

		return new ResponseEntity<>(
				response.getResponse(branch, branch != null ? "Branch Found" : "No Branch found", HttpStatus.OK),
				HttpStatus.OK);
	}

	/*
	 * ======================== Delete Branch ========================
	 */

	@ApiOperation(value = "Delete Branch", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = BRANCH_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In BranchController Delete branch of ids  :{} ", idsArrays);

		int status = branchService.deleteBranch(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Branch Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Branch successfully deleted", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Branch");

			return new ResponseEntity<>(response.getResponse("", "Invalid Branch", HttpStatus.OK), HttpStatus.OK);
		}
	}

	/*
	 * ======================== Update Branch Status========================
	 */

	@ApiOperation(value = "Update Branch Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = BRANCH_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@PathVariable Long id, @PathVariable Integer status) {
		log.info("REST Request In BranchController to update status {} {},", id, status);
		int res = branchService.updateBranchStatus(id, status);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", id, "Branch Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Branch successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", id, "Invalid Branch");

			return new ResponseEntity<>(response.getResponse("", "Invalid Branch", HttpStatus.OK), HttpStatus.OK);
		}
	}
}
