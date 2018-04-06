package org.tmf.openapi.catalog.domain.common;

import javax.validation.Valid;

import org.tmf.openapi.catalog.domain.TimePeriod;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data

public class ResourceCharacteristic {

	private String name;
	
	private String value;
	
	@JsonProperty("@schemaLocation")
	private String schemaLocation;
	
	@JsonProperty("@type")
	private String type;
	

	

}
