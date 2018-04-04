package org.tmf.openapi.servicecatalog.service;

import static org.tmf.openapi.servicecatalog.common.ListUtils.toList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.servicecatalog.domain.ServiceSpecification;
import org.tmf.openapi.servicecatalog.repository.ServiceSpecificationRepository;

import com.querydsl.core.types.Predicate;

@Service
public class ServiceSpecificationService {

	@Autowired
	private ServiceSpecificationRepository serviceSpecificationRepository;

	public ServiceSpecification createServiceSpecification(@Valid ServiceSpecification serviceSpecification) {

		if (serviceSpecification.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating ServiceSpecification");
		}

		setDefaultValues(serviceSpecification);

		return serviceSpecificationRepository.save(serviceSpecification);
	}

	public ServiceSpecification findServiceSpecification(@NotNull String id) {
		return serviceSpecificationRepository.findById(id).get();
	}

	public List<ServiceSpecification> findAllServiceSpecifications(Predicate predicate) {

		if (null == predicate) {
			return serviceSpecificationRepository.findAll();
		}
		return toList(serviceSpecificationRepository.findAll(predicate));
	}

	public void deleteServiceSpecification(@NotNull String id) {

		ServiceSpecification existingServiceSpecification = getExistingServiceSpecification(id);
		serviceSpecificationRepository.delete(existingServiceSpecification);

	}

	public ServiceSpecification updateServiceSpecification(@Valid ServiceSpecification serviceSpecification) {

		return saveServiceSpecification(serviceSpecification);
	}

	public ServiceSpecification partialUpdateServiceSpecification(ServiceSpecification serviceSpecification) {

		if (null == serviceSpecification.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		ServiceSpecification existingServiceSpecification = getExistingServiceSpecification(serviceSpecification.getId());

				
		if (null != serviceSpecification.getName()) {
			existingServiceSpecification.setName(serviceSpecification.getName());
		}

		if (null != serviceSpecification.getDescription()) {
			existingServiceSpecification.setDescription(serviceSpecification.getDescription());
		}	

		if (null != serviceSpecification.getLifecycleStatus()) {
			existingServiceSpecification.setLifecycleStatus(serviceSpecification.getLifecycleStatus());
		}

		if (null != serviceSpecification.getValidFor()) {
			existingServiceSpecification.setValidFor(serviceSpecification.getValidFor());
		}

		if (null != serviceSpecification.getVersion()) {
			existingServiceSpecification.setVersion(serviceSpecification.getVersion());
		}

		if (null != serviceSpecification.getSchemaLocation()) {
			existingServiceSpecification.setSchemaLocation(serviceSpecification.getSchemaLocation());
		}	
		
		if(null!=serviceSpecification.getType()) {
			existingServiceSpecification.setType(serviceSpecification.getType());
		}
		
		if(null!=serviceSpecification.getBaseType()) {
			existingServiceSpecification.setBaseType(serviceSpecification.getBaseType());
		}
		
		if(null!=serviceSpecification.getLastUpdate()) {
			existingServiceSpecification.setLastUpdate(serviceSpecification.getLastUpdate());
		}
		
		if(null!=serviceSpecification.getResourceSpecification()) {
			existingServiceSpecification.setResourceSpecification(serviceSpecification.getResourceSpecification());
		}

		return saveServiceSpecification(existingServiceSpecification);

	}

	private ServiceSpecification saveServiceSpecification(@Valid ServiceSpecification serviceSpecification) {
		return serviceSpecificationRepository.save(serviceSpecification);
	}

	private void setDefaultValues(ServiceSpecification serviceSpecification) {
		

		if (null == serviceSpecification.getType() || serviceSpecification.getType().trim().equals("")) {

			serviceSpecification.setType("ServiceSpecification");
		}

	}

	private ServiceSpecification getExistingServiceSpecification(@NotNull String id) {
		Optional<ServiceSpecification> existingServiceSpecificationOption = serviceSpecificationRepository.findById(id);
		if (!existingServiceSpecificationOption.isPresent()) {
			throw new IllegalArgumentException("ServiceSpecification with id " + id + " doesnot exists");
		}

		ServiceSpecification existingServiceSpecification = existingServiceSpecificationOption.get();
		return existingServiceSpecification;
	}

}
