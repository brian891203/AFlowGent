package io.csd.cloudtechnology.aflowgent.model.factory;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import io.csd.cloudtechnology.aflowgent.dtoRequest.CreateEdgeRequest;
import io.csd.cloudtechnology.aflowgent.model.Edge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EdgeFactory {

    // private final NodeService nodeService;
    // private final Map<String, NodeService> nodeServices;

    public Edge createEdge(CreateEdgeRequest request, String workFlowId) {
        if (request instanceof CreateEdgeRequest) {
            return createEdgeEntity(request, workFlowId);
        }
        
        throw new IllegalArgumentException("Unsupported request type: " + request.getClass().getName());
    }

    private Edge createEdgeEntity(CreateEdgeRequest request, String workFlowId) {
        Edge edge = new Edge();
        edge.setId(StringUtils.lowerCase(RandomStringUtils.randomAlphanumeric(6)));
        edge.setWorkflowId(workFlowId);
        edge.setSourceNodeId(request.getSourceNodeId());
        edge.setTargetNodeId(request.getTargetNodeId());
        edge.setSourceHandle(request.getSourceHandle());
        edge.setTargetHandle(request.getTargetHandle());
        
        // NodeService sourceService = nodeServices.get("QClassifierService");
        // Optional<Node> sourcrOptional = sourceService.getNodeById(request.getSourceNodeId());
        // if (sourcrOptional.isPresent()) {
        //     Node sourceNode = sourcrOptional.get();
        //     edge.setSourceType(sourceNode.getClass().getSimpleName());
        // }

        // if (request.getTargetNodeId() != null) {
        //     Optional<Node> targetOptional = nodeService.getNodeById(request.getTargetNodeId());
        //     if (targetOptional.isPresent()) {
        //         Node targetNode = targetOptional.get();
        //         edge.setTargetType(targetNode.getClass().getSimpleName());
        //     } else {
        //         edge.setTargetType(request.getTargetType());
        //     }
        // } else {
        //     edge.setTargetType(request.getTargetType());
        // }

        return edge;
    }
}
