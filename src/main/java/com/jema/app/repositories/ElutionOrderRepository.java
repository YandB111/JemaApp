/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 19-Aug-2023
*
*/

package com.jema.app.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jema.app.entities.ElutionOrder;

@Repository
public interface ElutionOrderRepository extends CrudRepository<ElutionOrder, String> {


	@Transactional
	@Modifying
	@Query(value = "UPDATE ElutionOrder SET status = :status WHERE id IN :id ")
	int updateStatus(@Param("id") String id, @Param("status") int status);

	@Transactional
	@Modifying
	@Query(value = "UPDATE ElutionOrder SET cancel = :cancel  WHERE id IN :id ")
	int cancel(@Param("id") String id, @Param("cancel") int cancel);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE ElutionOrder SET invoice_date = :invoice_date, invoice_url =:invoice_url, invoice_number =:invoice_number  WHERE id IN :id ")
	int updateInvoice(@Param("id") String id, @Param("invoice_date") Date invoiceDate, @Param("invoice_url") String invoiceURL, @Param("invoice_number") String invoiceNumber);
}
