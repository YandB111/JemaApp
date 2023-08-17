package com.jema.app.exceptions;

/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 19-Mar-2023
*
*/
public class InvalidTokenException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1784037521378648518L;

	public InvalidTokenException(String message) {
		super(message);
	}
}
