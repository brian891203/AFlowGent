package io.csd.cloudtechnology.aflowgent.interfaces.api;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.csd.cloudtechnology.aflowgent.dtoRequest.CreateWorkFlowRequest;
import io.csd.cloudtechnology.aflowgent.dtoRequest.UpdateWorkFlowRequest;
import io.csd.cloudtechnology.aflowgent.dtoResponse.WorkFlowDto;
import io.csd.cloudtechnology.aflowgent.model.Node;

@RequestMapping("/AflowGent/v1/workflows")
public interface WorkFlowApi {

    @PostMapping
    public ResponseEntity<WorkFlowDto> createWorkFlow(CreateWorkFlowRequest request) throws Exception;

    @GetMapping("/{flowId}")
    public ResponseEntity<WorkFlowDto> getWorkFlowByFlowId(@PathVariable("flowId") String flowId) throws Exception;

    @GetMapping
    public ResponseEntity<List<WorkFlowDto>> getAllWorkFlows();

    @PutMapping("/{flowId}")
    public ResponseEntity<WorkFlowDto> updateWorkFlow(
        @PathVariable("flowId") String flowId, 
        @RequestBody UpdateWorkFlowRequest request);

    @DeleteMapping("/{flowId}")
    public ResponseEntity<Void> deleteWorkFlow(@PathVariable("flowId") String flowId) throws Exception;

    @GetMapping("/{flowId}/nodes/info")
    public ResponseEntity<List<Node>> getAllNodesInfoByWorkFlowId(@PathVariable("flowId") String flowId);

    @GetMapping("/{flowId}/nodes")
    public ResponseEntity<List<Map<String, Object>>> getAllNodesByWorkFlowId(@PathVariable("flowId") String flowId);
}
