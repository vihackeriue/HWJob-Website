package com.hw.hwjobbackend.mapper.file;

import com.hw.hwjobbackend.dto.file.FileInfo;
import com.hw.hwjobbackend.entity.FileMgmt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileMgmtMapper {
    @Mapping(target = "id", source = "name")
    @Mapping(target = "url", source = "url")
    FileMgmt toFileMgmt(FileInfo fileInfo);
}
