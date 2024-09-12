package io.csd.cloudtechnology.aflowgent.rest;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.csd.cloudtechnology.aflowgent.dtoMapper.NodeLLMDtoMapper;
import io.csd.cloudtechnology.aflowgent.dtoRequest.CreateLLMRequest;
import io.csd.cloudtechnology.aflowgent.dtoRequest.UpdateLLMRequest;
import io.csd.cloudtechnology.aflowgent.dtoResponse.NodeDto;
import io.csd.cloudtechnology.aflowgent.dtoResponse.NodeLLMDto;
import io.csd.cloudtechnology.aflowgent.dtoResponse.WorkflowContext;
import io.csd.cloudtechnology.aflowgent.interfaces.api.LLMApi;
import io.csd.cloudtechnology.aflowgent.model.Node;
import io.csd.cloudtechnology.aflowgent.model.NodeLLM;
import io.csd.cloudtechnology.aflowgent.service.LLMService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LLMRestApi implements LLMApi {

    private final WorkflowContext workflowContext;
    private final LLMService LLMService;
    private final NodeLLMDtoMapper nodeLLMDtoMapper;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<NodeDto> createLLM(
        @PathVariable("flowId") String flowId,
        @RequestParam("request") String requestJson,
        @RequestParam("file") MultipartFile file) 
    throws Exception {

        CreateLLMRequest request = objectMapper.readValue(requestJson, CreateLLMRequest.class);
        
        if (file != null) {
            workflowContext.setCurrentWorkflowId(flowId);

            NodeLLM createdNodeLLM = LLMService.createNode(
                request, workflowContext.getCurrentWorkflowId(), file);
            NodeLLMDto nodeLLMDto = nodeLLMDtoMapper.toDto(createdNodeLLM);

            return ResponseEntity.status(HttpStatus.CREATED).body(nodeLLMDto);
        }
        workflowContext.setCurrentWorkflowId(flowId);

        NodeLLM createdNodeLLM = LLMService.createNode(request, workflowContext.getCurrentWorkflowId());
        NodeLLMDto nodeLLMDto = nodeLLMDtoMapper.toDto(createdNodeLLM);

        return ResponseEntity.status(HttpStatus.CREATED).body(nodeLLMDto);
    }

    @Override
    public ResponseEntity<NodeDto> getLLMByNodeId(
        @PathVariable("flowId") String flowId,
        @PathVariable("NodeId") String NodeId) 
    throws Exception {

        Optional<Node> Optional = LLMService.getNodeById(NodeId);
        if (Optional.isPresent()) {
            NodeLLM dto = (NodeLLM)Optional.get();
            log.info("Received request with model config: {}", dto.getModelConfig().toString());

            NodeLLMDto nodeLLMDto = nodeLLMDtoMapper.toDto(dto);
            return new ResponseEntity<>(nodeLLMDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<NodeDto> updateLLM(
        @PathVariable("flowId") String flowId, 
        @PathVariable("NodeId") String NodeId, 
        @RequestBody UpdateLLMRequest request) {

        Optional<Node> nodeOpt = LLMService.updateNode(NodeId, request, workflowContext.getCurrentWorkflowId());

        if (nodeOpt.isPresent()) {
            NodeLLMDto nodeLLMDto = nodeLLMDtoMapper.toDto((NodeLLM)nodeOpt.get());
            return ResponseEntity.status(HttpStatus.OK).body(nodeLLMDto);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> deleteLLM(String flowId, String NodeId) throws Exception {
        LLMService.deleteNode(NodeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }    
}
