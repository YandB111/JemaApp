/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 24-May-2023
*
*/

package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jema.app.entities.InventorySettingTax;

@Repository
public interface InventorySettingTaxRepository extends JpaRepository<InventorySettingTax, Long> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE inventory_setting_tax  SET status=?1  WHERE id IN ?2 ", nativeQuery = true)
	int updateStatus(@Param("status") int status, @Param("id") List<Long> idsArrays);
}
