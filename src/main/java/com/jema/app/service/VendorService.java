/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 27-May-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.VendorListView;
import com.jema.app.dto.VendorListViewByChemicalID;
import com.jema.app.entities.Vendor;

public interface VendorService {

	public Long save(Vendor vendor);

	public List<VendorListView> findAll(PageRequestDTO pageRequestDTO);

	public List<VendorListViewByChemicalID> findAllVendorByChemicalId(PageRequestDTO pageRequestDTO, Long id);

	public Vendor findById(Long id);

	public int delete(List<Long> ids);

	public int updateStatus(Long id, Integer status);
	
	public List<Vendor> findAll();

	Long update(Vendor vendor);

	String getVendorNameById(Long vendorId);

}
