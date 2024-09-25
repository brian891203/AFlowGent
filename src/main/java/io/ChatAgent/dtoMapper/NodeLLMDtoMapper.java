package io.ChatAgent.dtoMapper;

import java.util.Map;

import org.json.JSONObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import io.ChatAgent.dtoResponse.NodeLLMDto;
import io.ChatAgent.model.NodeLLM;

@Mapper(componentModel = "spring")
public interface NodeLLMDtoMapper {

    NodeLLMDtoMapper INSTANCE = Mappers.getMapper(NodeLLMDtoMapper.class);

    // NodeLLM to NodeLLMDto
    @Mapping(source = "id", target = "id")
    @Mapping(source = "workflowId", target = "workflowId")
    @Mapping(source = "parentId", target = "parentId")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "positionX", target = "positionX")
    @Mapping(source = "positionY", target = "positionY")
    @Mapping(source = "modelConfig", target = "modelConfig", qualifiedByName = "mapJsonToMap")
    @Mapping(source = "memory", target = "memory", qualifiedByName = "mapJsonToMap")
    @Mapping(source = "promptTemplate", target = "promptTemplate", qualifiedByName = "mapJsonToMap")
    @Mapping(source = "context", target = "context", qualifiedByName = "mapJsonToMap")
    @Mapping(source = "vision", target = "vision", qualifiedByName = "mapJsonToMap")
    @Mapping(source = "vectorStoreId", target = "vectorStoreId")
    NodeLLMDto toDto(NodeLLM nodeLLM);

    // NodeLLMDto to NodeLLM
    @Mapping(source = "id", target = "id")
    @Mapping(source = "workflowId", target = "workflowId")
    @Mapping(source = "parentId", target = "parentId")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "positionX", target = "positionX")
    @Mapping(source = "positionY", target = "positionY")
    @Mapping(source = "modelConfig", target = "modelConfig", qualifiedByName = "mapMapToJson")
    @Mapping(source = "memory", target = "memory", qualifiedByName = "mapMapToJson")
    @Mapping(source = "promptTemplate", target = "promptTemplate", qualifiedByName = "mapMapToJson")
    @Mapping(source = "context", target = "context", qualifiedByName = "mapMapToJson")
    @Mapping(source = "vision", target = "vision", qualifiedByName = "mapMapToJson")
    NodeLLM toEntity(NodeLLMDto dto);

    // Custom method: Convert JSONObject to Map<String, Object>
    @Named("mapJsonToMap")
    default Map<String, Object> mapJsonToMap(JSONObject jsonObject) {
        return jsonObject != null ? jsonObject.toMap() : null;
    }

    // Custom method: Convert Map<String, Object> to JSONObject
    @Named("mapMapToJson")
    default JSONObject mapMapToJson(Map<String, Object> map) {
        return map != null ? new JSONObject(map) : new JSONObject();
    }
}
