package org.tmf.openapi.servicecatalog.domain.common;

import javax.validation.Valid;

import org.tmf.openapi.servicecatalog.domain.TimePeriod;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RelatedPartyRef extends BaseRef {

	private String role;

	@Valid
	private TimePeriod validFor;

}
