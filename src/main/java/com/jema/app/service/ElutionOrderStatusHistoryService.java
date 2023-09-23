/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 11-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.ElutionOrderStatusHistory;

public interface ElutionOrderStatusHistoryService {

	public Long save(ElutionOrderStatusHistory mElutionOrderStatusHistory);

	public List<ElutionOrderStatusHistory> findById(String mElutionOrderId);
}
