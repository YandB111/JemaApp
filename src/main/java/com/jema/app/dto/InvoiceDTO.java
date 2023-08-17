/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 20-Jun-2023
*
*/

package com.jema.app.dto;

import java.util.Date;

import lombok.Data;

@Data
public class InvoiceDTO {

	String id;
	String invoiceNumber;
	String invoiceURL;
	Date invoiceDate;
}
