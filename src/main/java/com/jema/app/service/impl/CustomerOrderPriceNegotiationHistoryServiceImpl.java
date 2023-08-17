/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PriceHistoryListView;
import com.jema.app.entities.CustomerOrderPriceNegotiationHistory;
import com.jema.app.repositories.CustomerOrderPriceNegotiationHistoryRepository;
import com.jema.app.service.CustomerOrderPriceNegotiationHistoryService;
import com.jema.app.utils.AppUtils;

@Service
public class CustomerOrderPriceNegotiationHistoryServiceImpl implements CustomerOrderPriceNegotiationHistoryService {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Autowired
	CustomerOrderPriceNegotiationHistoryRepository mCustomerOrderPriceNegotiationHistoryRepository;

	@Override
	public Long save(CustomerOrderPriceNegotiationHistory mCustomerOrderPriceNegotiationHistory) {
		// TODO Auto-generated method stub
		return mCustomerOrderPriceNegotiationHistoryRepository.save(mCustomerOrderPriceNegotiationHistory).getId();
	}

	@Override
	public List<PriceHistoryListView> findAll(String id, PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, i.price as price, i.total_price as total_price, i.createtime as createtime, "
				+ "i.comment as comment, c.id as chemical_id, c.name as chemical_name, " + "c.code as chemical_code "
				+ "from customer_order_price_negotiation_history i " + "left join chemical c on c.id = i.chemical_id ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where i.customer_order_id='" + id + "' and c.code ilike '%"
					+ pageRequestDTO.getKeyword().trim() + "%'";
		} else {
			baseBuery = baseBuery + "  where i.customer_order_id='" + id + "'";
		}

		baseBuery = baseBuery
				+ " group by i.id, i.price, i.total_price, i.createtime, i.comment, c.code, c.id, c.name order by i.id DESC";
		// create a query to retrieve MyEntity objects
		Query query = null;
		try {
			query = entityManager.createNativeQuery(baseBuery, Tuple.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// set the maximum number of results to retrieve (i.e., the page size)
		query.setMaxResults(pageRequestDTO.getPageSize());

		// set the index of the first result to retrieve (i.e., the offset)
		query.setFirstResult(pageRequestDTO.getPageNumber() * pageRequestDTO.getPageSize());

		// execute the query and obtain the list of entities for the requested page
		List<Tuple> tuples = query.getResultList();

		List<PriceHistoryListView> dataList = AppUtils.parseTuple(tuples, PriceHistoryListView.class, gson);

		return dataList;

	}

}
