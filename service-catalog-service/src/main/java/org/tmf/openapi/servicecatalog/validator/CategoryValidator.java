package org.tmf.openapi.servicecatalog.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.tmf.openapi.servicecatalog.domain.catalog.ServiceCategory;

public class CategoryValidator implements ConstraintValidator<ValidCategory, ServiceCategory> {

	@Override
	public boolean isValid(ServiceCategory category, ConstraintValidatorContext context) {

		if (null == category || null == category.getIsRoot()) {
			return true;
		}
		
		if (false == category.getIsRoot()) {

			if (null == category.getParentId() || "".equals(category.getParentId().trim())) {
				return false;
			}
		}
		else {
			
			if (null != category.getParentId() || !"".equals(category.getParentId().trim())) {
				return false;
			}
		}
		return true;
	}
}