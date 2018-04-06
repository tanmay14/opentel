package org.tmf.openapi.servicecatalog.domain;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import org.tmf.openapi.servicecatalog.domain.common.LifeCycleStatus;
import org.tmf.openapi.servicecatalog.domain.common.RelatedPartyRef;
import org.tmf.openapi.servicecatalog.domain.common.ResourceSpecificationRef;
import org.tmf.openapi.servicecatalog.domain.common.TargetServiceSchemaRef;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Document
@JsonFilter("serviceSpecificationFilter")
@Data
@EqualsAndHashCode(of = "id")
@ToString(includeFieldNames = true)
@QueryEntity
public class ServiceSpecification {

	@Id
	private String id;
	
	@Transient
	private URI href;

	@NotEmpty
	@Indexed(unique = true)
	private String name;

	private String description;
	
	@NotEmpty
	@JsonProperty("@type")
	private String type;

	@JsonProperty("@baseType")
	private String baseType;

	@JsonProperty("@schemaLocation")
	private String schemaLocation;
	
	@NotNull
	@Valid
	private TimePeriod validFor; //startdate-->todays date

	private String version; //version-->1.0
	
	private String lastUpdate; //todays date

	private String lifecycleStatus; //In design 

	private boolean isBundle; //false

	private List<ResourceSpecificationRef> resourceSpecification;
	
	private List<Attachment> attachment;

	private List<ServiceSpecCharacteristic> serviceSpecCharacteristic;

	private List<RelatedPartyRef> relatedParty;

	private List<ServiceSpecificationRelationship> serviceSpecRelationship;	

	private TargetServiceSchemaRef targetServiceSchema;
	
	private List<ServiceSpecCharRelationship> serviceSpecCharRelationship;
	
	private List<ServiceSpecCharacteristicValue> serviceSpecCharacteristicValue;
	
	

}
