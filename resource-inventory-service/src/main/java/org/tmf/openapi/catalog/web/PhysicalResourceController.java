package org.tmf.openapi.catalog.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.tmf.openapi.catalog.common.ObjectMapper.mapObjectWithExcludeFilter;

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
import org.tmf.openapi.catalog.domain.catalog.PhysicalResource;
import org.tmf.openapi.catalog.service.PhysicalResourceService;

import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/resourceInventoryManagement/physicalResource")
public class PhysicalResourceController {

	@Autowired
	private PhysicalResourceService physicalResourceService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getPhysicalResource(@RequestParam MultiValueMap<String, String> requestParams,
			Pageable pageable, @QuerydslPredicate(root = PhysicalResource.class) Predicate predicate) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(physicalResourceService.findAllPhysicalResources(predicate)),
				requestParams, "physicalPhysicalResourceFilter"));

	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCalalog(@PathVariable String id,
			@RequestParam MultiValueMap<String, String> requestParams) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(physicalResourceService.findPhysicalResource(id)), requestParams,
				"physicalPhysicalResourceFilter"));

	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> createPhysicalResource(@RequestBody @Valid PhysicalResource physicalResource)
			throws URISyntaxException {

		physicalResource = physicalResourceService.createPhysicalResource(physicalResource);
		
		System.out.println(physicalResource);
		return ResponseEntity.created(populateHref(physicalResource).getHref())
				.body(mapObjectWithExcludeFilter(physicalResource, null, "physicalPhysicalResourceFilter"));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> updatePhysicalResource(@PathVariable String id, @RequestBody PhysicalResource physicalResource) {

		validatePhysicalResource(id, physicalResource);
		return ResponseEntity.ok(
				mapObjectWithExcludeFilter(populateHref(physicalResourceService.updatePhysicalResource(physicalResource)), null, "physicalPhysicalResourceFilter"));

	}

	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> patchPhysicalResource(@PathVariable String id, @RequestBody PhysicalResource physicalResource) {

		validatePhysicalResource(id, physicalResource);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(physicalResourceService.partialUpdatePhysicalResource(physicalResource)),
				null, "physicalPhysicalResourceFilter"));

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> deletePhysicalResource(@PathVariable String id) {
		physicalResourceService.deletePhysicalResource(id);
		return ResponseEntity.noContent().build();

	}

	private PhysicalResource populateHref(PhysicalResource physicalResource) {
		physicalResource.setHref(linkTo(PhysicalResourceController.class).slash(physicalResource.getId()).toUri());
		return physicalResource;
	}

	private List<PhysicalResource> populateHref(List<PhysicalResource> physicalResources) {
		for (PhysicalResource physicalResource : physicalResources) {
			populateHref(physicalResource);
		}
		return physicalResources;
	}

	private void validatePhysicalResource(String id, PhysicalResource physicalResource) {
		if ((null == physicalResource.getId()) || (null != physicalResource.getId() && !physicalResource.getId().equals(id))) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
	}

}
