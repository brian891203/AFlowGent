package io.ChatAgent.interfaces.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.ChatAgent.dtoRequest.CreateQClassifierRequest;
import io.ChatAgent.dtoRequest.UpdateQClassifierRequest;
import io.ChatAgent.dtoResponse.NodeDto;

@RequestMapping("/AflowGent/v1/workflows/{flowId}/QClassifier")
public interface QClassifierApi {

    @PostMapping
    public ResponseEntity<NodeDto> createQClassifier(
        @PathVariable("flowId") String flowId, 
        @RequestBody CreateQClassifierRequest request) throws Exception;

    @GetMapping("/{NodeId}")
    public ResponseEntity<NodeDto> getQClassifierByNodeId(
        @PathVariable("flowId") String flowId, 
        @PathVariable("NodeId") String NodeId) throws Exception;

    @PutMapping("/{NodeId}")
    public ResponseEntity<NodeDto> updateQClassifier(
        @PathVariable("flowId") String flowId, 
        @PathVariable("NodeId") String NodeId, 
        @RequestBody UpdateQClassifierRequest request);

    @DeleteMapping("/{NodeId}")
    public ResponseEntity<Void> deleteQClassifier(
        @PathVariable("flowId") String flowId, 
        @PathVariable("NodeId") String NodeId) throws Exception;
}
