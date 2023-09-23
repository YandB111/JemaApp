package com.jema.app.entities;

import java.util.List;

import lombok.Data;

@Data
public class UpdateVerificationRequest {
	private List<String> ids;
    private String verified;
}
