package org.tmf.openapi.servicecatalog.domain;

import javax.validation.Valid;

import org.tmf.openapi.servicecatalog.domain.common.BaseRef;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceSpecificationRelationship extends BaseRef{
	
	
	
	private String role;

	@Valid
	private TimePeriod validFor;

}
