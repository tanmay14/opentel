package org.tmf.openapi.catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.tmf.openapi.catalog.domain.catalog.LogicalResource;
import org.tmf.openapi.catalog.domain.catalog.PhysicalResource;
import org.tmf.openapi.catalog.domain.catalog.Resource;

@Repository
public interface PhysicalResourceRepository extends MongoRepository<PhysicalResource, String>, QuerydslPredicateExecutor<PhysicalResource> {

}



