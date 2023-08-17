package com.jema.app.entities;

import lombok.Data;

@Data
public class AboutPoliciesUpdateRequest {
	private Long id;
	private String aboutUsContent;
	private String contactContent;
	private String tContent;
	private String policyContent;


}
