package com.hw.hwjobbackend.repository.file;

import com.hw.hwjobbackend.model.entity.file.FileMgmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileMgmtRepository extends JpaRepository<FileMgmt, String> {
    Optional<FileMgmt> findByUrl(String url);
}
