package org.tmf.openapi.catalog.domain.common;

import javax.validation.Valid;

import org.tmf.openapi.catalog.domain.TimePeriod;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data

public class ResourceRelationship {

	private String type;
	
	@Valid
	private TimePeriod validFor;
	
	private ResourceRef resourceRef;
	

	

}
