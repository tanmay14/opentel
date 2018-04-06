package org.tmf.openapi.catalog.domain.catalog;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tmf.openapi.catalog.domain.TimePeriod;
import org.tmf.openapi.catalog.domain.common.Note;
import org.tmf.openapi.catalog.domain.common.PlaceRef;
import org.tmf.openapi.catalog.domain.common.RelatedPartyRef;
import org.tmf.openapi.catalog.domain.common.ResourceAttachment;
import org.tmf.openapi.catalog.domain.common.ResourceCharacteristic;
import org.tmf.openapi.catalog.domain.common.ResourceRelationship;
import org.tmf.openapi.catalog.domain.common.ResourceSpecificationRef;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Document
@JsonFilter("physicalResourceFilter")
@Data
@EqualsAndHashCode(callSuper = true)
/*@EqualsAndHashCode(of = "id")*/
@ToString(includeFieldNames = true)
@QueryEntity
public class PhysicalResource extends Resource{

	
	private String manufactureDate;
	private String powerState;
	private String serialNumber;
	private String versionNumber;

}
