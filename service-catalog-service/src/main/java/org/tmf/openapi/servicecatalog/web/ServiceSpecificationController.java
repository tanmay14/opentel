package org.tmf.openapi.servicecatalog.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.tmf.openapi.servicecatalog.common.ObjectMapper.mapObjectWithExcludeFilter;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tmf.openapi.servicecatalog.domain.ServiceSpecification;
import org.tmf.openapi.servicecatalog.service.ServiceSpecificationService;

import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/catalogManagement/serviceSpecification")
public class ServiceSpecificationController {

	@Autowired
	private ServiceSpecificationService serviceSpecificationService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getServiceSpecification(
			@RequestParam MultiValueMap<String, String> requestParams, Pageable pageable,
			@QuerydslPredicate(root = ServiceSpecification.class) Predicate predicate) {
		return ResponseEntity
				.ok(mapObjectWithExcludeFilter(populateHref(serviceSpecificationService.findAllServiceSpecifications(predicate)),
						requestParams, "serviceSpecificationFilter"));

	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getServiceSpecification(@PathVariable String id,
			@RequestParam MultiValueMap<String, String> requestParams) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(
				populateHref(serviceSpecificationService.findServiceSpecification(id)), requestParams, "serviceSpecificationFilter"));

	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> createServiceSpecification(
			@RequestBody @Valid ServiceSpecification serviceSpecification) throws URISyntaxException {

		serviceSpecification = serviceSpecificationService.createServiceSpecification(serviceSpecification);
		return ResponseEntity.created(populateHref(serviceSpecification).getHref())
				.body(mapObjectWithExcludeFilter(serviceSpecification, null, "serviceSpecificationFilter"));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> updateServiceSpecification(@PathVariable String id,
			@RequestBody ServiceSpecification serviceSpecification) {

		validateServiceSpecification(id, serviceSpecification);
		return ResponseEntity.ok(
				mapObjectWithExcludeFilter(populateHref(serviceSpecificationService.updateServiceSpecification(serviceSpecification)),
						null, "serviceSpecificationFilter"));

	}

	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> patchServiceSpecification(@PathVariable String id,
			@RequestBody ServiceSpecification serviceSpecification) {

		validateServiceSpecification(id, serviceSpecification);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(
				populateHref(serviceSpecificationService.partialUpdateServiceSpecification(serviceSpecification)), null,
				"serviceSpecificationFilter"));

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> deleteCatalog(@PathVariable String id) {
		serviceSpecificationService.deleteServiceSpecification(id);
		return ResponseEntity.noContent().build();

	}

	private ServiceSpecification populateHref(ServiceSpecification serviceSpecification) {
		serviceSpecification.setHref(linkTo(ServiceSpecificationController.class).slash(serviceSpecification.getId()).toUri());
		return serviceSpecification;
	}

	private List<ServiceSpecification> populateHref(List<ServiceSpecification> serviceSpecifications) {
		for (ServiceSpecification serviceSpecification : serviceSpecifications) {
			populateHref(serviceSpecification);
		}
		return serviceSpecifications;
	}

	private void validateServiceSpecification(String id, ServiceSpecification serviceSpecification) {
		if ((null == serviceSpecification.getId())
				|| (null != serviceSpecification.getId() && !serviceSpecification.getId().equals(id))) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
	}

}
