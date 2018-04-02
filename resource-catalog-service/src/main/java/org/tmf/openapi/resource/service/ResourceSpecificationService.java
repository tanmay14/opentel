package org.tmf.openapi.resource.service;

import static org.tmf.openapi.resource.common.ListUtils.toList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.resource.domain.ResourceSpecification;

import org.tmf.openapi.resource.repository.ResourceSpecificationRepository;

import com.querydsl.core.types.Predicate;

@Service
public class ResourceSpecificationService {

	@Autowired
	private ResourceSpecificationRepository resourceSpecificationRepository;

	public ResourceSpecification createResourceSpecification(@Valid ResourceSpecification resourceSpecification) {

		if (resourceSpecification.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating Category");
		}

		setDefaultValues(resourceSpecification);

		return resourceSpecificationRepository.save(resourceSpecification);
	}

	public ResourceSpecification findResourceSpecification(@NotNull String id) {
		return resourceSpecificationRepository.findById(id).get();
	}

	public List<ResourceSpecification> findAllResourceSpecification(Predicate predicate) {

		if (null == predicate) {
			return resourceSpecificationRepository.findAll();
		}
		return toList(resourceSpecificationRepository.findAll(predicate));
	}
	

	public void deleteResourceSpecification(@NotNull String id) {
		ResourceSpecification existingCategory = getExistingCategory(id);
		resourceSpecificationRepository.delete(existingCategory);

	}

	public ResourceSpecification updateResourceSpecification(@Valid ResourceSpecification resourceSpecification) {

		return resourceSpecificationRepository.save(resourceSpecification);
	}

	public ResourceSpecification partialUpdateResourceSpecification(ResourceSpecification resourceSpecification) {

		if (null == resourceSpecification.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		ResourceSpecification existingCategory = getExistingCategory(resourceSpecification.getId());

		if (null != resourceSpecification.getName()) {
			existingCategory.setName(resourceSpecification.getName());
		}

		if (null != resourceSpecification.getDescription()) {
			existingCategory.setDescription(resourceSpecification.getDescription());
		}

		if (null != resourceSpecification.getSchemaLocation()) {
			existingCategory.setSchemaLocation(resourceSpecification.getSchemaLocation());
		}

		if (null != resourceSpecification.getVersion()) {
			existingCategory.setVersion(resourceSpecification.getVersion());
		}

		if (null != resourceSpecification.getValidFor()) {
			existingCategory.setValidFor(resourceSpecification.getValidFor());
		}

		if (null != resourceSpecification.getLifecycleStatus()) {
			existingCategory.setLifecycleStatus(resourceSpecification.getLifecycleStatus());
		}

		

		

		return saveResourceSpecification(existingCategory);

	}

	private ResourceSpecification saveResourceSpecification(@Valid ResourceSpecification resourceSpecification) {
		return resourceSpecificationRepository.save(resourceSpecification);
	}

	private void setDefaultValues(ResourceSpecification resourceSpecification) {
		if (null == resourceSpecification.getType() || resourceSpecification.getType().trim().equals("")) {
			resourceSpecification.setType("ServiceCategory");
		}

		if (null == resourceSpecification.getVersion()) {
			resourceSpecification.setVersion("1.0");
		}

		
	}

	private ResourceSpecification getExistingCategory(@NotNull String id) {
		Optional<ResourceSpecification> existingCategoryOption = resourceSpecificationRepository.findById(id);
		if (!existingCategoryOption.isPresent()) {
			throw new IllegalArgumentException("resourceSpecificationRepository with id " + id + " doesnot exists");
		}

		ResourceSpecification existingCategory = existingCategoryOption.get();
		return existingCategory;
	}

}
