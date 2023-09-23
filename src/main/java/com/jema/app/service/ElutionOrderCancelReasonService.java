/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.ElutionOrderCancelReason;

public interface ElutionOrderCancelReasonService {

	public Long save(ElutionOrderCancelReason mElutionOrderCancelReason);

	public List<ElutionOrderCancelReason> findById(String id);
	
}
