package org.tmf.openapi.servicecatalog.service;

import static org.tmf.openapi.servicecatalog.common.ListUtils.toList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.servicecatalog.domain.ServiceCandidate;
import org.tmf.openapi.servicecatalog.repository.ServiceCandidateRepository;

import com.querydsl.core.types.Predicate;

@Service
public class ServiceCandidateService {

	@Autowired
	private ServiceCandidateRepository serviceCandidateRepository;

	public ServiceCandidate createServiceCandidate(@Valid ServiceCandidate serviceCandidate) {

		if (serviceCandidate.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating ServiceCandidate");
		}

		setDefaultValues(serviceCandidate);

		return serviceCandidateRepository.save(serviceCandidate);
	}

	public ServiceCandidate findServiceCandidate(@NotNull String id) {
		return serviceCandidateRepository.findById(id).get();
	}

	public List<ServiceCandidate> findAllServiceCandidates(Predicate predicate) {

		if (null == predicate) {
			return serviceCandidateRepository.findAll();
		}
		return toList(serviceCandidateRepository.findAll(predicate));
	}

	public void deleteServiceCandidate(@NotNull String id) {

		ServiceCandidate existingServiceCandidate = getExistingServiceCandidate(id);
		serviceCandidateRepository.delete(existingServiceCandidate);

	}

	public ServiceCandidate updateServiceCandidate(@Valid ServiceCandidate serviceCandidate) {

		return saveServiceCandidate(serviceCandidate);
	}

	public ServiceCandidate partialUpdateServiceCandidate(ServiceCandidate serviceCandidate) {

		if (null == serviceCandidate.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		ServiceCandidate existingServiceCandidate = getExistingServiceCandidate(serviceCandidate.getId());

				
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
		
		if(null!=serviceCandidate.getServiceSpecification()) {
			existingServiceCandidate.setServiceSpecification(serviceCandidate.getServiceSpecification());
		}

		return saveServiceCandidate(existingServiceCandidate);

	}

	private ServiceCandidate saveServiceCandidate(@Valid ServiceCandidate serviceCandidate) {
		return serviceCandidateRepository.save(serviceCandidate);
	}

	private void setDefaultValues(ServiceCandidate serviceCandidate) {
		

		if (null == serviceCandidate.getType() || serviceCandidate.getType().trim().equals("")) {

			serviceCandidate.setType("ServiceCandidate");
		}

	}

	private ServiceCandidate getExistingServiceCandidate(@NotNull String id) {
		Optional<ServiceCandidate> existingServiceCandidateOption = serviceCandidateRepository.findById(id);
		if (!existingServiceCandidateOption.isPresent()) {
			throw new IllegalArgumentException("ServiceCandidate with id " + id + " doesnot exists");
		}

		ServiceCandidate existingServiceCandidate = existingServiceCandidateOption.get();
		return existingServiceCandidate;
	}

}
