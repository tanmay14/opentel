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
import org.tmf.openapi.catalog.domain.catalog.PhysicalResource;
import org.tmf.openapi.catalog.repository.PhysicalResourceRepository;

import com.querydsl.core.types.Predicate;

@Service
public class PhysicalResourceService {

	@Autowired
	private PhysicalResourceRepository physicalResourceRepository;

	public PhysicalResource createPhysicalResource(@Valid PhysicalResource resource) {

		if (resource.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating PhysicalResource");
		}
		
		

		setDefaultValues(resource);
		return physicalResourceRepository.save(resource);
	}

	public PhysicalResource findPhysicalResource(@NotNull String id) {
		return physicalResourceRepository.findById(id).get();
	}

	public List<PhysicalResource> findAllPhysicalResources(Predicate predicate) {

		if (null == predicate) {
			return physicalResourceRepository.findAll();
		}
		return toList(physicalResourceRepository.findAll(predicate));
	}

	public void deletePhysicalResource(@NotNull String id) {
		PhysicalResource existingPhysicalResource = getExistingPhysicalResource(id);
		physicalResourceRepository.delete(existingPhysicalResource);

	}

	public PhysicalResource updatePhysicalResource(@Valid PhysicalResource resource) {

		return physicalResourceRepository.save(resource);
	}

	public PhysicalResource partialUpdatePhysicalResource(PhysicalResource resource) {

		if (null == resource.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		PhysicalResource existingPhysicalResource = getExistingPhysicalResource(resource.getId());

		if (null != resource.getName()) {
			existingPhysicalResource.setName(resource.getName());
		}

		if (null != resource.getDescription()) {
			existingPhysicalResource.setDescription(resource.getDescription());
		}

		if (null != resource.getSchemaLocation()) {
			existingPhysicalResource.setSchemaLocation(resource.getSchemaLocation());
		}

		if (null != resource.getVersion()) {
			existingPhysicalResource.setVersion(resource.getVersion());
		}

		if (null != resource.getValidFor()) {
			existingPhysicalResource.setValidFor(resource.getValidFor());
		}

		if (null != resource.getLifecycleState()) {
			existingPhysicalResource.setLifecycleState(resource.getLifecycleState());
		}
		
       
		

		return physicalResourceRepository.save(existingPhysicalResource);

	}

	private PhysicalResource getExistingPhysicalResource(@NotNull String id) {
		Optional<PhysicalResource> existingPhysicalResourceOption = physicalResourceRepository.findById(id);
		if (!existingPhysicalResourceOption.isPresent()) {
			throw new IllegalArgumentException("PhysicalResource with id " + id + " doesnot exists");
		}

		PhysicalResource existingPhysicalResource = existingPhysicalResourceOption.get();
		return existingPhysicalResource;
	}

	private void setDefaultValues(PhysicalResource resource) {
		
	
		if (null == resource.getType() || resource.getType().trim().equals("")) {
			resource.setType("ServicePhysicalResource");
		}
		if (null == resource.getBaseType() || resource.getBaseType().trim().equals("")) {
			resource.setBaseType("PhysicalResource");
		}		
		
		if (null == resource.getLifecycleState() || resource.getLifecycleState().trim().equals("")) {
			resource.setLifecycleState("In Design");
		}
		
		if (null == resource.getVersion()) {
			resource.setVersion("1.0");
		}		
		
	}
	

}
