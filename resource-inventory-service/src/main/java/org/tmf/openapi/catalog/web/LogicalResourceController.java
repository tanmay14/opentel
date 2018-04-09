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
import org.tmf.openapi.catalog.domain.catalog.LogicalResource;
import org.tmf.openapi.catalog.service.LogicalResourceService;

import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/resourceInventoryManagement/logicalResource")
public class LogicalResourceController {

	@Autowired
	private LogicalResourceService logicalResourceService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getLogicalResource(@RequestParam MultiValueMap<String, String> requestParams,
			Pageable pageable, @QuerydslPredicate(root = LogicalResource.class) Predicate predicate) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(logicalResourceService.findAllLogicalResources(predicate)),
				requestParams, "logicalLogicalResourceFilter"));

	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCalalog(@PathVariable String id,
			@RequestParam MultiValueMap<String, String> requestParams) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(logicalResourceService.findLogicalResource(id)), requestParams,
				"logicalLogicalResourceFilter"));

	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> createLogicalResource(@RequestBody @Valid LogicalResource logicalResource)
			throws URISyntaxException {

		logicalResource = logicalResourceService.createLogicalResource(logicalResource);
		
		System.out.println(logicalResource);
		return ResponseEntity.created(populateHref(logicalResource).getHref())
				.body(mapObjectWithExcludeFilter(logicalResource, null, "logicalLogicalResourceFilter"));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> updateLogicalResource(@PathVariable String id, @RequestBody LogicalResource logicalResource) {

		validateLogicalResource(id, logicalResource);
		return ResponseEntity.ok(
				mapObjectWithExcludeFilter(populateHref(logicalResourceService.updateLogicalResource(logicalResource)), null, "logicalLogicalResourceFilter"));

	}

	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> patchLogicalResource(@PathVariable String id, @RequestBody LogicalResource logicalResource) {

		validateLogicalResource(id, logicalResource);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(logicalResourceService.partialUpdateLogicalResource(logicalResource)),
				null, "logicalLogicalResourceFilter"));

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> deleteLogicalResource(@PathVariable String id) {
		logicalResourceService.deleteLogicalResource(id);
		return ResponseEntity.noContent().build();

	}

	private LogicalResource populateHref(LogicalResource logicalResource) {
		logicalResource.setHref(linkTo(LogicalResourceController.class).slash(logicalResource.getId()).toUri());
		return logicalResource;
	}

	private List<LogicalResource> populateHref(List<LogicalResource> logicalResources) {
		for (LogicalResource logicalResource : logicalResources) {
			populateHref(logicalResource);
		}
		return logicalResources;
	}

	private void validateLogicalResource(String id, LogicalResource logicalResource) {
		if ((null == logicalResource.getId()) || (null != logicalResource.getId() && !logicalResource.getId().equals(id))) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
	}

}
