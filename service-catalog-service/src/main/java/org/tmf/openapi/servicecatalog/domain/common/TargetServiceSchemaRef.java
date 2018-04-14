package org.tmf.openapi.servicecatalog.domain.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TargetServiceSchemaRef {

	@JsonProperty("@referredType")
	private String referredType;

	@JsonProperty("@schemaLocation")
	private String schemaLocation;

}
