package com.jema.app.entities;

import lombok.Data;

@Data
public class PaginationRequest {
	private int page;
	private int size;
}
