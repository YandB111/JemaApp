/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 26-May-2023
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

import com.jema.app.entities.ElutionCustomerSettingLeadStatus;

@Repository
public interface ElutionCustomerSettingLeadStatusRepository extends JpaRepository<ElutionCustomerSettingLeadStatus, Long> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE elution_customer_setting_lead_status  SET status=?1  WHERE id IN ?2 ", nativeQuery = true)
	int updateStatus(@Param("status") int status, @Param("id") List<Long> idsArrays);


	boolean existsByNameIgnoreCase(String statusName);

}
