/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.ReturnReason;

public interface ReturnReasonService {

	public Long save(ReturnReason returnReason);

	public List<ReturnReason> findById(String id);
	
}
