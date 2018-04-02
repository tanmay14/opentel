package org.tmf.openapi.servicecatalog.service;

import static org.tmf.openapi.servicecatalog.common.ListUtils.toList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.servicecatalog.domain.TimePeriod;
import org.tmf.openapi.servicecatalog.domain.catalog.ServiceCatalog;
import org.tmf.openapi.servicecatalog.repository.CatalogRepository;

import com.querydsl.core.types.Predicate;

@Service
public class CatalogService {

	@Autowired
	private CatalogRepository catalogRepository;

	public ServiceCatalog createCatalog(@Valid ServiceCatalog serviceCatalog) {

		if (serviceCatalog.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating Catalog");
		}

		setDefaultValues(serviceCatalog);
		return catalogRepository.save(serviceCatalog);
	}

	public ServiceCatalog findCatalog(@NotNull String id) {
		return catalogRepository.findById(id).get();
	}

	public List<ServiceCatalog> findAllCatalogs(Predicate predicate) {

		if (null == predicate) {
			return catalogRepository.findAll();
		}
		return toList(catalogRepository.findAll(predicate));
	}

	public void deleteCatalog(@NotNull String id) {
		ServiceCatalog existingCatalog = getExistingCatalog(id);
		catalogRepository.delete(existingCatalog);

	}

	public ServiceCatalog updateCatalog(@Valid ServiceCatalog serviceCatalog) {

		return catalogRepository.save(serviceCatalog);
	}

	public ServiceCatalog partialUpdateCatalog(ServiceCatalog serviceCatalog) {

		if (null == serviceCatalog.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		ServiceCatalog existingCatalog = getExistingCatalog(serviceCatalog.getId());

		if (null != serviceCatalog.getName()) {
			existingCatalog.setName(serviceCatalog.getName());
		}

		if (null != serviceCatalog.getDescription()) {
			existingCatalog.setDescription(serviceCatalog.getDescription());
		}

		if (null != serviceCatalog.getSchemaLocation()) {
			existingCatalog.setSchemaLocation(serviceCatalog.getSchemaLocation());
		}

		if (null != serviceCatalog.getVersion()) {
			existingCatalog.setVersion(serviceCatalog.getVersion());
		}

		if (null != serviceCatalog.getValidFor()) {
			existingCatalog.setValidFor(serviceCatalog.getValidFor());
		}

		if (null != serviceCatalog.getLifecycleStatus()) {
			existingCatalog.setLifecycleStatus(serviceCatalog.getLifecycleStatus());
		}

		return catalogRepository.save(existingCatalog);

	}

	private ServiceCatalog getExistingCatalog(@NotNull String id) {
		Optional<ServiceCatalog> existingCatalogOption = catalogRepository.findById(id);
		if (!existingCatalogOption.isPresent()) {
			throw new IllegalArgumentException("Catalog with id " + id + " doesnot exists");
		}

		ServiceCatalog existingCatalog = existingCatalogOption.get();
		return existingCatalog;
	}

	private void setDefaultValues(ServiceCatalog serviceCatalog) {
		if (null == serviceCatalog.getType() || serviceCatalog.getType().trim().equals("")) {
			serviceCatalog.setType("ServiceCatalog");
		}
		if (null == serviceCatalog.getBaseType() || serviceCatalog.getBaseType().trim().equals("")) {
			serviceCatalog.setBaseType("Catalog");
		}
		
		if (null == serviceCatalog.getDescription() || serviceCatalog.getDescription().trim().equals("")) {
			serviceCatalog.setDescription("");
		}
		
		if (null ==serviceCatalog.getLastUpdate() || serviceCatalog.getLastUpdate().trim().equals("")) {
			serviceCatalog.setLastUpdate("2017-08-30T00:00");
		}
		
		if (null== serviceCatalog.getVersion() || serviceCatalog.getVersion().trim().equals("") ) {
			serviceCatalog.setVersion("1.0");
		}
		
		if (null == serviceCatalog.getLifecycleStatus() || serviceCatalog.getLifecycleStatus().trim().equals("")) {
			serviceCatalog.setLifecycleStatus("In Design");
		}
	}

}
