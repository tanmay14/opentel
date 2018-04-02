package org.tmf.openapi.resource.service;

import static org.tmf.openapi.resource.common.ListUtils.toList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.resource.domain.ResourceCandidate;
import org.tmf.openapi.resource.repository.ResourceCandidateRepository;

import com.querydsl.core.types.Predicate;

@Service
public class ResourceCandidateService {

	@Autowired
	private ResourceCandidateRepository serviceCandidateRepository;

	public ResourceCandidate createServiceCandidate(@Valid ResourceCandidate serviceCandidate) {

		if (serviceCandidate.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating ServiceCandidate");
		}

		setDefaultValues(serviceCandidate);

		return serviceCandidateRepository.save(serviceCandidate);
	}

	public ResourceCandidate findServiceCandidate(@NotNull String id) {
		return serviceCandidateRepository.findById(id).get();
	}

	public List<ResourceCandidate> findAllServiceCandidates(Predicate predicate) {

		if (null == predicate) {
			return serviceCandidateRepository.findAll();
		}
		return toList(serviceCandidateRepository.findAll(predicate));
	}

	public void deleteServiceCandidate(@NotNull String id) {

		ResourceCandidate existingServiceCandidate = getExistingServiceCandidate(id);
		serviceCandidateRepository.delete(existingServiceCandidate);

	}

	public ResourceCandidate updateServiceCandidate(@Valid ResourceCandidate serviceCandidate) {

		return saveServiceCandidate(serviceCandidate);
	}

	public ResourceCandidate partialUpdateServiceCandidate(ResourceCandidate serviceCandidate) {

		if (null == serviceCandidate.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		ResourceCandidate existingServiceCandidate = getExistingServiceCandidate(serviceCandidate.getId());

				
		if (null != serviceCandidate.getName()) {
			existingServiceCandidate.setName(serviceCandidate.getName());
		}

		if (null != serviceCandidate.getDescription()) {
			existingServiceCandidate.setDescription(serviceCandidate.getDescription());
		}	

		if (null != serviceCandidate.getLifecycleStatus()) {
			existingServiceCandidate.setLifecycleStatus(serviceCandidate.getLifecycleStatus());
		}

		if (null != serviceCandidate.getValidFor()) {
			existingServiceCandidate.setValidFor(serviceCandidate.getValidFor());
		}

		if (null != serviceCandidate.getVersion()) {
			existingServiceCandidate.setVersion(serviceCandidate.getVersion());
		}

		if (null != serviceCandidate.getSchemaLocation()) {
			existingServiceCandidate.setSchemaLocation(serviceCandidate.getSchemaLocation());
		}	

		
		if (null != serviceCandidate.getCategory()) {
			existingServiceCandidate.setCategory(serviceCandidate.getCategory());
		}
		
		if(null!=serviceCandidate.getType()) {
			existingServiceCandidate.setType(serviceCandidate.getType());
		}
		
		if(null!=serviceCandidate.getBaseType()) {
			existingServiceCandidate.setBaseType(serviceCandidate.getBaseType());
		}
		
		if(null!=serviceCandidate.getLastUpdate()) {
			existingServiceCandidate.setLastUpdate(serviceCandidate.getLastUpdate());
		}
		
		if(null!=serviceCandidate.getResourceSpecification()) {
			existingServiceCandidate.setResourceSpecification(serviceCandidate.getResourceSpecification());
		}

		return saveServiceCandidate(existingServiceCandidate);

	}

	private ResourceCandidate saveServiceCandidate(@Valid ResourceCandidate serviceCandidate) {
		return serviceCandidateRepository.save(serviceCandidate);
	}

	private void setDefaultValues(ResourceCandidate serviceCandidate) {
		

		if (null == serviceCandidate.getType() || serviceCandidate.getType().trim().equals("")) {

			serviceCandidate.setType("ServiceCandidate");
		}

	}

	private ResourceCandidate getExistingServiceCandidate(@NotNull String id) {
		Optional<ResourceCandidate> existingServiceCandidateOption = serviceCandidateRepository.findById(id);
		if (!existingServiceCandidateOption.isPresent()) {
			throw new IllegalArgumentException("ServiceCandidate with id " + id + " doesnot exists");
		}

		ResourceCandidate existingServiceCandidate = existingServiceCandidateOption.get();
		return existingServiceCandidate;
	}

}
