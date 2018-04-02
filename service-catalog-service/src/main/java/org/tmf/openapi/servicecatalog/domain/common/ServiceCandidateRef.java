package org.tmf.openapi.servicecatalog.domain.common;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceCandidateRef extends BaseRef{
	private String version;

	
}
