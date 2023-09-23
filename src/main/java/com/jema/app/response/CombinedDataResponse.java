package com.jema.app.response;

import java.util.List;

import com.jema.app.dto.CustomerOrderDTO;
import com.jema.app.dto.InventoryRequestDTO;
import com.jema.app.entities.Employee;
import com.jema.app.entities.FinanceAddRecordRequest;

import lombok.Data;
@Data
public class CombinedDataResponse {
    private List<Employee> employees;
    private List<FinanceAddRecordRequest> financeRecords;
    private List<InventoryRequestDTO> inventoryRequests;
    private List<CustomerOrderDTO> customerOrders;

    private String verification;

    public CombinedDataResponse(
            List<Employee> employees,
            List<FinanceAddRecordRequest> financeRecords,
            List<InventoryRequestDTO> inventoryRequests,
            List<CustomerOrderDTO> customerOrders) {
        this.employees = employees;
        this.financeRecords = financeRecords;
        this.inventoryRequests = inventoryRequests;
        this.customerOrders = customerOrders;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<FinanceAddRecordRequest> getFinanceRecords() {
        return financeRecords;
    }

    public void setFinanceRecords(List<FinanceAddRecordRequest> financeRecords) {
        this.financeRecords = financeRecords;
    }

    public List<InventoryRequestDTO> getInventoryRequests() {
        return inventoryRequests;
    }

    public void setInventoryRequests(List<InventoryRequestDTO> inventoryRequests) {
        this.inventoryRequests = inventoryRequests;
    }

    public List<CustomerOrderDTO> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<CustomerOrderDTO> customerOrders) {
        this.customerOrders = customerOrders;
    }
    
    
}
