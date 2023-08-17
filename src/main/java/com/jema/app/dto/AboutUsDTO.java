package com.jema.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AboutUsDTO {
	private Long id;
	private String aboutUsContent;
	private String contactContent;
	private String tContent;
	private String policyContent;

}
