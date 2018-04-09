package org.tmf.openapi.catalog.service;

import static org.tmf.openapi.catalog.common.ListUtils.toList;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.swing.text.DateFormatter;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.tmf.openapi.catalog.domain.TimePeriod;
import org.tmf.openapi.catalog.domain.catalog.Resource;
import org.tmf.openapi.catalog.repository.ResourceRepository;

import com.querydsl.core.types.Predicate;

@Service
public class ResourceService {

	@Autowired
	private ResourceRepository resourceRepository;

	public Resource createResource(@Valid Resource resource) {

		if (resource.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating Resource");
		}
		
		

		setDefaultValues(resource);
		return resourceRepository.save(resource);
	}

	public Resource findResource(@NotNull String id) {
		return resourceRepository.findById(id).get();
	}

	public List<Resource> findAllResources(Predicate predicate) {

		if (null == predicate) {
			return resourceRepository.findAll();
		}
		return toList(resourceRepository.findAll(predicate));
	}

	public void deleteResource(@NotNull String id) {
		Resource existingResource = getExistingResource(id);
		resourceRepository.delete(existingResource);

	}

	public Resource updateResource(@Valid Resource resource) {

		return resourceRepository.save(resource);
	}

	public Resource partialUpdateResource(Resource resource) {

		if (null == resource.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		Resource existingResource = getExistingResource(resource.getId());

		if (null != resource.getName()) {
			existingResource.setName(resource.getName());
		}

		if (null != resource.getDescription()) {
			existingResource.setDescription(resource.getDescription());
		}

		if (null != resource.getSchemaLocation()) {
			existingResource.setSchemaLocation(resource.getSchemaLocation());
		}

		if (null != resource.getVersion()) {
			existingResource.setVersion(resource.getVersion());
		}

		if (null != resource.getValidFor()) {
			existingResource.setValidFor(resource.getValidFor());
		}

		if (null != resource.getLifecycleStatus()) {
			existingResource.setLifecycleStatus(resource.getLifecycleStatus());
		}
		
       
		

		return resourceRepository.save(existingResource);

	}

	private Resource getExistingResource(@NotNull String id) {
		Optional<Resource> existingResourceOption = resourceRepository.findById(id);
		if (!existingResourceOption.isPresent()) {
			throw new IllegalArgumentException("Resource with id " + id + " doesnot exists");
		}

		Resource existingResource = existingResourceOption.get();
		return existingResource;
	}

	private void setDefaultValues(Resource resource) {
		
	
		/*if (null == resource.getType() || resource.getType().trim().equals("")) {
			resource.setType("ServiceResource");
		}
		if (null == resource.getBaseType() || resource.getBaseType().trim().equals("")) {
			resource.setBaseType("Resource");
		}		
		
		if (null == resource.getLifecycleStatus() || resource.getLifecycleStatus().trim().equals("")) {
			resource.setLifecycleStatus("In Design");
		}
		
		if (null == resource.getVersion()) {
			resource.setVersion("1.0");
		}		*/
		
	}
	

}
