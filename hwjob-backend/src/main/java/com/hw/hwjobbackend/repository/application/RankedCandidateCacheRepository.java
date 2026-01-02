package com.hw.hwjobbackend.repository.application;

import com.hw.hwjobbackend.model.entity.application.RankedCandidateCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankedCandidateCacheRepository extends CrudRepository<RankedCandidateCache, String> {
}
