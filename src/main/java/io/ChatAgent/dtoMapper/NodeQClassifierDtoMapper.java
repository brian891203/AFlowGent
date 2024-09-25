package io.ChatAgent.dtoMapper;

import java.util.Map;

import org.json.JSONObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import io.ChatAgent.dtoResponse.NodeQClassifierDto;
import io.ChatAgent.model.NodeClassifier;

@Mapper(componentModel = "spring")
public interface NodeQClassifierDtoMapper {

    NodeQClassifierDtoMapper INSTANCE = Mappers.getMapper(NodeQClassifierDtoMapper.class);

    // NodeClassifier to NodeQClassifierDto
    @Mapping(source = "id", target = "id")
    @Mapping(source = "workflowId", target = "workflowId")
    @Mapping(source = "parentId", target = "parentId")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "positionX", target = "positionX")
    @Mapping(source = "positionY", target = "positionY")
    @Mapping(source = "classes", target = "classes", qualifiedByName = "mapClassesToMap")
    // @Mapping(source = "queryVariableSelector", target = "queryVariableSelector")
    NodeQClassifierDto toDto(NodeClassifier nodeClassifier);

    // NodeQClassifierDto to NodeClassifier
    @Mapping(source = "id", target = "id")
    @Mapping(source = "workflowId", target = "workflowId")
    @Mapping(source = "parentId", target = "parentId")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "positionX", target = "positionX")
    @Mapping(source = "positionY", target = "positionY")
    @Mapping(source = "classes", target = "classes", qualifiedByName = "mapMapToClasses")
    // @Mapping(source = "queryVariableSelector", target = "queryVariableSelector")
    NodeClassifier toEntity(NodeQClassifierDto dto);

     // 自定義方法：將 JSONObject 轉換為 Map<String, Object>
     @Named("mapClassesToMap")
     default Map<String, Object> mapClassesToMap(JSONObject classesJson) {
         return classesJson != null ? classesJson.toMap() : null;
     }

     // 自定義方法：將 Map<String, Object> 轉換為 JSONObject
    @Named("mapMapToClasses")
    default JSONObject mapMapToClasses(Map<String, Object> classesMap) {
        return classesMap != null ? new JSONObject(classesMap) : new JSONObject();
    }
}
