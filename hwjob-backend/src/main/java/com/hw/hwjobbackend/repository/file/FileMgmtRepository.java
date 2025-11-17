package com.hw.hwjobbackend.repository.file;

import com.hw.hwjobbackend.entity.FileMgmt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileMgmtRepository extends MongoRepository<FileMgmt, String> {
    Optional<FileMgmt> findByUrl(String url);
}
