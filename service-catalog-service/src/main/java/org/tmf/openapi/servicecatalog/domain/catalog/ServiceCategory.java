package org.tmf.openapi.servicecatalog.domain.catalog;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tmf.openapi.servicecatalog.domain.TimePeriod;
import org.tmf.openapi.servicecatalog.domain.common.CategoryRef;
import org.tmf.openapi.servicecatalog.domain.common.LifeCycleStatus;
import org.tmf.openapi.servicecatalog.domain.common.RelatedPartyRef;
import org.tmf.openapi.servicecatalog.domain.common.ServiceCandidateRef;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Document
@JsonFilter("serviceCategoryFilter")
@Data
@EqualsAndHashCode(of = "id")
@ToString(includeFieldNames = true)
@QueryEntity
public class ServiceCategory {

	@Id
	private String id;

	@Transient
	private URI href;

	@NotEmpty
	@Indexed(unique = true)
	private String name;

	private String description;

	@JsonProperty("@type")
	private String type;

	@JsonProperty("@baseType")
	private String baseType;

	@JsonProperty("@schemaLocation")
	private String schemaLocation;

	private String lastUpdate;

	private String lifecycleStatus;

	@Valid
	private TimePeriod validFor;

	private String version;

	private String parentId;

	private Boolean isRoot;
	
	@Valid
	private List<RelatedPartyRef> relatedParty;

	private List<CategoryRef> category;

	private List<ServiceCandidateRef> serviceCandidate;

}
