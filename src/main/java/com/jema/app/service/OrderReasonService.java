/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 07-Jun-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.OrderReason;

public interface OrderReasonService {

	public Long save(OrderReason orderReason);

	public List<OrderReason> findAll();

	public OrderReason findById(Long id);

	public int delete(List<Long> idsArrays);
}
