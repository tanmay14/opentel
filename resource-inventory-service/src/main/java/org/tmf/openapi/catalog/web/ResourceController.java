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
import org.tmf.openapi.catalog.domain.catalog.Resource;
import org.tmf.openapi.catalog.service.ResourceService;

import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/resourceInventoryManagement/Resource")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getResource(@RequestParam MultiValueMap<String, String> requestParams,
			Pageable pageable, @QuerydslPredicate(root = Resource.class) Predicate predicate) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(resourceService.findAllResources(predicate)),
				requestParams, "resourceInventoryFilter"));

	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCalalog(@PathVariable String id,
			@RequestParam MultiValueMap<String, String> requestParams) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(resourceService.findResource(id)), requestParams,
				"resourceInventoryFilter"));

	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> createResource(@RequestBody @Valid Resource resource)
			throws URISyntaxException {

		resource = resourceService.createResource(resource);
		
		System.out.println(resource);
		return ResponseEntity.created(populateHref(resource).getHref())
				.body(mapObjectWithExcludeFilter(resource, null, "resourceInventoryFilter"));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> updateResource(@PathVariable String id, @RequestBody Resource resource) {

		validateResource(id, resource);
		return ResponseEntity.ok(
				mapObjectWithExcludeFilter(populateHref(resourceService.updateResource(resource)), null, "resourceInventoryFilter"));

	}

	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> patchResource(@PathVariable String id, @RequestBody Resource resource) {

		validateResource(id, resource);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(resourceService.partialUpdateResource(resource)),
				null, "resourceInventoryFilter"));

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> deleteResource(@PathVariable String id) {
		resourceService.deleteResource(id);
		return ResponseEntity.noContent().build();

	}

	private Resource populateHref(Resource resource) {
		resource.setHref(linkTo(ResourceController.class).slash(resource.getId()).toUri());
		return resource;
	}

	private List<Resource> populateHref(List<Resource> resources) {
		for (Resource resource : resources) {
			populateHref(resource);
		}
		return resources;
	}

	private void validateResource(String id, Resource resource) {
		if ((null == resource.getId()) || (null != resource.getId() && !resource.getId().equals(id))) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
	}

}
