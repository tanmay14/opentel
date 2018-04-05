package org.tmf.openapi.servicecatalog.service;

import static org.tmf.openapi.servicecatalog.common.ListUtils.toList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmf.openapi.servicecatalog.domain.catalog.ServiceCategory;
import org.tmf.openapi.servicecatalog.repository.CategoryRepository;

import com.querydsl.core.types.Predicate;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public ServiceCategory createCategory(@Valid ServiceCategory serviceCategory) {

		if (serviceCategory.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating Category");
		}

		setDefaultValues(serviceCategory);

		return categoryRepository.save(serviceCategory);
	}

	public ServiceCategory findCategory(@NotNull String id) {
		return categoryRepository.findById(id).get();
	}

	public List<ServiceCategory> findAllCategories(Predicate predicate) {

		if (null == predicate) {
			return categoryRepository.findAll();
		}
		return toList(categoryRepository.findAll(predicate));
	}
	

	public void deleteCategory(@NotNull String id) {
		ServiceCategory existingCategory = getExistingCategory(id);
		categoryRepository.delete(existingCategory);

	}

	public ServiceCategory updateCategory(@Valid ServiceCategory serviceCategory) {

		return categoryRepository.save(serviceCategory);
	}

	public ServiceCategory partialUpdateCategory(ServiceCategory serviceCategory) {

		if (null == serviceCategory.getId()) {
			throw new IllegalArgumentException("id is mandatory field for update.");
		}

		ServiceCategory existingCategory = getExistingCategory(serviceCategory.getId());

		if (null != serviceCategory.getName()) {
			existingCategory.setName(serviceCategory.getName());
		}

		if (null != serviceCategory.getDescription()) {
			existingCategory.setDescription(serviceCategory.getDescription());
		}

		if (null != serviceCategory.getSchemaLocation()) {
			existingCategory.setSchemaLocation(serviceCategory.getSchemaLocation());
		}

		if (null != serviceCategory.getVersion()) {
			existingCategory.setVersion(serviceCategory.getVersion());
		}

		if (null != serviceCategory.getValidFor()) {
			existingCategory.setValidFor(serviceCategory.getValidFor());
		}

		if (null != serviceCategory.getLifecycleStatus()) {
			existingCategory.setLifecycleStatus(serviceCategory.getLifecycleStatus());
		}

		if (null != serviceCategory.getParentId()) {
			existingCategory.setParentId(serviceCategory.getParentId());
		}
		
		if(null !=serviceCategory.getBaseType()) {
			existingCategory.setBaseType(serviceCategory.getBaseType());
		}
		
		if (null!=serviceCategory.getIsRoot()) {
			existingCategory.setIsRoot(serviceCategory.getIsRoot());
		}
		
		if (null!=serviceCategory.getServiceCandidate()) {
			existingCategory.setServiceCandidate(serviceCategory.getServiceCandidate());
		}
		
		if(null!=serviceCategory.getCategory()) {
			existingCategory.setCategory(serviceCategory.getCategory());
		}

		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");	
        existingCategory.setLastUpdate(df.format(new Date()));

		return saveCategory(existingCategory);

	}

	private ServiceCategory saveCategory(@Valid ServiceCategory serviceCategory) {
		return categoryRepository.save(serviceCategory);
	}

	private void setDefaultValues(ServiceCategory serviceCategory) {
		if (null == serviceCategory.getType() || serviceCategory.getType().trim().equals("")) {
			serviceCategory.setType("ServiceCategory");
		}
		
		if (null == serviceCategory.getBaseType() || serviceCategory.getBaseType().trim().equals("")) {
			serviceCategory.setBaseType("Category");
		}

		if (null == serviceCategory.getVersion()) {
			serviceCategory.setVersion("1.0");
		}
		
		if (null == serviceCategory.getLifecycleStatus() || serviceCategory.getLifecycleStatus().trim().equals("")) {
			serviceCategory.setLifecycleStatus("In Design");
		}
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");		
		serviceCategory.setLastUpdate(df.format(new Date()));

		/*if (null == serviceCategory.getIsRoot()) {
			serviceCategory.setIsRoot(true);
		}*/
	}

	private ServiceCategory getExistingCategory(@NotNull String id) {
		Optional<ServiceCategory> existingCategoryOption = categoryRepository.findById(id);
		if (!existingCategoryOption.isPresent()) {
			throw new IllegalArgumentException("ServiceCategory with id " + id + " doesnot exists");
		}

		ServiceCategory existingCategory = existingCategoryOption.get();
		return existingCategory;
	}

}
