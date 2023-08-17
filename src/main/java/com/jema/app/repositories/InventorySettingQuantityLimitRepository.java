/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 21-May-2023
*
*/

package com.jema.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.InventorySettingQuantityLimit;

@Repository
public interface InventorySettingQuantityLimitRepository extends CrudRepository<InventorySettingQuantityLimit, Long> {

	InventorySettingQuantityLimit findByChemical(Long chemical);
}
