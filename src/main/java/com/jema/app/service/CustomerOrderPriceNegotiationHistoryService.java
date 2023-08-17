/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PriceHistoryListView;
import com.jema.app.entities.CustomerOrderPriceNegotiationHistory;

public interface CustomerOrderPriceNegotiationHistoryService {

	public Long save(CustomerOrderPriceNegotiationHistory mCustomerOrderPriceNegotiationHistory);

	public List<PriceHistoryListView> findAll(String id, PageRequestDTO pageRequestDTO);

}
