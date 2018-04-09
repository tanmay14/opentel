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
import org.tmf.openapi.catalog.domain.catalog.LogicalResource;
import org.tmf.openapi.catalog.repository.LogicalResourceRepository;

import com.querydsl.core.types.Predicate;

@Service
public class LogicalResourceService {

	@Autowired
	private LogicalResourceRepository logicalResourceRepository;

	public LogicalResource createLogicalResource(@Valid LogicalResource resource) {

		if (resource.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating LogicalResource");
		}
		
		

		setDefaultValues(resource);
		return logicalResourceRepository.save(resource);
	}

	public LogicalResource findLogicalResource(@NotNull String id) {
		return logicalResourceRepository.findById(id).get();
	}

	public List<LogicalResource> findAllLogicalResources(Predicate predicate) {

		if (null == predicate) {
			return logicalResourceRepository.findAll();
		}
		return toList(logicalResourceRepository.findAll(predicate));
	}

	public void deleteLogicalResource(@NotNull String id) {
		LogicalResource existingLogicalResource = getExistingLogicalResource(id);
		logicalResourceRepository.delete(existingLogicalResource);

	}

	public LogicalResource updateLogicalResource(@Valid LogicalResource resource) {

		return logicalResourceRepository.save(resource);
	}

	public LogicalResource partialUpdateLogicalResource(LogicalResource resource) {

		if (null == resource.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		LogicalResource existingLogicalResource = getExistingLogicalResource(resource.getId());

		if (null != resource.getName()) {
			existingLogicalResource.setName(resource.getName());
		}

		if (null != resource.getDescription()) {
			existingLogicalResource.setDescription(resource.getDescription());
		}

		if (null != resource.getSchemaLocation()) {
			existingLogicalResource.setSchemaLocation(resource.getSchemaLocation());
		}

		if (null != resource.getVersion()) {
			existingLogicalResource.setVersion(resource.getVersion());
		}

		if (null != resource.getValidFor()) {
			existingLogicalResource.setValidFor(resource.getValidFor());
		}

		if (null != resource.getLifecycleState()) {
			existingLogicalResource.setLifecycleState(resource.getLifecycleState());
		}
		
       
		

		return logicalResourceRepository.save(existingLogicalResource);

	}

	private LogicalResource getExistingLogicalResource(@NotNull String id) {
		Optional<LogicalResource> existingLogicalResourceOption = logicalResourceRepository.findById(id);
		if (!existingLogicalResourceOption.isPresent()) {
			throw new IllegalArgumentException("LogicalResource with id " + id + " doesnot exists");
		}

		LogicalResource existingLogicalResource = existingLogicalResourceOption.get();
		return existingLogicalResource;
	}

	private void setDefaultValues(LogicalResource resource) {
		
	
		if (null == resource.getType() || resource.getType().trim().equals("")) {
			resource.setType("ServiceLogicalResource");
		}
		if (null == resource.getBaseType() || resource.getBaseType().trim().equals("")) {
			resource.setBaseType("LogicalResource");
		}		
		
		if (null == resource.getLifecycleState() || resource.getLifecycleState().trim().equals("")) {
			resource.setLifecycleState("In Design");
		}
		
		if (null == resource.getVersion()) {
			resource.setVersion("1.0");
		}		
		
	}
	

}
