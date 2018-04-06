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
@JsonFilter("resourceInventoryFilter")
@Data
@EqualsAndHashCode(of = "id")
@ToString(includeFieldNames = true)
@QueryEntity
public class Resource {

	@Id
	private String id;

	@Transient
	private URI href;

	private String description;
	
	private String category;
	
	private TimePeriod validFor;
	
	@NotEmpty
	@Indexed(unique = true)
	private String name;
	
	private String lifecycleStatus;	

	@JsonProperty("@type")
	private String type;

	@JsonProperty("@baseType")
	private String baseType;

	@JsonProperty("@schemaLocation")
	private String schemaLocation;

	private String version;
	
	private List<ResourceRelationship> resourceRelationship;
	
	private List<PlaceRef> place;
	
	private List<Note> note;
	
	private ResourceSpecificationRef resourceSpecification;	

	@Valid
	private List<RelatedPartyRef> relatedParty;
	
	private List<ResourceCharacteristic> resorceCharacteristic;
	
	private List<ResourceAttachment> resourceAttachment;

}
