package io.csd.cloudtechnology.aflowgent.dtoMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.csd.cloudtechnology.aflowgent.dtoResponse.EdgeDto;
import io.csd.cloudtechnology.aflowgent.model.Edge;

@Mapper(componentModel = "spring")
public interface EdgeDtoMapper {

    EdgeDtoMapper INSTANCE = Mappers.getMapper(EdgeDtoMapper.class);

    // Edge to EdgeDto
    @Mapping(source = "id", target = "id")
    @Mapping(source = "workflowId", target = "workflowId")
    @Mapping(source = "sourceNodeId", target = "sourceNodeId")
    @Mapping(source = "targetNodeId", target = "targetNodeId")
    @Mapping(source = "sourceHandle", target = "sourceHandle")
    @Mapping(source = "targetHandle", target = "targetHandle")
    @Mapping(source = "sourceType", target = "sourceType")
    @Mapping(source = "targetType", target = "targetType")
    EdgeDto toDto(Edge edge);

    // EdgeDto to Edge
    @Mapping(source = "id", target = "id")
    @Mapping(source = "workflowId", target = "workflowId")
    @Mapping(source = "sourceNodeId", target = "sourceNodeId")
    @Mapping(source = "targetNodeId", target = "targetNodeId")
    @Mapping(source = "sourceHandle", target = "sourceHandle")
    @Mapping(source = "targetHandle", target = "targetHandle")
    @Mapping(source = "sourceType", target = "sourceType")
    @Mapping(source = "targetType", target = "targetType")
    Edge toEntity(EdgeDto edgeDto);
}
