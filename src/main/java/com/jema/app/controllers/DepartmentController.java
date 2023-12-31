/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 08-Apr-2023
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.jema.app.dto.DepartmentDTO;
import com.jema.app.dto.DepartmentView;
import com.jema.app.dto.LeaveManagementView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.Branch;
import com.jema.app.entities.Department;
import com.jema.app.response.DepartmentErrorResponse;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.BranchService;
import com.jema.app.service.DepartmentService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Department Controller")
@RestController
public class DepartmentController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	BranchService branchService;

	/*
	 * ======================== Department ADD ==================================
	 */
	@CrossOrigin
	@PostMapping(value = DEPARTMENT_ADD, produces = "application/json")
	public ResponseEntity<?> addDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
	    logger.info("Request:In Department Controller for Add Department: {}", departmentDTO);
	    Department department = new Department();
	    BeanUtils.copyProperties(departmentDTO, department);
	    department.setCreateTime(new Date());
	    department.setUpdateTime(new Date());

	    try {
	        Long id = departmentService.save(department);
	        departmentDTO.setId(id);

	        GenericResponse genericResponse = new GenericResponse();
	        return new ResponseEntity<GenericResponse>(
	                genericResponse.getResponse(departmentDTO, "Department successfully added", HttpStatus.OK),
	                HttpStatus.OK);
	    } catch (IllegalArgumentException e) {
	        DepartmentErrorResponse errorResponse = new DepartmentErrorResponse();
	        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
	        errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
	        errorResponse.setMessage(e.getMessage());
	        errorResponse.setTimestamp(new Date());
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        DepartmentErrorResponse errorResponse = new DepartmentErrorResponse();
	        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        errorResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	        errorResponse.setMessage("An unexpected error occurred.");
	        errorResponse.setTimestamp(new Date());
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	/*
	 * ======================== Department Edit/Update ========================
	 */
	@CrossOrigin
	@PutMapping(value = DEPARTMENT_UPDATE, produces = "application/json")
	public ResponseEntity<?> updateDepartment(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody DepartmentDTO departmentDTO) {
		logger.info("Request:In Department Controller for Update Department :{} ", departmentDTO);
		GenericResponse genericResponse = new GenericResponse();
		Department mDepartment = departmentService.findById(id);

		if (mDepartment != null) {

			Department department = new Department();
			department.setId(id);
			department.setName(departmentDTO.getName());
			department.setBranch(departmentDTO.getBranch());
			department.setCode(departmentDTO.getCode());
			department.setManagedBy(departmentDTO.getManagedBy());
			department.setStatus(mDepartment.getStatus());
			department.setCreateTime(mDepartment.getCreateTime());
			department.setUpdateTime(new Date());

			Long mID = departmentService.save(department);
			if (mID > 0)
				return new ResponseEntity<GenericResponse>(
						genericResponse.getResponse(department, "Department successfully Updated", HttpStatus.OK),
						HttpStatus.OK);
			else
				return new ResponseEntity<>(
						genericResponse.getResponse("", "Error While Department Updating", HttpStatus.OK),
						HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Department", HttpStatus.OK),
					HttpStatus.OK);
		}

	}

	/*
	 * ======================== Get All Department's ========================
	 */

	@ApiOperation(value = "Get All Department with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = DEPARTMENT_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllDepartment(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Department {} ", pageRequestDTO);

//		Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.DESC, pageRequestDTO.getSort()).ignoreCase());
//		Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sortBy);
//
//		Page<Department> page;
//		Long recordsCount = 0l;
//		if (pageRequestDTO.getKeyword() == null || pageRequestDTO.getKeyword().trim().isEmpty()) {
//			page = departmentService.findAll(pageable);
//		} else {
//			page = departmentService.findAllByName(pageRequestDTO.getKeyword().trim(), pageable);
//		}
//		recordsCount = departmentService.getCount(pageRequestDTO.getKeyword().trim());
//		Object obj = (new PageResponseDTO()).getRespose(page.getContent(), recordsCount);
		
		Long recordsCount = 0l;

		List<DepartmentView> dataList = departmentService.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);
		
		return onSuccess(obj, Constants.DEPARTMENT_FETCHED);
	}

	/*
	 * ======================== Find One Department ========================
	 */

	@ApiOperation(value = "Find Department By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = DEPARTMENT_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In DepartmentController Find branch By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		Department department = departmentService.findById(id);
		String msg = department != null ? "Department Found" : "No Department found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);

		return new ResponseEntity<>(response.getResponse(department,
				department != null ? "Department Found" : "No Department found", HttpStatus.OK), HttpStatus.OK);
	}

	/*
	 * ======================== Delete Department ========================
	 */

	@ApiOperation(value = "Delete Department", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = DEPARTMENT_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In DepartmentController Delete branch of ids  :{} ", idsArrays);

		int status = departmentService.deleteDepartment(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Department Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Department successfully deleted", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Department");

			return new ResponseEntity<>(response.getResponse("", "Invalid Department", HttpStatus.OK), HttpStatus.OK);
		}
	}

	/*
	 * ======================== Update Department Status========================
	 */

	@ApiOperation(value = "Update Department Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = DEPARTMENT_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@PathVariable Long id, @PathVariable Integer status) {
		log.info("REST Request In DepartmentController to update status {} {},", id, status);
		int res = departmentService.updateDepartmentStatus(id, status);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", id, "Department Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Department successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", id, "Invalid Department");

			return new ResponseEntity<>(response.getResponse("", "Invalid Department", HttpStatus.OK), HttpStatus.OK);
		}
	}
}
