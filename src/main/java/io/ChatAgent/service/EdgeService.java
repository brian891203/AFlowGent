package io.ChatAgent.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.ChatAgent.dao.EdgeDao;
import io.ChatAgent.dao.NodeDao;
import io.ChatAgent.dtoRequest.CreateEdgeRequest;
import io.ChatAgent.dtoRequest.UpdateEdgeRequest;
import io.ChatAgent.model.Edge;
import io.ChatAgent.model.Node;
import io.ChatAgent.model.factory.EdgeFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EdgeService {

    final private EdgeDao edgeDao;
    final private EdgeFactory edgeFactory;

    @Autowired
    @Qualifier("NodeDao")
    private NodeDao nodeDao;

    public Edge createEdge(CreateEdgeRequest request, String workFlowId) {

        if (request.getSourceNodeId() == null || request.getTargetNodeId() == null) {
            throw new IllegalArgumentException("SourceNodeId and TargetNodeId cannot be null");
        }

        Edge edge = edgeFactory.createEdge(request, workFlowId);
        edgeDao.createEdge(edge);
        log.info("Created Edge: {}", edge);

        Node targetNode = nodeDao.findNodeById(request.getTargetNodeId());
        if (targetNode != null) {
            targetNode.setParentId(request.getSourceNodeId());
            nodeDao.updateNode(targetNode);
            log.info("Updated Node {}: set parentId to {}", targetNode.getId(), request.getSourceNodeId());
        } else {
            throw new IllegalArgumentException("Target Node not found for ID: " + request.getTargetNodeId());
        }

        return edge;
    }

    // Retrieve an Edge by ID
    public Optional<Edge> getEdgeById(String edgeId) {
        return edgeDao.getEdgeById(edgeId);
    }

    public List<Edge> getAllEdgesByWorkflowId(String workflowId) {
        log.debug("Fetching all edges for workflow ID: {}", workflowId);
        return edgeDao.findAllEdgesByWorkflowId(workflowId);
    }

    public Optional<Edge> updateEdge(String edgeId, UpdateEdgeRequest request, String workFlowId) {
        log.debug("Updating edge with ID: {}", edgeId);
        Optional<Edge> edgeOpt = edgeDao.findEdgeById(edgeId);
    
        if (edgeOpt.isPresent()) {
            Edge edge = edgeOpt.get();
    
            if (request.getTargetNodeId() != null) {
                edge.setTargetNodeId(request.getTargetNodeId());
            }
            if (request.getTargetHandle() != null) {
                edge.setTargetHandle(request.getTargetHandle());
            }
            if (request.getTargetType() != null) {
                edge.setTargetType(request.getTargetType());
            }
    
            edge.setWorkflowId(workFlowId);  // Updating workflow ID
    
            edgeDao.updateEdge(edge);
            log.info("Updated Edge: {}", edge);
            return Optional.of(edge);
        } else {
            log.warn("Edge not found with ID: {}", edgeId);
            return Optional.empty();
        }
    }

    public boolean deleteEdge(String edgeId) {
        log.debug("Deleting edge with ID: {}", edgeId);
        Optional<Edge> edgeOpt = edgeDao.findEdgeById(edgeId);

        if (edgeOpt.isPresent()) {
            edgeDao.deleteEdge(edgeId);
            log.info("Deleted Edge with ID: {}", edgeId);
            return true;
        } else {
            log.warn("Edge not found with ID: {}", edgeId);
            return false;
        }
    }
}