package org.tmf.openapi.catalog.domain.common;

import javax.validation.Valid;

import org.tmf.openapi.catalog.domain.TimePeriod;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data

public class Note {

	private String author;

	@Valid
	private String date;
	
	private String text;

}
