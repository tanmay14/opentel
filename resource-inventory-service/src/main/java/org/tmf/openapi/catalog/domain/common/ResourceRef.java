package org.tmf.openapi.catalog.domain.common;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "id")
@ToString(includeFieldNames = true)
public class ResourceRef {

	@NotEmpty
	private String id; //null

	private String href; //same

	
}