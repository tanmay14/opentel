package org.tmf.openapi.catalog.domain.common;

import java.util.List;

import javax.validation.Valid;

import org.tmf.openapi.catalog.domain.TimePeriod;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceCharacteristic extends BaseRef{


	private String value;

	
	@JsonProperty("@schemaLocation")
	private String schemaLocation;
	
	
	

	

}
