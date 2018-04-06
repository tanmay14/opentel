package org.tmf.openapi.catalog.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.tmf.openapi.catalog.domain.ServiceCandidate;

public class ServiceCandidateValidator implements ConstraintValidator<ValidServiceCandidate, ServiceCandidate> {

	@Override
	public boolean isValid(ServiceCandidate serviceCandidate, ConstraintValidatorContext context) {

		if (null == serviceCandidate)
			return true;

		
		return true;
	}
}