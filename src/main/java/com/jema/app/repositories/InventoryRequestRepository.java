/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 03-Jun-2023
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

import com.jema.app.entities.InventoryRequest;

@Repository
public interface InventoryRequestRepository extends CrudRepository<InventoryRequest, String> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE InventoryRequest SET status = :status, status_comment =:comment  WHERE id IN :id ")
	int updateStatus(@Param("id") String id, @Param("status") int status, @Param("comment") String comment);

	@Transactional
	@Modifying
	@Query(value = "UPDATE InventoryRequest SET invoice_date = :invoice_date, invoice_url =:invoice_url, invoice_number =:invoice_number  WHERE id IN :id ")
	int updateInvoice(@Param("id") String id, @Param("invoice_date") Date invoiceDate,
			@Param("invoice_url") String invoiceURL, @Param("invoice_number") String invoiceNumber);

	@Transactional
	@Modifying
	@Query(value = "UPDATE InventoryRequest SET comment = :comment , mark_off = :mark_off , mark_off_date= :mark_off_date WHERE id IN :id ")
	int markOff(@Param("id") String id, @Param("comment") String comment, @Param("mark_off") int markOff,@Param("mark_off_date") Date date);

	@Transactional
	@Modifying
	@Query(value = "UPDATE InventoryRequest SET price = :price , quantity = :quantity , total_price = :total_price , total_tax = :total_tax  WHERE id IN :id ")
	int updatePrice(@Param("id") String id, @Param("price") Double price, @Param("quantity") Long quantity,
			@Param("total_price") Double total_price, @Param("total_tax") Double total_tax);

	@Transactional
	@Modifying
	@Query(value = "UPDATE InventoryRequest SET cancel = :cancel  WHERE id IN :id ")
	int cancel(@Param("id") String id, @Param("cancel") int cancel);

	@Transactional
	@Modifying
	@Query(value = "UPDATE InventoryRequest SET returned = :returned  WHERE id IN :id ")
	int returned(@Param("id") String id, @Param("returned") int returned);

}
