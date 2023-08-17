/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 20-May-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.Increment;

public interface IncrementService {

	public Long save(Increment increment);

	public List<Increment> findAll(Long employeeId);
}
