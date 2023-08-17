/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 21-May-2023
*
*/

package com.jema.app.controllers;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.dto.InventorySettingQuantityLimitDTO;
import com.jema.app.dto.InventorySettingQuantityLimitListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.InventorySettingQuantityLimit;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.InventorySettingQuantityLimitService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Inventory Setting Quantity Limit Controller")
@RestController
public class InventorySettingQuantityLimitController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(InventorySettingQuantityLimitController.class);

	@Autowired
	InventorySettingQuantityLimitService inventorySettingQuantityLimitService;

	/*
	 * ======================== Quantity Limit ADD =================
	 */
	@ApiOperation(value = "Quantity Limit ADD", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Added"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = INVENTORY_SETTING_QUANTITY_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody InventorySettingQuantityLimitDTO inventorySettingDTO) {
		logger.info("Request:In InventorySetting Controller for Add :{} ", inventorySettingDTO);
		GenericResponse genericResponse = new GenericResponse();
		InventorySettingQuantityLimit mInventorySetting = inventorySettingQuantityLimitService.findByChemical(inventorySettingDTO.getChemical());
		if (mInventorySetting == null) {
			InventorySettingQuantityLimit inventorySetting = new InventorySettingQuantityLimit();
			BeanUtils.copyProperties(inventorySettingDTO, inventorySetting);
			Long id = inventorySettingQuantityLimitService.save(inventorySetting);
			inventorySettingDTO.setId(id);
		} else {
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(inventorySettingDTO, "This Chemical already exist", HttpStatus.CONFLICT),
					HttpStatus.CONFLICT);
		}
		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(inventorySettingDTO, "Quantity Limit successfully added", HttpStatus.OK),
				HttpStatus.OK);

	}

	/*
	 * ======================== Find One Quantity Limit ========================
	 */

	@ApiOperation(value = "Find Quantity Limit By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = INVENTORY_SETTING_QUANTITY_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In InventorySetting Controller Find Quantity Limit By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		InventorySettingQuantityLimit inventorySetting = inventorySettingQuantityLimitService.findById(id);
		String msg = inventorySetting != null ? "Quantity Limit Found" : "No Quantity Limit found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);

		return new ResponseEntity<>(response.getResponse(inventorySetting,
				inventorySetting != null ? "Quantity Limit Found" : "No Quantity Limit found", HttpStatus.OK),
				HttpStatus.OK);
	}
	
	
	/*
	 * ======================== Delete Quantity Limit ========================
	 */

	@ApiOperation(value = "Delete Quantity Limit", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = INVENTORY_SETTING_QUANTITY_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In InventorySetting Controller Delete Quantity Limit of ids  :{} ", idsArrays);

		int status = inventorySettingQuantityLimitService.delete(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Quantity Limit Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Quantity Limit successfully deleted", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Quantity Limit");

			return new ResponseEntity<>(response.getResponse("", "Invalid Quantity Limit", HttpStatus.OK), HttpStatus.OK);
		}
	}

	
	/*
	 * ======================== Get All Quantity Limit ==================================
	 */

	@ApiOperation(value = "Get All Quantity Limit with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = INVENTORY_SETTING_QUANTITY_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Quantity Limit");		
		Long recordsCount = 0l;

		List<InventorySettingQuantityLimitListView> dataList = inventorySettingQuantityLimitService.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);
		
		return onSuccess(obj, Constants.INVENTORY_SETTING_FETCHED);
	}

}
