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

import com.jema.app.entities.CancelReason;

@Repository
public interface CancelReasonRepository extends CrudRepository<CancelReason, Long> {

	List<CancelReason> findByInventoryRequestId(String id);
}
