package io.ChatAgent.rest;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.ChatAgent.dtoMapper.NodeQClassifierDtoMapper;
import io.ChatAgent.dtoRequest.CreateQClassifierRequest;
import io.ChatAgent.dtoRequest.UpdateQClassifierRequest;
import io.ChatAgent.dtoResponse.NodeDto;
import io.ChatAgent.dtoResponse.NodeQClassifierDto;
import io.ChatAgent.dtoResponse.WorkflowContext;
import io.ChatAgent.interfaces.api.QClassifierApi;
import io.ChatAgent.model.Node;
import io.ChatAgent.model.NodeClassifier;
import io.ChatAgent.service.QClassifierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
// @FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class QClassifierRestApi implements QClassifierApi{

    private final WorkflowContext workflowContext;
    private final QClassifierService qClassifierService;
    private final NodeQClassifierDtoMapper nodeQClassifierDtoMapper;

    @Override
    public ResponseEntity<NodeDto> createQClassifier(
        @PathVariable("flowId") String flowId,
        @RequestBody @Valid CreateQClassifierRequest request) 
    throws Exception {

        workflowContext.setCurrentWorkflowId(flowId);

        NodeClassifier createdNodeQClassifier = qClassifierService.createNode(request, workflowContext.getCurrentWorkflowId());
        NodeQClassifierDto nodeQClassifierDto = nodeQClassifierDtoMapper.toDto(createdNodeQClassifier);

        return ResponseEntity.status(HttpStatus.CREATED).body(nodeQClassifierDto);
    }

    @Override
    public ResponseEntity<NodeDto> getQClassifierByNodeId(
        @PathVariable("flowId") String flowId,
        @PathVariable("NodeId") String NodeId) 
    throws Exception {

        Optional<Node> Optional = qClassifierService.getNodeById(NodeId);
        if (Optional.isPresent()) {
            NodeClassifier dto = (NodeClassifier)Optional.get();
            log.info("Received request with classes: {}", dto.getClasses().toString());

            NodeQClassifierDto nodeQClassifierDto = nodeQClassifierDtoMapper.toDto(dto);
            return new ResponseEntity<>(nodeQClassifierDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<NodeDto> updateQClassifier(
        @PathVariable("flowId") String flowId, 
        @PathVariable("NodeId") String NodeId, 
        @RequestBody UpdateQClassifierRequest request) {

        Optional<Node> nodeOpt = qClassifierService.updateNode(NodeId, request, workflowContext.getCurrentWorkflowId());

        if (nodeOpt.isPresent()) {
            NodeQClassifierDto nodeQClassifierDto = nodeQClassifierDtoMapper.toDto((NodeClassifier)nodeOpt.get());
            return ResponseEntity.status(HttpStatus.OK).body(nodeQClassifierDto);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> deleteQClassifier(String flowId, String NodeId) throws Exception {
        qClassifierService.deleteNode(NodeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }    
}
