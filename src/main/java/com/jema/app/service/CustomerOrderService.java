/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 03-Jun-2023
*
*/

package com.jema.app.service;

import java.util.Date;
import java.util.List;

import com.jema.app.dto.CustomerOrderListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.CustomerOrder;

public interface CustomerOrderService {

	public String save(CustomerOrder customerOrder);

	public List<CustomerOrderListView> findAll(PageRequestDTO pageRequestDTO);

	public List<CustomerOrderListView> findAllByCustomerID(String customerID, PageRequestDTO pageRequestDTO);

	public int updateStatus(String id, Integer status, String comment);

	public int markOff(String id, String comment, int MarkOff, Date date);

	public int cancel(String id, int cancel);

	public int returned(String id, int returned);

	public CustomerOrder findById(String id);

	public int updateInvoice(String id, Date invoiceDate, String invoiceURL, String invoiceNumber);

	public int updatePrice(String id, Double price, Long quantity, Double total_price, Double total_tax);
}
