package io.csd.cloudtechnology.aflowgent.interfaces.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.csd.cloudtechnology.aflowgent.dtoRequest.CreateEdgeRequest;
import io.csd.cloudtechnology.aflowgent.dtoRequest.UpdateEdgeRequest;
import io.csd.cloudtechnology.aflowgent.dtoResponse.EdgeDto;
import jakarta.validation.Valid;

@RequestMapping("/AflowGent/v1/workflows/{flowId}/edges")
public interface EdgeApi {

    @PostMapping
    ResponseEntity<EdgeDto> createEdge(
        @PathVariable("flowId") String flowId, 
        @RequestBody CreateEdgeRequest request);

    @GetMapping("/{edgeId}")
    ResponseEntity<EdgeDto> getEdgeById(
        @PathVariable("flowId") String flowId, 
        @PathVariable("edgeId") String edgeId);

    @GetMapping
    ResponseEntity<List<EdgeDto>> getAllEdges(@PathVariable("flowId") String flowId);

    @PutMapping("/{edgeId}")
    ResponseEntity<EdgeDto> updateEdge(
        @PathVariable("flowId") String flowId, 
        @PathVariable("edgeId") String edgeId, 
        @RequestBody @Valid UpdateEdgeRequest request);

    @DeleteMapping("/{edgeId}")
    ResponseEntity<Void> deleteEdge(
        @PathVariable("flowId") String flowId, 
        @PathVariable("edgeId") String edgeId);
}
