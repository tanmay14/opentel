package org.tmf.openapi.servicecatalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.tmf.openapi.servicecatalog.domain.catalog.ServiceCategory;

@Repository
public interface CategoryRepository extends MongoRepository<ServiceCategory, String>, QuerydslPredicateExecutor<ServiceCategory> {

}



