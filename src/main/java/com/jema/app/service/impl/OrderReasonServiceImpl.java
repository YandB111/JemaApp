/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 07-Jun-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jema.app.entities.OrderReason;
import com.jema.app.repositories.OrderReasonRepository;
import com.jema.app.service.OrderReasonService;

@Service
public class OrderReasonServiceImpl implements OrderReasonService{

	@Autowired
	OrderReasonRepository orderReasonRepository;
	
	@Override
	public Long save(OrderReason orderReason) {
		// TODO Auto-generated method stub
		return orderReasonRepository.save(orderReason).getId();
	}

	@Override
	public List<OrderReason> findAll() {
		// TODO Auto-generated method stub
		Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.DESC, "id").ignoreCase());
		return orderReasonRepository.findAll(sortBy);
	}

	@Override
	public OrderReason findById(Long id) {
		// TODO Auto-generated method stub
		Optional<OrderReason> orderReason = orderReasonRepository.findById(id);
		if (orderReason.isPresent()) {
			return orderReason.get();
		}
		return null;
	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		orderReasonRepository.deleteAllById(idsArrays);
		return 1;
	}

}
