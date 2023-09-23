/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.ElutionOrderCancelReason;

@Repository
public interface ElutionOrderCancelReasonRepository extends CrudRepository<ElutionOrderCancelReason, Long> {

	List<ElutionOrderCancelReason> findByElutionOrderId(String id);
}
