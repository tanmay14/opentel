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
import org.tmf.openapi.servicecatalog.domain.catalog.ServiceCatalog;
import org.tmf.openapi.servicecatalog.service.CatalogService;

import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/catalogManagement/serviceCatalog")
public class CatalogController {

	@Autowired
	private CatalogService catalogService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCatalog(@RequestParam MultiValueMap<String, String> requestParams,
			Pageable pageable, @QuerydslPredicate(root = ServiceCatalog.class) Predicate predicate) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(catalogService.findAllCatalogs(predicate)),
				requestParams, "serviceCatalogFilter"));

	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> getCalalog(@PathVariable String id,
			@RequestParam MultiValueMap<String, String> requestParams) {
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(catalogService.findCatalog(id)), requestParams,
				"serviceCatalogFilter"));

	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> createCatalog(@RequestBody @Valid ServiceCatalog serviceCatalog)
			throws URISyntaxException {

		serviceCatalog = catalogService.createCatalog(serviceCatalog);
		
		System.out.println(serviceCatalog);
		return ResponseEntity.created(populateHref(serviceCatalog).getHref())
				.body(mapObjectWithExcludeFilter(serviceCatalog, null, "serviceCatalogFilter"));
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> updateCatalog(@PathVariable String id, @RequestBody ServiceCatalog serviceCatalog) {

		validateCatalog(id, serviceCatalog);
		return ResponseEntity.ok(
				mapObjectWithExcludeFilter(populateHref(catalogService.updateCatalog(serviceCatalog)), null, "serviceCatalogFilter"));

	}

	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> patchCatalog(@PathVariable String id, @RequestBody ServiceCatalog serviceCatalog) {

		validateCatalog(id, serviceCatalog);
		return ResponseEntity.ok(mapObjectWithExcludeFilter(populateHref(catalogService.partialUpdateCatalog(serviceCatalog)),
				null, "serviceCatalogFilter"));

	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MappingJacksonValue> deleteCatalog(@PathVariable String id) {
		catalogService.deleteCatalog(id);
		return ResponseEntity.noContent().build();

	}

	private ServiceCatalog populateHref(ServiceCatalog serviceCatalog) {
		serviceCatalog.setHref(linkTo(CatalogController.class).slash(serviceCatalog.getId()).toUri());
		return serviceCatalog;
	}

	private List<ServiceCatalog> populateHref(List<ServiceCatalog> serviceCatalogs) {
		for (ServiceCatalog serviceCatalog : serviceCatalogs) {
			populateHref(serviceCatalog);
		}
		return serviceCatalogs;
	}

	private void validateCatalog(String id, ServiceCatalog serviceCatalog) {
		if ((null == serviceCatalog.getId()) || (null != serviceCatalog.getId() && !serviceCatalog.getId().equals(id))) {
			throw new IllegalArgumentException("id cannot be updated.");
		}
	}

}
