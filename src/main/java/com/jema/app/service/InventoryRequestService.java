/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 03-Jun-2023
*
*/

package com.jema.app.service;

import java.util.Date;
import java.util.List;

import com.jema.app.dto.InventoryRequestDTO;
import com.jema.app.dto.InventoryRequestListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.InventoryRequest;

public interface InventoryRequestService {

	public String save(InventoryRequest inventoryRequest);

	public List<InventoryRequestListView> findAll(PageRequestDTO pageRequestDTO);

	public List<InventoryRequestListView> findAllByVendorID(Long vendorID, PageRequestDTO pageRequestDTO);

	public int updateStatus(String id, Integer status, String comment);

	public int updateInvoice(String id, Date invoiceDate, String invoiceURL, String invoiceNumber);

	public int markOff(String id, String comment, int MarkOff, Date date);

	public int cancel(String id, int cancel);

	public int returned(String id, int returned);

	public InventoryRequest findById(String id);

	public int updatePrice(String id, Double price, Long quantity, Double total_price, Double total_tax);

	

}
