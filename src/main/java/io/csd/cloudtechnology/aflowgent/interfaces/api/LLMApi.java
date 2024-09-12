package io.csd.cloudtechnology.aflowgent.interfaces.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.csd.cloudtechnology.aflowgent.dtoRequest.UpdateLLMRequest;
import io.csd.cloudtechnology.aflowgent.dtoResponse.NodeDto;

@RequestMapping("/AflowGent/v1/workflows/{flowId}/LLM")
public interface LLMApi {

    @PostMapping
    public ResponseEntity<NodeDto> createLLM(
        @PathVariable("flowId") String flowId, 
        @RequestParam("request") String requestJson,
        @RequestParam("file") MultipartFile file) throws Exception;

    @GetMapping("/{NodeId}")
    public ResponseEntity<NodeDto> getLLMByNodeId(
        @PathVariable("flowId") String flowId, 
        @PathVariable("NodeId") String NodeId) throws Exception;

    @PutMapping("/{NodeId}")
    public ResponseEntity<NodeDto> updateLLM(
        @PathVariable("flowId") String flowId, 
        @PathVariable("NodeId") String NodeId, 
        @RequestBody UpdateLLMRequest request);

    @DeleteMapping("/{NodeId}")
    public ResponseEntity<Void> deleteLLM(
        @PathVariable("flowId") String flowId, 
        @PathVariable("NodeId") String NodeId) throws Exception;
}
