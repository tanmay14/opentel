package org.tmf.openapi.catalog.domain.common;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "id")
@ToString(includeFieldNames = true)
public abstract class ValueRef {

	@JsonProperty("@type")
	private String type;
	
	private String serialNumber;
    private String versionNumber;
	private String manufactureDate;


}