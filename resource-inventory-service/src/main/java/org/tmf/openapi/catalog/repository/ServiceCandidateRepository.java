package org.tmf.openapi.catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.tmf.openapi.catalog.domain.ServiceCandidate;

@Repository
public interface ServiceCandidateRepository
		extends MongoRepository<ServiceCandidate, String>, QuerydslPredicateExecutor<ServiceCandidate> {

}
