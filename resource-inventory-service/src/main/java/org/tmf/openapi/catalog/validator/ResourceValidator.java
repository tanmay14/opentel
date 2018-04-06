package org.tmf.openapi.catalog.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.tmf.openapi.catalog.domain.catalog.Resource;


public class ResourceValidator implements ConstraintValidator<ValidCategory, Resource> {

	@Override
	public boolean isValid(Resource resource, ConstraintValidatorContext context) {

		/*if (null == resource || null == resource.getIsRoot()) {
			return true;
		}
		
		if (false == resource.getIsRoot()) {

			if (null == resource.getParentId() || "".equals(resource.getParentId().trim())) {
				return false;
			}
		}
		else {
			
			if (null != resource.getParentId() || !"".equals(resource.getParentId().trim())) {
				return false;
			}
		}
		return true;*/
		return true;
	}
}