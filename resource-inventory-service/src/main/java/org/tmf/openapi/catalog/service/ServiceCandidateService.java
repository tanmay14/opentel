package org.tmf.openapi.catalog.service;

import static org.tmf.openapi.catalog.common.ListUtils.toList;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.catalog.domain.ServiceCandidate;
import org.tmf.openapi.catalog.repository.ServiceCandidateRepository;

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
		
		
		if(null!=serviceCandidate.getBaseType()) {
			existingServiceCandidate.setBaseType(serviceCandidate.getBaseType());
		}
		
		
		if(null!=serviceCandidate.getServiceSpecification()) {
			existingServiceCandidate.setServiceSpecification(serviceCandidate.getServiceSpecification());
		}
		
		//existingServiceCandidate.setLastUpdate(LocalTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").ISO_DATE_TIME.withZone().format(Instant.now()));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddTHH:mm");
		existingServiceCandidate.setLastUpdate(sdf.format(new Date()));
		
		return saveServiceCandidate(existingServiceCandidate);

	}

	private ServiceCandidate saveServiceCandidate(@Valid ServiceCandidate serviceCandidate) {
		return serviceCandidateRepository.save(serviceCandidate);
	}

	private void setDefaultValues(ServiceCandidate serviceCandidate) {
		

		if (null == serviceCandidate.getType() || serviceCandidate.getType().trim().equals("")) {

			serviceCandidate.setType("ServiceCandidate");
		}
		
		if (null == serviceCandidate.getVersion() || serviceCandidate.getVersion().trim().equals("")) {
			serviceCandidate.setVersion("1.0");
		}
		
		if (null== serviceCandidate.getLifecycleStatus() || serviceCandidate.getLifecycleStatus().trim().equals(""))
		{
			serviceCandidate.setLifecycleStatus("In Design");
		}
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddTHH:mm");
		serviceCandidate.setLastUpdate(sdf.format(new Date()));

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
