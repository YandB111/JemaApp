package com.jema.app.response;

import java.util.List;

import com.jema.app.dto.CustomerOrderListView;
import com.jema.app.entities.CustomerOrder;
import com.jema.app.entities.Employee;
import com.jema.app.entities.FinanceAddRecordRequest;
import com.jema.app.entities.InventoryRequest;

import lombok.Data;

@Data
public class AggregatedDataResponse {

	private List<Employee> employeeData;
	private List<CustomerOrder> customerOrderData;
	private List<FinanceAddRecordRequest> financeData;
	private List<InventoryRequest> inventoryRequestData;

	// Getters and setters
}
