package com.jema.app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.entities.AuditAndReconsileVerify;
import com.jema.app.entities.Customer;
import com.jema.app.entities.CustomerOrder;
import com.jema.app.entities.Employee;
import com.jema.app.entities.FinanceAddRecordRequest;
import com.jema.app.entities.InventoryRequest;
import com.jema.app.entities.SalaryDetails;
import com.jema.app.entities.UpdateVerificationRequest;
import com.jema.app.repositories.AuditAndReconsileVerifyRepository;
import com.jema.app.repositories.CustomerRepository;
import com.jema.app.service.AddRecordFinanceService;
import com.jema.app.service.AuditAndReconsileVerifyService;
import com.jema.app.service.CustomerOrderService;
import com.jema.app.service.EmployeeService;
import com.jema.app.service.InventoryRequestService;
import com.jema.app.service.VendorService;

import io.swagger.annotations.Api;

@Api(value = "AuditAndReconsile Controller")
@RestController
public class AuditAndSystemController extends ApiController {
	
	
	@Autowired
	private EmployeeService employeeService;

	
	@Autowired
	private CustomerOrderService customerOrderService;

	
	@Autowired
	private AddRecordFinanceService financeService;

	
	@Autowired
	private InventoryRequestService inventoryRequestService;

	
	@Autowired
	private VendorService vendorService;

	
	@Autowired
	private CustomerRepository customerRepository;

	
	@Autowired
	AuditAndReconsileVerifyService verifyService;

	
	@Autowired
	private AuditAndReconsileVerifyRepository verifyRepository;

	
	@CrossOrigin
	@GetMapping(value = AUDIT_RECONSILE_GETALLDATA, produces = "application/json")
	public ResponseEntity<List<Map<String, Object>>> getAllData() {
		List<InventoryRequest> inventoryRequests = inventoryRequestService.findAllInventoryRequests();
		List<CustomerOrder> customerOrders = customerOrderService.findAllCustomerOrders();
		List<Employee> employees = employeeService.findAllEmployees();
		List<FinanceAddRecordRequest> financeRecords = financeService.findAllFinanceRecords();

		List<Map<String, Object>> selectedDataList = new ArrayList<>();

		for (InventoryRequest request : inventoryRequests) {
			Map<String, Object> selectedData = new HashMap<>();

			selectedData.put("id", request.getId());

			Optional<AuditAndReconsileVerify> existingEntityOptional = verifyRepository.findById(request.getId());
			AuditAndReconsileVerify entity;
 
			if (existingEntityOptional.isPresent()) {
				entity = existingEntityOptional.get();
			} else {
				entity = new AuditAndReconsileVerify();
				entity.setId(request.getId());
				entity.setVerified("No");
			}
			if (!existingEntityOptional.isPresent()) {
				entity.setTotalPrice(request.getTotalPrice());
				entity.setCreateDate(request.getCreateTime());
				entity.setPaymentMode(request.getPaymentMode());
				entity.setVendorName(vendorService.getVendorNameById(request.getVendorId()));
				entity.setRecordsId(request.getId());
				
				verifyRepository.save(entity);
			} else if (entity.getVerified() == null) {
				entity.setVerified("No");
				verifyRepository.save(entity); 
			}

			selectedData.put("price", request.getPrice());
			selectedData.put("totalPrice", entity.getTotalPrice()); 
			selectedData.put("createTime", entity.getCreateDate()); 
			selectedData.put("paymentMode", entity.getPaymentMode()); 
			selectedData.put("vendorId", request.getVendorId());
			selectedData.put("In Id", request.getId());
			String vendorName = vendorService.getVendorNameById(request.getVendorId());
			selectedData.put("vendorName", vendorName);
			selectedDataList.add(selectedData);
		}

		for (CustomerOrder order : customerOrders) {
			Map<String, Object> selectedData = new HashMap<>();

			
			selectedData.put("id", order.getId());
			Optional<AuditAndReconsileVerify> existingEntity = verifyRepository.findById(order.getId());
			AuditAndReconsileVerify entity;

			if (existingEntity.isPresent()) {
				entity = existingEntity.get();
			} else {
				entity = new AuditAndReconsileVerify();
				entity.setId(order.getId());
				entity.setVerified(null);
			}

			String existingVerified = entity.getVerified();
			entity.setTotalPrice(order.getTotalPrice());
			entity.setCreateDate(order.getCreateTime());
			entity.setPaymentMode(order.getPaymentMode());

			
			Customer customer = customerRepository.findCustomerById(order.getCustomerId());

			if (customer != null) {
				entity.setVendorName(customer.getName());
			} else {
				entity.setVendorName("Unknown Vendor"); 
			}

			if (existingVerified == null) {
				entity.setVerified("No");
			}

			verifyRepository.save(entity);
			selectedData.put("totalPrice", order.getTotalPrice());
			selectedData.put("createTime", order.getCreateTime());
			selectedData.put("paymentMode", order.getPaymentMode());
			selectedData.put("CustomerID", order.getCustomerId());

			if (customer != null) {
				selectedData.put("customerName", customer.getName());
			} else {
				selectedData.put("customerName", "Unknown Customer"); 
			}

			selectedDataList.add(selectedData);
		}
		for (Employee employee : employees) {
			Map<String, Object> selectedData = new HashMap<>();
			selectedData.put("id", employee.getId());

			Optional<AuditAndReconsileVerify> existingEntityOptional = verifyRepository
					.findById(employee.getEmployeeId());
			AuditAndReconsileVerify entity;

			if (existingEntityOptional.isPresent()) {
				entity = existingEntityOptional.get();
			} else {
				entity = new AuditAndReconsileVerify();
				entity.setId(employee.getEmployeeId());
				entity.setVerified("No");
				entity.setTypeOfPayment("Salary");
			}
			if (!existingEntityOptional.isPresent()) {
				entity.setCreateDate(employee.getCreateTime());
				entity.setVendorName(employee.getName());

				// Accessing SalaryDetails to get total salary
				SalaryDetails salaryDetails = employee.getSalaryDetails();
				if (salaryDetails != null) {
					entity.setTotalPrice(salaryDetails.getTotalSalary());
				} else {
					entity.setTotalPrice(0.0);
				}
				verifyRepository.save(entity);
			}
			selectedData.put("name", employee.getName());
			selectedData.put("employeeId", employee.getEmployeeId());
			selectedData.put("totalSalary", entity.getTotalPrice());
			selectedData.put("createTime", entity.getCreateDate());

			selectedDataList.add(selectedData);
		}

		for (FinanceAddRecordRequest financeRecord : financeRecords) {
			Map<String, Object> selectedData = new HashMap<>();
			selectedData.put("id", financeRecord.getId());
			Optional<AuditAndReconsileVerify> existingEntityOptional = verifyRepository
					.findById(financeRecord.getEmployeeIds());
			AuditAndReconsileVerify entity;

			if (existingEntityOptional.isPresent()) {
				entity = existingEntityOptional.get();
			} else {
				entity = new AuditAndReconsileVerify();
				entity.setId(financeRecord.getEmployeeIds());
				entity.setVerified("No");
			}
			if (!existingEntityOptional.isPresent()) {
				entity.setTotalPrice(financeRecord.getTotaPrice());
				entity.setForLocalcreateDate(financeRecord.getDueDate());
				entity.setPaymentMode(financeRecord.getPaymentType());
				entity.setVendorName(financeRecord.getEmployeeName());

				verifyRepository.save(entity);
			}

			selectedData.put("paymentType", financeRecord.getPaymentType());
			selectedData.put("employeeIds", financeRecord.getEmployeeIds());
			selectedData.put("employeeName", financeRecord.getEmployeeName());
			selectedData.put("totaPrice", financeRecord.getTotaPrice());
			selectedData.put("dueDate", financeRecord.getDueDate());

			selectedDataList.add(selectedData);
		}

		return ResponseEntity.ok(selectedDataList);

	}

	@CrossOrigin
	@GetMapping(value = AUDIT_RECONSILE_GETALLRecords, produces = "application/json")
	public ResponseEntity<List<AuditAndReconsileVerify>> getAllRecords() {
		List<AuditAndReconsileVerify> records = verifyService.getAllRecords();
		return ResponseEntity.ok(records);
	}

	@CrossOrigin
	@PostMapping(value = AUDIT_RECONSILE_GETVERICATION, produces = "application/json")
	public ResponseEntity<String> updateVerifiedStatus(@RequestBody UpdateVerificationRequest request) {
	    List<String> updatedIds = new ArrayList<>();

	    for (String id : request.getIds()) {
	        // Fetch the entity by ID from the repository
	    	AuditAndReconsileVerify entity = verifyRepository.findById(id).orElse(null);

	        if (entity != null) {
	            entity.setVerified(request.getVerified());
	            verifyRepository.save(entity);
	            updatedIds.add(id);
	        }
	    }

	    if (!updatedIds.isEmpty()) {
	        String response = "Verified status updated to '" + request.getVerified() + "' for IDs: " + String.join(", ", updatedIds);
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	
	@CrossOrigin
	@GetMapping(value = AUDIT_RECONSILE_FILTER_VERIFY, produces = "application/json")
	public ResponseEntity<List<AuditAndReconsileVerify>> getEntitiesByVerified(@RequestParam String verified) {
		List<AuditAndReconsileVerify> entities = verifyRepository.findByVerified(verified);

		if (!entities.isEmpty()) {
			return ResponseEntity.ok(entities);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}