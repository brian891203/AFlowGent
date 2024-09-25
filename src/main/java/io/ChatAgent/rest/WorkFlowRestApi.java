package io.ChatAgent.rest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.ChatAgent.dtoMapper.WorkFlowDtoMapper;
import io.ChatAgent.dtoRequest.CreateWorkFlowRequest;
import io.ChatAgent.dtoRequest.UpdateWorkFlowRequest;
import io.ChatAgent.dtoResponse.WorkFlowDto;
import io.ChatAgent.interfaces.api.WorkFlowApi;
import io.ChatAgent.model.Node;
import io.ChatAgent.model.NodeClassifier;
import io.ChatAgent.model.NodeLLM;
import io.ChatAgent.model.aggregates.WorkFlow;
import io.ChatAgent.model.aggregates.commands.CreateWorkFlowCommand;
import io.ChatAgent.model.aggregates.commands.UpdateWorkFlowCommand;
import io.ChatAgent.service.WorkFlowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class WorkFlowRestApi implements WorkFlowApi{

    @Autowired
    WorkFlowService workflowService;

    @Autowired
    WorkFlowDtoMapper workFlowDtoMapper;

    @Override
    public ResponseEntity<WorkFlowDto> createWorkFlow(@RequestBody @Valid CreateWorkFlowRequest request) 
    throws Exception {
        // System.out.println("createWorkFlow");
        CreateWorkFlowCommand createWorkflowCommand = workFlowDtoMapper.toCreateWorkflowCommand(request);
    
        log.info("createWorkflowCommand: {}", createWorkflowCommand);
        WorkFlow createdWorkFlow = workflowService.createWorkFlow(createWorkflowCommand).block();
        WorkFlowDto responseDto = workFlowDtoMapper.toDto(createdWorkFlow);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Override
    public ResponseEntity<WorkFlowDto> getWorkFlowByFlowId(String flowId) throws Exception {
        Optional<WorkFlow> Optional = workflowService.getWorkFlowByFlowId(flowId);
        if (Optional.isPresent()) {
            WorkFlowDto Dto = workFlowDtoMapper.toDto(Optional.get());
            return new ResponseEntity<>(Dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<WorkFlowDto> updateWorkFlow(
        @PathVariable("flowId") String flowId,
        @RequestBody @Valid UpdateWorkFlowRequest request) {

        Optional<WorkFlow> Optional = workflowService.getWorkFlowByFlowId(flowId);

        if (Optional.isPresent()) {
            UpdateWorkFlowCommand updateWorkFlowCommand = workFlowDtoMapper.toUpdateWorkFlowCommand(request);
            WorkFlow updatedWorkFlow = workflowService.updateWorkFlow(updateWorkFlowCommand, Optional.get());
            WorkFlowDto responseDto = workFlowDtoMapper.toDto(updatedWorkFlow);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<WorkFlowDto>> getAllWorkFlows() {
        List<WorkFlow> workflows = workflowService.getAllWorkFlows();
        List<WorkFlowDto> workflowDtos = workflows.stream()
            .map(workFlowDtoMapper::toDto)
            .collect(Collectors.toList());

        return new ResponseEntity<>(workflowDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteWorkFlow(String flowId) throws Exception {
        workflowService.deleteWorkFlow(flowId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<Node>> getAllNodesInfoByWorkFlowId(@PathVariable("flowId") String flowId) {
        log.info("Fetching all node IDs for workflow ID: {}", flowId);
        List<Node> nodeInfo = workflowService.getAllNodesInfoByWorkFlowId(flowId);
    
        if (nodeInfo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    
        return new ResponseEntity<>(nodeInfo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Map<String, Object>>> getAllNodesByWorkFlowId(
        @PathVariable("flowId") String flowId) {
        List<Node> nodes = workflowService.getAllNodesByWorkFlowId(flowId);
        
        List<Map<String, Object>> response = nodes.stream().map(node -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", node.getId());
            map.put("workflowId", node.getWorkflowId());
            map.put("parentId", node.getParentId());
            map.put("title", node.getTitle());
            map.put("type", node.getType());
            map.put("description", node.getDescription());
            map.put("positionX", node.getPositionX());
            map.put("positionY", node.getPositionY());

            if (node instanceof NodeClassifier) {
                NodeClassifier classifier = (NodeClassifier) node;
                Map<String, Object> sortedClasses = new LinkedHashMap<>();
                sortedClasses.put("category_1", classifier.getClasses().get("category_1"));
                sortedClasses.put("category_2", classifier.getClasses().get("category_2"));
                map.put("classes", sortedClasses);
            } else if (node instanceof NodeLLM) {
                NodeLLM llm = (NodeLLM) node;
                map.put("modelConfig", llm.getModelConfig().toMap());
                map.put("memory", llm.getMemory().toMap());
                map.put("promptTemplate", llm.getPromptTemplate().toMap());
                map.put("context", llm.getContext().toMap());
                map.put("vision", llm.getVision().toMap());
                map.put("vectorStoreId", llm.getVectorStoreId());
            }

            return map;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
