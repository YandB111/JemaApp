/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 13-May-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.AccountDetailsView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.AccountDetails;

public interface AccountDetailsService {

	public Long save(AccountDetails accountDetails);

	public AccountDetails findById(Long id);
	
	public int delete(List<Long> idsArrays);
	
	public int updateBalance(Long id, Long balance);
	
	public List<AccountDetailsView> findAll(PageRequestDTO pageRequestDTO);
	
}
