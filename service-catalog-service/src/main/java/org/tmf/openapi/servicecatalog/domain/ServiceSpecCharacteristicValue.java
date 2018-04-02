package org.tmf.openapi.servicecatalog.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ServiceSpecCharacteristicValue {

	@JsonProperty("default")
	private boolean _default;

	private String unitOfMeasure;

	private TimePeriod validFor;

	private String value;

	private String valueType; // TODO Identify possible values and create ENUM

	private String valueFrom;

	private String valueTo;

	private String rangeInterval; // TODO Identify possible values and create ENUM

	private String regex;
	
	@JsonProperty("@type")
	private String type;
	
	@JsonProperty("@schemaLocation")
	private String schemaLocation;

}
