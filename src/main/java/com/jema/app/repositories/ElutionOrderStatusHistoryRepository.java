/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 11-Jun-2023
*
*/

package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.ElutionOrderStatusHistory;

@Repository
public interface ElutionOrderStatusHistoryRepository  extends CrudRepository<ElutionOrderStatusHistory, Long> {

	List<ElutionOrderStatusHistory> findByOrderId(String id);
}
