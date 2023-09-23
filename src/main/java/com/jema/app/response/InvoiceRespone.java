package com.jema.app.response;

import java.util.List;

import com.jema.app.entities.AccountInvoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRespone {
	private List<AccountInvoice> invoices;
	private long totalCount;
}
