/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 19-Aug-2023
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.dto.ElutionOrderDTO;
import com.jema.app.dto.ElutionOrderListView;
import com.jema.app.dto.InvoiceDTO;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.CustomerOrderCancelReason;
import com.jema.app.entities.ElutionOrder;
import com.jema.app.entities.ElutionOrderCancelReason;
import com.jema.app.entities.ElutionOrderStatusHistory;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.ElutionOrderCancelReasonService;
import com.jema.app.service.ElutionOrderService;
import com.jema.app.service.ElutionOrderStatusHistoryService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Elution Order Controller")
@RestController
public class ElutionOrderController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(ElutionOrderController.class);

	@Autowired
	ElutionOrderService mElutionOrderService;

	@Autowired
	ElutionOrderStatusHistoryService mElutionOrderStatusHistoryService;
	
	@Autowired
	ElutionOrderCancelReasonService mElutionOrderCancelReasonService;

	/*
	 * ======================== Elution Order ADD ==================================
	 */
	@ApiOperation(value = "Add Order", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_ORDER_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody ElutionOrderDTO mElutionOrderDTO) {
		logger.info("Request:In Elution Order Controller for Add Order :{} ", mElutionOrderDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionOrder mElutionOrder = new ElutionOrder();
		BeanUtils.copyProperties(mElutionOrderDTO, mElutionOrder);
		mElutionOrder.setCreateTime(new Date());
		mElutionOrder.setUpdateTime(new Date());
		String id = mElutionOrderService.save(mElutionOrder);
		mElutionOrderDTO.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(mElutionOrderDTO, "Order successfully added", HttpStatus.OK),
				HttpStatus.OK);

	}

	/*
	 * ======================== Find One Elution Order ========================
	 */

	@ApiOperation(value = "Find Elution Order By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = ELUTION_ORDER_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") String id) {
		logger.info("Request: In Elution Order Controller Find Elution Order By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		ElutionOrder mElutionOrder = mElutionOrderService.findById(id);
		String msg = mElutionOrder != null ? "Order Found" : "No Order found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);

		return new ResponseEntity<>(response.getResponse(mElutionOrder,
				mElutionOrder != null ? "Order Found" : "No Order found", HttpStatus.OK), HttpStatus.OK);
	}

	/*
	 * ======================== Get All Elution Order ===========
	 */

	@ApiOperation(value = "Get All Elution Order with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_ORDER_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllCustomerOrder(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Elution Order {} ", pageRequestDTO);

		Long recordsCount = 0l;

		List<ElutionOrderListView> dataList = mElutionOrderService.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);

		return onSuccess(obj, Constants.ELUTION_ORDER_FETCHED);
	}

	/*
	 * ======================== Cancel Elution Order ==============
	 */

	@ApiOperation(value = "Cancel ElutionOrder", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_ORDER_CANCEL, produces = "application/json")
	public ResponseEntity<GenericResponse> cancel(
			@Valid @RequestBody ElutionOrderCancelReason mElutionOrderCancelReason) {
		logger.info("Rest request to cancel ElutionOrder {} ", mElutionOrderCancelReason);

		int res = mElutionOrderService.cancel(mElutionOrderCancelReason.getElutionOrderId(), 1);
		mElutionOrderCancelReason.setCreateTime(new Date());
		mElutionOrderCancelReasonService.save(mElutionOrderCancelReason);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", mElutionOrderCancelReason.getElutionOrderId(),
					"Elution Order Successfully cancel");

			return new ResponseEntity<>(response.getResponse("", "Elution Order successfully cancel", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", mElutionOrderCancelReason.getElutionOrderId(),
					"Invalid Elution Order");

			return new ResponseEntity<>(response.getResponse("", "Invalid Customer Order", HttpStatus.OK),
					HttpStatus.OK);
		}
	}
	
	/*
	 * ========== Find One Canceled ElutionOrder ================
	 */

	@ApiOperation(value = "Find Canceled Elution Order By Elution Order Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = ELUTION_ORDER_CANCELED_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findCanceledById(@PathVariable(name = "id") String id) {
		logger.info("Request: In ElutionOrder Controller Find Canceled ElutionOrder By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		List<ElutionOrderCancelReason> mOrderReason = mElutionOrderCancelReasonService.findById(id);
		String msg = mOrderReason != null ? "Elution Order Found" : "No Elution Order found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		return new ResponseEntity<>(
				response.getResponse(mOrderReason,
						mOrderReason != null ? "Elution Order Found" : "No Elution Order found", HttpStatus.OK),
				HttpStatus.OK);
	}


	/*
	 * ================== Update ElutionOrder Invoice================
	 */

	@ApiOperation(value = "Update Elution Order Invoice", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_ORDER_INVOICE, produces = "application/json")
	public ResponseEntity<GenericResponse> updateInvoice(@RequestBody InvoiceDTO mInvoiceDTO) {
		log.info("REST Request In Elution Order Controller to update invoice {} {},", mInvoiceDTO.getId(), mInvoiceDTO);
		int res = mElutionOrderService.updateInvoice(mInvoiceDTO.getId(), mInvoiceDTO.getInvoiceDate(),
				mInvoiceDTO.getInvoiceURL(), mInvoiceDTO.getInvoiceNumber());
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", mInvoiceDTO.getId(), "Elution Order Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Elution Order successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", mInvoiceDTO.getId(), "Invalid Elution Order");

			return new ResponseEntity<>(response.getResponse("", "Invalid Elution Order", HttpStatus.OK),
					HttpStatus.OK);
		}
	}

	/*
	 * ======================== Find All ElutionOrder by Customer ID============
	 */

	@ApiOperation(value = "Find All Elution Order by Customer ID", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_ORDER_FIND_BY_CUSTOMER, produces = "application/json")
	public ResponseEntity<GenericResponse> findByCustomerId(@PathVariable(name = "id") String id,
			@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Request: In Elution Order Controller Find Elution Order By Customer Id :{} ", id);
		Long recordsCount = 0l;
		List<ElutionOrderListView> dataList = mElutionOrderService.findAllByCustomerID(id, pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);

		return onSuccess(obj, Constants.ELUTION_ORDER_FETCHED);
	}

	/*
	 * ================== Update ElutionOrder Status================
	 */

	@ApiOperation(value = "Update ElutionOrder Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = ELUTION_ORDER_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@PathVariable String id,
			@RequestBody ElutionOrderStatusHistory mElutionOrderStatusHistory) {
		log.info("REST Request In ElutionOrder Controller to update status {} {},", id, mElutionOrderStatusHistory);
		mElutionOrderStatusHistory.setCreateTime(new Date());
		Long mRes = mElutionOrderStatusHistoryService.save(mElutionOrderStatusHistory);
		int res = mElutionOrderService.updateStatus(id, mElutionOrderStatusHistory.getStatus());
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", id, "Order Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Order successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", id, "Invalid Order");

			return new ResponseEntity<>(response.getResponse("", "Invalid Order", HttpStatus.OK), HttpStatus.OK);
		}
	}

	/*
	 * ========== Find All ElutionOrder Status Change History ================
	 */

	@ApiOperation(value = "Find All ElutionOrder Status Change History By Elution Order Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = ELUTION_ORDER_STATUS_HISTORY, produces = "application/json")
	public ResponseEntity<GenericResponse> findStatusHistoryById(@PathVariable(name = "id") String id) {
		logger.info(
				"Request: In Elution Order Controller Find All Elution Order Status Change History By ElutionOrder Id :{} ",
				id);

		GenericResponse response = new GenericResponse();
		List<ElutionOrderStatusHistory> mElutionOrderStatusHistory = mElutionOrderStatusHistoryService.findById(id);
		String msg = mElutionOrderStatusHistory != null ? "Elution Order History Found"
				: "No Elution Order History found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		return new ResponseEntity<>(response.getResponse(mElutionOrderStatusHistory,
				mElutionOrderStatusHistory != null ? "Elution Order History Found" : "No Elution Order History found",
				HttpStatus.OK), HttpStatus.OK);
	}

}
