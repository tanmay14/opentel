package org.tmf.openapi.servicecatalog.domain;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.tmf.openapi.servicecatalog.domain.common.LifeCycleStatus;
import org.tmf.openapi.servicecatalog.domain.common.RelatedPartyRef;
import org.tmf.openapi.servicecatalog.domain.common.ResourceSpecificationRef;
import org.tmf.openapi.servicecatalog.domain.common.TargetProductSchemaRef;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ServiceSpecification {

	@NotEmpty
	private String id;

	@NotEmpty
	private String href;

	private String name;

	private String description;
	
	@JsonProperty("@type")
	private String type;

	@JsonProperty("@baseType")
	private String baseType;

	@JsonProperty("@schemaLocation")
	private String schemaLocation;
	
	@NotNull
	@Valid
	private TimePeriod validFor;

	private String version;
	
	private Date lastUpdate;

	private LifeCycleStatus lifecycleStatus;

	private boolean isBundle;

	private List<ResourceSpecificationRef> resourceSpecification;
	
	private List<Attachment> attachment;

	private List<ServiceSpecCharacteristic> serviceSpecCharacteristic;

	private List<RelatedPartyRef> relatedParty;

	private List<ServiceSpecificationRelationship> serviceSpecificationRelationship;	

	private TargetProductSchemaRef targetServiceSchema;
	

}
