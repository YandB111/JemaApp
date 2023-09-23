/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 16-Jul-2023
*
*/

package com.jema.app.service;

import java.util.Date;
import java.util.List;

import com.jema.app.dto.ElutionOrderListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.ElutionOrder;

public interface ElutionOrderService {

	public String save(ElutionOrder elutionOrder);

	public ElutionOrder findById(String id);
	
	public List<ElutionOrderListView> findAll(PageRequestDTO pageRequestDTO);
	
	public List<ElutionOrderListView> findAllByCustomerID(String customerID, PageRequestDTO pageRequestDTO);
	
	public int cancel(String id, int cancel);
	
	public int updateInvoice(String id, Date invoiceDate, String invoiceURL, String invoiceNumber);

	public int updateStatus(String id, Integer status);

	Double calculateTotalAmountReceived();
}
