package com.jema.app.response;

import java.util.List;

import com.jema.app.entities.ElutionAccountInvoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElutionInvoiceResponse {
	private List<ElutionAccountInvoice> invoices;
	private long totalCount;
}
