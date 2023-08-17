/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 27-May-2023
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

import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.dto.VendorDTO;
import com.jema.app.dto.VendorListView;
import com.jema.app.dto.VendorListViewByChemicalID;
import com.jema.app.entities.Vendor;
import com.jema.app.response.DepartmentErrorResponse;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.VendorService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Vendor Controller")
@RestController
public class VendorController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(VendorController.class);

	@Autowired
	VendorService vendorService;

	/*
	 * ======================== Vendor ADD ==================================
	 */
	@ApiOperation(value = "Add Vendor", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = VENDOR_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody VendorDTO vendorDTO) {
		logger.info("Request:In Vendor Controller for Add Vendor :{} ", vendorDTO);
		GenericResponse genericResponse = new GenericResponse();
		try {
			Vendor vendor = new Vendor();
			BeanUtils.copyProperties(vendorDTO, vendor);
			vendor.setCreateTime(new Date());
			vendor.setUpdateTime(new Date());
			Long id = vendorService.save(vendor);
			vendorDTO.setId(id);

			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(vendorDTO, "Vendor successfully added", HttpStatus.OK), HttpStatus.OK);

		} catch (IllegalArgumentException e) {
			// Handle the IllegalArgumentException and send a custom error response
			DepartmentErrorResponse customResponse = new DepartmentErrorResponse();
			customResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			customResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
			customResponse.setMessage(e.getMessage());
			customResponse.setTimestamp(new Date());
			return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
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
	 * ======================== Get All Vendor ==================================
	 */

	@ApiOperation(value = "Get All Vendor with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = VENDOR_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllVendors(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Vendor {} ", pageRequestDTO);

		Long recordsCount = 0l;

		List<VendorListView> dataList = vendorService.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);

		return onSuccess(obj, Constants.VENDOR_FETCHED);
	}

	/*
	 * ======================== Get All Vendor by Chemical ID
	 * ==================================
	 */

	@ApiOperation(value = "Get All Vendor by Chemical ID with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = VENDOR_FIND_ALL_BY_CHEMICAL_ID, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllVendorsByChemicalId(
			@PathVariable(name = "id", required = true) Long id, @Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Vendor {} ", pageRequestDTO);

		Long recordsCount = 0l;

		List<VendorListViewByChemicalID> dataList = vendorService.findAllVendorByChemicalId(pageRequestDTO, id);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);

		return onSuccess(obj, Constants.VENDOR_FETCHED);
	}

	/*
	 * ======================== Vendor Edit/Update ========================
	 */
	@ApiOperation(value = "Update Vendor", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = VENDOR_UPDATE, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody VendorDTO vendorDTO) {
		logger.info("Request:In Vendor Controller for Update Vendor :{} ", vendorDTO);
		GenericResponse genericResponse = new GenericResponse();

		Vendor mVendor = vendorService.findById(id);

		if (mVendor != null) {

			Vendor vendor = new Vendor();
			BeanUtils.copyProperties(vendorDTO, vendor);
			vendor.setId(id);
			vendor.setCreateTime(mVendor.getCreateTime());
			vendor.setUpdateTime(new Date());
			vendor.setStatus(mVendor.getStatus());
			vendor.setDeleted(mVendor.getDeleted());

			Long mID = vendorService.save(vendor);
			vendorDTO.setId(mID);
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(vendorDTO, "Vendor successfully Updated", HttpStatus.OK),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Vendor", HttpStatus.OK),
					HttpStatus.OK);
		}

	}

	/*
	 * ======================== Find One Vendor ========================
	 */

	@ApiOperation(value = "Find Vendor By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = VENDOR_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In Vendor Controller Find Vendor By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		Vendor Vendor = vendorService.findById(id);
		String msg = Vendor != null ? "Vendor Found" : "No Vendor found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);

		return new ResponseEntity<>(
				response.getResponse(Vendor, Vendor != null ? "Vendor Found" : "No Vendor found", HttpStatus.OK),
				HttpStatus.OK);
	}

	/*
	 * ======================== Update Vendor Status========================
	 */

	@ApiOperation(value = "Update Vendor Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = VENDOR_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@PathVariable Long id, @PathVariable Integer status) {
		log.info("REST Request In Vendor Controller to update status {} {},", id, status);
		int res = vendorService.updateStatus(id, status);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", id, "Vendor Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Vendor successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", id, "Invalid Vendor");

			return new ResponseEntity<>(response.getResponse("", "Invalid Vendor", HttpStatus.OK), HttpStatus.OK);
		}
	}

	/*
	 * ======================== Delete Vendor ========================
	 */

	@ApiOperation(value = "Delete Vendor", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = VENDOR_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In Vendor Controller Delete Vendor of ids  :{} ", idsArrays);

		int status = vendorService.delete(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Vendor Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Vendor successfully deleted", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Vendor");

			return new ResponseEntity<>(response.getResponse("", "Invalid Vendor", HttpStatus.OK), HttpStatus.OK);
		}
	}

}
