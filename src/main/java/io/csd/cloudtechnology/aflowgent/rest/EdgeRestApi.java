package io.csd.cloudtechnology.aflowgent.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.csd.cloudtechnology.aflowgent.dtoMapper.EdgeDtoMapper;
import io.csd.cloudtechnology.aflowgent.dtoRequest.CreateEdgeRequest;
import io.csd.cloudtechnology.aflowgent.dtoRequest.UpdateEdgeRequest;
import io.csd.cloudtechnology.aflowgent.dtoResponse.EdgeDto;
import io.csd.cloudtechnology.aflowgent.interfaces.api.EdgeApi;
import io.csd.cloudtechnology.aflowgent.model.Edge;
import io.csd.cloudtechnology.aflowgent.service.EdgeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EdgeRestApi implements EdgeApi {

    private final EdgeService edgeService;
    private final EdgeDtoMapper edgeDtoMapper;

    @Override
    public ResponseEntity<EdgeDto> createEdge(
        @PathVariable("flowId") String flowId, 
        @RequestBody @Valid CreateEdgeRequest request) {

        Edge createdEdge = edgeService.createEdge(request, flowId);
        EdgeDto edgeDto = edgeDtoMapper.toDto(createdEdge);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(edgeDto);
    }

    @Override
    public ResponseEntity<EdgeDto> getEdgeById(
        @PathVariable("flowId") String flowId,
        @PathVariable("edgeId") String edgeId) {

        Optional<Edge> edgeOpt = edgeService.getEdgeById(edgeId);
        
        if (edgeOpt.isPresent()) {
            EdgeDto edgeDto = edgeDtoMapper.toDto(edgeOpt.get());
            return ResponseEntity.ok(edgeDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<List<EdgeDto>> getAllEdges(
        @PathVariable("flowId") String flowId) {

        List<Edge> edges = edgeService.getAllEdgesByWorkflowId(flowId);
        List<EdgeDto> edgeDtos = edges.stream()
            .map(edgeDtoMapper::toDto)
            .collect(Collectors.toList());

        return ResponseEntity.ok(edgeDtos);
    }

    @Override
    public ResponseEntity<EdgeDto> updateEdge(
        @PathVariable("flowId") String flowId, 
        @PathVariable("edgeId") String edgeId, 
        @RequestBody @Valid UpdateEdgeRequest request) {

        Optional<Edge> updatedEdgeOpt = edgeService.updateEdge(edgeId, request, flowId);

        if (updatedEdgeOpt.isPresent()) {
            EdgeDto edgeDto = edgeDtoMapper.toDto(updatedEdgeOpt.get());
            return ResponseEntity.ok(edgeDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteEdge(
        @PathVariable("flowId") String flowId,
        @PathVariable("edgeId") String edgeId) {

        boolean deleted = edgeService.deleteEdge(edgeId);
        
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
