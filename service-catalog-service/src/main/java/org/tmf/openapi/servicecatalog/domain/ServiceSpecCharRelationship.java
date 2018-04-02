package org.tmf.openapi.servicecatalog.domain;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ServiceSpecCharRelationship {

	private String type;

	@Valid
	private TimePeriod validFor;	
	private String id;
	private String name;
	private String href;
	@JsonProperty("@type")
	private String at_type;

}
