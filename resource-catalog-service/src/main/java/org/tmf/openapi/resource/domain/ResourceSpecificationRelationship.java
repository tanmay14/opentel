package org.tmf.openapi.resource.domain;

import javax.validation.Valid;

import org.tmf.openapi.resource.domain.common.BaseRef;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceSpecificationRelationship extends BaseRef{
	@JsonProperty("@atype")
	private String atype;
	
	private String role;

	@Valid
	private TimePeriod validFor;

}
