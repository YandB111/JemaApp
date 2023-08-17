/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.CancelReason;

public interface CancelReasonService {

	public Long save(CancelReason cancelReason);

	public List<CancelReason> findById(String id);
	
}
