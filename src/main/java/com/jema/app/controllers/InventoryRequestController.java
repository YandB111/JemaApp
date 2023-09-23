/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 03-Jun-2023
*
*/

package com.jema.app.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.dto.InventoryPriceNegotiationHistoryDTO;
import com.jema.app.dto.InventoryRequestDTO;
import com.jema.app.dto.InventoryRequestListView;
import com.jema.app.dto.InvoiceDTO;
import com.jema.app.dto.MarkOffDTO;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.dto.PriceHistoryListView;
import com.jema.app.entities.CancelReason;
import com.jema.app.entities.InventoryPriceNegotiationHistory;
import com.jema.app.entities.InventoryRequest;
import com.jema.app.entities.InventoryRequestItemTax;
import com.jema.app.entities.InventoryStatusHistory;
import com.jema.app.entities.ReturnReason;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.CancelReasonService;
import com.jema.app.service.InventoryPriceNegotiationHistoryService;
import com.jema.app.service.InventoryRequestService;
import com.jema.app.service.InventoryStatusHistoryService;
import com.jema.app.service.ReturnReasonService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Inventory Request Controller")
@RestController
public class InventoryRequestController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(InventoryRequestController.class);

	@Autowired
	InventoryRequestService inventoryRequestService;

	@Autowired
	InventoryStatusHistoryService inventoryStatusHistoryService;

	@Autowired
	CancelReasonService cancelReasonService;

	@Autowired
	ReturnReasonService mReturnReasonService;

	@Autowired
	InventoryPriceNegotiationHistoryService mInventoryPriceNegotiationHistoryService;

	/*
	 * ======================== InventoryRequest ADD ========================
	 */
	@CrossOrigin
	@PostMapping(value = INVENTORY_REQUEST_ADD, produces = "application/json")
	public ResponseEntity<?> addEmployee(@Valid @RequestBody InventoryRequestDTO inventoryRequestDTO) {
		logger.info("Request:In InventoryRequest Controller for Add InventoryRequest :{} ", inventoryRequestDTO);
		GenericResponse genericResponse = new GenericResponse();

		for (int i = 0; i < inventoryRequestDTO.getItem().size(); i++) {
			InventoryRequest inventoryRequest = new InventoryRequest();
			inventoryRequest.setTax(new ArrayList<>());
			BeanUtils.copyProperties(inventoryRequestDTO.getItem().get(i), inventoryRequest);
			List<InventoryRequestItemTax> mList = new ArrayList<>();
			for (int k = 0; k < inventoryRequestDTO.getItem().get(i).getTax().size(); k++) {
				InventoryRequestItemTax obj = new InventoryRequestItemTax();
				obj.setId(null);
				obj.setTax(inventoryRequestDTO.getItem().get(i).getTax().get(k).getTax());
				mList.add(obj);
			}
			inventoryRequest.setTax(mList);
			inventoryRequest.setCreateTime(new Date());
			inventoryRequest.setUpdateTime(new Date());

			String id = inventoryRequestService.save(inventoryRequest);
			inventoryRequestDTO.setId(id);
		}

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(inventoryRequestDTO, "InventoryRequest successfully added", HttpStatus.OK),
				HttpStatus.OK);

	}

	/*
	 * ======================== Get All Inventory Request ===========
	 */

	@ApiOperation(value = "Get All Inventory Request with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = INVENTORY_REQUEST_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllVendors(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Vendor {} ", pageRequestDTO);

		Long recordsCount = 0l;

		List<InventoryRequestListView> dataList = inventoryRequestService.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);

		return onSuccess(obj, Constants.INVENTORY_FETCHED);
	}

	/*
	 * ================== Update Inventory Request Status================
	 */

	@ApiOperation(value = "Update Inventory Request Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = INVENTORY_REQUEST_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@PathVariable String id,
			@RequestBody InventoryStatusHistory inventoryStatusHistory) {
		log.info("REST Request In Inventory Request Controller to update status {} {},", id, inventoryStatusHistory);
		inventoryStatusHistory.setCreateTime(new Date());
		Long mRes = inventoryStatusHistoryService.save(inventoryStatusHistory);
		int res = inventoryRequestService.updateStatus(id, inventoryStatusHistory.getStatus(),
				inventoryStatusHistory.getComment());
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", id, "Inventory Request Successfully updated");

			return new ResponseEntity<>(
					response.getResponse("", "Inventory Request successfully updated", HttpStatus.OK), HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", id, "Invalid Inventory Request");

			return new ResponseEntity<>(response.getResponse("", "Invalid Inventory Request", HttpStatus.OK),
					HttpStatus.OK);
		}
	}

	/*
	 * ================== Update Inventory Request Invoice================
	 */

	@ApiOperation(value = "Update Inventory Request Invoice", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = INVENTORY_REQUEST_INVOICE, produces = "application/json")
	public ResponseEntity<GenericResponse> updateInvoice(@RequestBody InvoiceDTO mInvoiceDTO) {
		log.info("REST Request In Inventory Request Controller to update status {} {},", mInvoiceDTO.getId(),
				mInvoiceDTO);
		int res = inventoryRequestService.updateInvoice(mInvoiceDTO.getId(), mInvoiceDTO.getInvoiceDate(),
				mInvoiceDTO.getInvoiceURL(), mInvoiceDTO.getInvoiceNumber());
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", mInvoiceDTO.getId(),
					"Inventory Request Successfully updated");

			return new ResponseEntity<>(
					response.getResponse("", "Inventory Request successfully updated", HttpStatus.OK), HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", mInvoiceDTO.getId(), "Invalid Inventory Request");

			return new ResponseEntity<>(response.getResponse("", "Invalid Inventory Request", HttpStatus.OK),
					HttpStatus.OK);
		}
	}

	/*
	 * ======================== Find One Inventory Request ========================
	 */

	@ApiOperation(value = "Find Inventory Request By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = INVENTORY_REQUEST_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") String id) {
		logger.info("Request: In Inventory Request Controller Find Inventory Request By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		InventoryRequest inventoryRequest = inventoryRequestService.findById(id);
		String msg = inventoryRequest != null ? "Inventory Request Found" : "No Inventory Request found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);

		return new ResponseEntity<>(response.getResponse(inventoryRequest,
				inventoryRequest != null ? "Inventory Request Found" : "No Inventory Request found", HttpStatus.OK),
				HttpStatus.OK);
	}

	/*
	 * ======================== Mark Off Inventory Request ==============
	 */

	@ApiOperation(value = "Mark Off Inventory Request", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = INVENTORY_REQUEST_MARK_OFF, produces = "application/json")
	public ResponseEntity<GenericResponse> markOff(@Valid @RequestBody MarkOffDTO markOffDTO) {
		logger.info("Rest request to mark off inventory request {} ", markOffDTO);

		int res = inventoryRequestService.markOff(markOffDTO.getId(), markOffDTO.getComment(), markOffDTO.getMarkOff(),
				new Date());
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", markOffDTO.getId(),
					"Inventory Request Successfully mark off");

			return new ResponseEntity<>(
					response.getResponse("", "Inventory Request successfully mark off", HttpStatus.OK), HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", markOffDTO.getId(), "Invalid Inventory Request");

			return new ResponseEntity<>(response.getResponse("", "Invalid Inventory Request", HttpStatus.OK),
					HttpStatus.OK);
		}
	}

	/*
	 * ======================== Cancel Inventory Request ==============
	 */

	@ApiOperation(value = "Cancel Inventory Request", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = INVENTORY_REQUEST_CANCEL, produces = "application/json")
	public ResponseEntity<GenericResponse> cancel(@Valid @RequestBody CancelReason cancelReason) {
		logger.info("Rest request to cancel inventory request {} ", cancelReason);

		int res = inventoryRequestService.cancel(cancelReason.getInventoryRequestId(), 1);
		cancelReason.setCreateTime(new Date());
		cancelReasonService.save(cancelReason);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", cancelReason.getInventoryRequestId(),
					"Inventory Request Successfully cancel");

			return new ResponseEntity<>(
					response.getResponse("", "Inventory Request successfully cancel", HttpStatus.OK), HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", cancelReason.getInventoryRequestId(),
					"Invalid Inventory Request");

			return new ResponseEntity<>(response.getResponse("", "Invalid Inventory Request", HttpStatus.OK),
					HttpStatus.OK);
		}
	}

	/*
	 * ======================== Return Inventory Request ==============
	 */

	@ApiOperation(value = "Return Inventory Request", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = INVENTORY_REQUEST_RETURN, produces = "application/json")
	public ResponseEntity<GenericResponse> returned(@Valid @RequestBody ReturnReason mReturnReason) {
		logger.info("Rest request to Return inventory request {} ", mReturnReason);

		int res = inventoryRequestService.returned(mReturnReason.getInventoryRequestId(), 1);
		mReturnReason.setCreateTime(new Date());
		mReturnReasonService.save(mReturnReason);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", mReturnReason.getInventoryRequestId(),
					"Inventory Request Successfully Return");

			return new ResponseEntity<>(
					response.getResponse("", "Inventory Request successfully Return", HttpStatus.OK), HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", mReturnReason.getInventoryRequestId(),
					"Invalid Inventory Request");

			return new ResponseEntity<>(response.getResponse("", "Invalid Inventory Request", HttpStatus.OK),
					HttpStatus.OK);
		}
	}

	/*
	 * ========== Find One Canceled Inventory Request ================
	 */

	@ApiOperation(value = "Find Canceled Inventory Request By Inventory Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = INVENTORY_REQUEST_CANCELED_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findCanceledById(@PathVariable(name = "id") String id) {
		logger.info("Request: In Inventory Request Controller Find Canceled Inventory By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		List<CancelReason> mOrderReason = cancelReasonService.findById(id);
		String msg = mOrderReason != null ? "Inventory Found" : "No Inventory found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		return new ResponseEntity<>(response.getResponse(mOrderReason,
				mOrderReason != null ? "Inventory Found" : "No Inventory found", HttpStatus.OK), HttpStatus.OK);
	}

	/*
	 * ========== Find One Returned Inventory Request ================
	 */

	@ApiOperation(value = "Find Returned Inventory Request By Inventory Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = INVENTORY_REQUEST_RETURNED_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findReturnedById(@PathVariable(name = "id") String id) {
		logger.info("Request: In Inventory Request Controller Find Returned Inventory By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		List<ReturnReason> mReturnReason = mReturnReasonService.findById(id);
		String msg = mReturnReason != null ? "Inventory Found" : "No Inventory found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		return new ResponseEntity<>(response.getResponse(mReturnReason,
				mReturnReason != null ? "Inventory Found" : "No Inventory found", HttpStatus.OK), HttpStatus.OK);
	}

	/*
	 * ======================== Update Price =================
	 */
	@ApiOperation(value = "Update Price", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = INVENTORY_REQUEST_PRICE_HISTORY, produces = "application/json")
	public ResponseEntity<?> updatePrice(
			@Valid @RequestBody InventoryPriceNegotiationHistoryDTO inventoryPriceNegotiationHistoryDTO) {
		logger.info("Request:In Inventory Controller for Update Price :{} ", inventoryPriceNegotiationHistoryDTO);
		GenericResponse genericResponse = new GenericResponse();

		InventoryPriceNegotiationHistory mInventoryPriceNegotiationHistory = new InventoryPriceNegotiationHistory();
		BeanUtils.copyProperties(inventoryPriceNegotiationHistoryDTO, mInventoryPriceNegotiationHistory);
		mInventoryPriceNegotiationHistory.setCreateTime(new Date());
		Long id = mInventoryPriceNegotiationHistoryService.save(mInventoryPriceNegotiationHistory);

		/*
		 * Update Inventory Request Price and details
		 */
		if (id > 0) {
			inventoryRequestService.updatePrice(inventoryPriceNegotiationHistoryDTO.getInventoryRequestId(),
					inventoryPriceNegotiationHistoryDTO.getPrice(), inventoryPriceNegotiationHistoryDTO.getQuantity(),
					inventoryPriceNegotiationHistoryDTO.getTotalPrice(),
					inventoryPriceNegotiationHistoryDTO.getTotalTax());
		}
		inventoryPriceNegotiationHistoryDTO.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(inventoryPriceNegotiationHistoryDTO, "Successfully Updated", HttpStatus.OK),
				HttpStatus.OK);

	}

	/*
	 * ======================== Get All Inventory Price History ===========
	 */

	@ApiOperation(value = "Get All Inventory Price History with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = INVENTORY_REQUEST_PRICE_HISTORY_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllPriceHistory(@PathVariable(name = "id") String id,
			@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Price history {} ", pageRequestDTO);

		Long recordsCount = 0l;

		List<PriceHistoryListView> dataList = mInventoryPriceNegotiationHistoryService.findAll(id, pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);

		return onSuccess(obj, Constants.PRICE_HISTORY_FETCHED);
	}

	/*
	 * ========== Find All Inventory Status Change History ================
	 */

	@ApiOperation(value = "Find All Inventory Status Change History By Inventory Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = INVENTORY_REQUEST_STATUS_HISTORY, produces = "application/json")
	public ResponseEntity<GenericResponse> findStatusHistoryById(@PathVariable(name = "id") String id) {
		logger.info(
				"Request: In Inventory Request Controller Find All Inventory Status Change History By Inventory Id :{} ",
				id);

		GenericResponse response = new GenericResponse();
		List<InventoryStatusHistory> mInventoryStatusHistory = inventoryStatusHistoryService.findById(id);
		String msg = mInventoryStatusHistory != null ? "Inventory History Found" : "No Inventory History found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		return new ResponseEntity<>(response.getResponse(mInventoryStatusHistory,
				mInventoryStatusHistory != null ? "Inventory History Found" : "No Inventory History found",
				HttpStatus.OK), HttpStatus.OK);
	}

	/*
	 * ======================== Find All Inventory Request by Vendor ID============
	 */

	@ApiOperation(value = "Find All Inventory Request by Vendor ID", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = INVENTORY_REQUEST_FIND_BY_VENDOR, produces = "application/json")
	public ResponseEntity<GenericResponse> findByVendorId(@PathVariable(name = "id") Long id,
			@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Request: In Inventory Request Controller Find Inventory Request By Vendor Id :{} ", id);
		Long recordsCount = 0l;
		List<InventoryRequestListView> dataList = inventoryRequestService.findAllByVendorID(id, pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);

		return onSuccess(obj, Constants.INVENTORY_FETCHED);
	}




}
