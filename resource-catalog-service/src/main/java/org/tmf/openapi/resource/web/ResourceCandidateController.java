package org.tmf.openapi.resource.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.tmf.openapi.resource.common.ObjectMapper.mapObjectWithExcludeFilter;

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
import org.tmf.openapi.resource.domain.ResourceCandidate;
import org.tmf.openapi.resource.service.ResourceCandidateService;

import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/catalogManagement/resourceCandidate")
public class ResourceCandidateController {

	@Autowired
	private ResourceCandidateService serviceCandidateService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getServiceCandidate(
			@RequestParam MultiValueMap<String, String> requestParams, Pageable pageable,
			@QuerydslPredicate(root = ResourceCandidate.class) Predicate predicate) {
		return ResponseEntity
				.ok(mapObjectWithExcludeFilter(populateHref(serviceCandidateService.findAllServiceCandidates(predicate)),
						requestParams, "resourceCandidateFilter"));

	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getServiceCandidate(@PathVariable String id,
			@RequestParam MultiValueMap<String, String> requestParams) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(
				populateHref(serviceCandidateService.findServiceCandidate(id)), requestParams, "resourceCandidateFilter"));

	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> createServiceCandidate(
			@RequestBody @Valid ResourceCandidate serviceCandidate) throws URISyntaxException {

		serviceCandidate = serviceCandidateService.createServiceCandidate(serviceCandidate);
		return ResponseEntity.created(populateHref(serviceCandidate).getHref())
				.body(mapObjectWithExcludeFilter(serviceCandidate, null, "resourceCandidateFilter"));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> updateServiceCandidate(@PathVariable String id,
			@RequestBody ResourceCandidate serviceCandidate) {

		validateServiceCandidate(id, serviceCandidate);
		return ResponseEntity.ok(
				mapObjectWithExcludeFilter(populateHref(serviceCandidateService.updateServiceCandidate(serviceCandidate)),
						null, "resourceCandidateFilter"));

	}

	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> patchServiceCandidate(@PathVariable String id,
			@RequestBody ResourceCandidate serviceCandidate) {

		validateServiceCandidate(id, serviceCandidate);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(
				populateHref(serviceCandidateService.partialUpdateServiceCandidate(serviceCandidate)), null,
				"resourceCandidateFilter"));

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> deleteCatalog(@PathVariable String id) {
		serviceCandidateService.deleteServiceCandidate(id);
		return ResponseEntity.noContent().build();

	}

	private ResourceCandidate populateHref(ResourceCandidate serviceCandidate) {
		serviceCandidate.setHref(linkTo(ResourceCandidateController.class).slash(serviceCandidate.getId()).toUri());
		return serviceCandidate;
	}

	private List<ResourceCandidate> populateHref(List<ResourceCandidate> serviceCandidates) {
		for (ResourceCandidate serviceCandidate : serviceCandidates) {
			populateHref(serviceCandidate);
		}
		return serviceCandidates;
	}

	private void validateServiceCandidate(String id, ResourceCandidate serviceCandidate) {
		if ((null == serviceCandidate.getId())
				|| (null != serviceCandidate.getId() && !serviceCandidate.getId().equals(id))) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
	}

}
