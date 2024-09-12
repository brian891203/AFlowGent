package io.csd.cloudtechnology.aflowgent.dtoMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.csd.cloudtechnology.aflowgent.dtoRequest.CreateWorkFlowRequest;
import io.csd.cloudtechnology.aflowgent.dtoRequest.UpdateWorkFlowRequest;
import io.csd.cloudtechnology.aflowgent.dtoResponse.WorkFlowDto;
import io.csd.cloudtechnology.aflowgent.model.aggregates.WorkFlow;
import io.csd.cloudtechnology.aflowgent.model.aggregates.commands.CreateWorkFlowCommand;
import io.csd.cloudtechnology.aflowgent.model.aggregates.commands.UpdateWorkFlowCommand;

@Mapper(componentModel = "spring")
public interface WorkFlowDtoMapper {

    WorkFlowDtoMapper INSTANCE = Mappers.getMapper(WorkFlowDtoMapper.class);

    @Mapping(source = "created_by", target = "createdBy")
    @Mapping(source = "updated_by", target = "updatedBy")   
    @Mapping(source = "created_at", target = "createdAt")
    @Mapping(source = "updated_at", target = "updatedAt")
    @Mapping(source = "tool_published", target = "toolPublished")
    @Mapping(source = "description", target = "description")
    WorkFlowDto toDto(WorkFlow workFlow);  // WorkFlow to WorkFlowDto

    @Mapping(source = "createdBy", target = "created_by")
    @Mapping(source = "updatedBy", target = "updated_by")
    @Mapping(source = "createdAt", target = "created_at")
    @Mapping(source = "updatedAt", target = "updated_at")
    @Mapping(source = "toolPublished", target = "tool_published")
    @Mapping(source = "description", target = "description")
    WorkFlow toEntity(WorkFlowDto workFlowDto); // WorkFlowDto to WorkFlow entity

    @Mapping(source = "description", target = "description")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "toolPublished", target = "toolPublished")
    public CreateWorkFlowCommand toCreateWorkflowCommand(
        CreateWorkFlowRequest request
    );

    @Mapping(source = "description", target = "description")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "toolPublished", target = "toolPublished")
    public UpdateWorkFlowCommand toUpdateWorkFlowCommand(
        UpdateWorkFlowRequest request
    );
}
