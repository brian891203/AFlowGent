package io.csd.cloudtechnology.aflowgent.dtoMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.csd.cloudtechnology.aflowgent.dtoResponse.KMDtoResponse;
import io.csd.cloudtechnology.aflowgent.model.KM;

@Mapper(componentModel = "spring")
public interface KMDtoMapper {

    KMDtoMapper INSTANCE = Mappers.getMapper(KMDtoMapper.class);

    // KM to KMDtoResponse
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fileName", target = "fileName")
    @Mapping(source = "fileType", target = "fileType")
    @Mapping(source = "uploadedAt", target = "uploadedAt")
    @Mapping(source = "uploadedBy", target = "uploadedBy")
    @Mapping(source = "vectorStoreId", target = "vectorStoreId")
    @Mapping(source = "systemPrompt", target = "systemPrompt")
    KMDtoResponse toDto(KM km);

    // KMDtoResponse to KM
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fileName", target = "fileName")
    @Mapping(source = "fileType", target = "fileType")
    @Mapping(source = "uploadedAt", target = "uploadedAt")
    @Mapping(source = "uploadedBy", target = "uploadedBy")
    @Mapping(source = "vectorStoreId", target = "vectorStoreId")
    @Mapping(source = "systemPrompt", target = "systemPrompt")
    KM toEntity(KMDtoResponse dto);
}
