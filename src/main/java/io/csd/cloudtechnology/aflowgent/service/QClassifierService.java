package io.csd.cloudtechnology.aflowgent.service;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.csd.cloudtechnology.aflowgent.dao.NodeDao;
import io.csd.cloudtechnology.aflowgent.dtoRequest.CreateNodeRequest;
import io.csd.cloudtechnology.aflowgent.dtoRequest.UpdateNodeRequest;
import io.csd.cloudtechnology.aflowgent.dtoRequest.UpdateQClassifierRequest;
import io.csd.cloudtechnology.aflowgent.model.Node;
import io.csd.cloudtechnology.aflowgent.model.NodeClassifier;
import io.csd.cloudtechnology.aflowgent.model.factory.NodeFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class QClassifierService extends NodeService {

    @Autowired
    @Qualifier("QClassifierDao")
    private NodeDao nodeDao;

    final NodeFactory nodeFactory;

    @Override
    public NodeClassifier createNode(CreateNodeRequest request, String workFlowId) {

        NodeClassifier node = (NodeClassifier)NodeFactory.createNode(request, workFlowId);

        nodeDao.createNode(node);
        log.info("Created NodeClassifier: {}", node);
        return node;
    }

    @Override
    public Optional<Node> getNodeById(String id) {
        return nodeDao.getNodeById(id);
    }

    @Override
    public Optional<Node> updateNode(String nodeId, UpdateNodeRequest request, String workFlowId) {
        Optional<Node> existingNodeOpt = getNodeById(nodeId);
        if (existingNodeOpt.isPresent()) {
            NodeClassifier existingNode = (NodeClassifier) existingNodeOpt.get();

            if (request.getTitle() != null) {
                existingNode.setTitle(request.getTitle());
            }
            if (request.getDescription() != null) {
                existingNode.setDescription(request.getDescription());
            }
            if (request.getPositionX() != null) {
                existingNode.setPositionX(request.getPositionX());
            }
            if (request.getPositionY() != null) {
                existingNode.setPositionY(request.getPositionY());
            }
            if (request.getParentId() != null) {
                existingNode.setParentId(request.getParentId());
            }
            UpdateQClassifierRequest qClassifierRequest = (UpdateQClassifierRequest) request;
            if (qClassifierRequest.getClasses() != null) {
                existingNode.setClasses(new JSONObject(qClassifierRequest.getClasses()));
            }

            nodeDao.updateNode(existingNode);

            log.info("Updated NodeClassifier: {}", existingNode);
            return Optional.of(existingNode);
        } else {
            log.warn("NodeClassifier with ID {} not found", nodeId);
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteNode(String id) {
        Optional<Node> nodeOpt = getNodeById(id);
        if (nodeOpt.isPresent()) {
            nodeDao.deleteNode(id);
            log.info("Deleted NodeClassifier with ID: {}", id);
            return true;
        } else {
            log.warn("NodeClassifier with ID {} not found", id);
            return false;
        }
    }

    // @Override
    // public List<NodeClassifier> getAllNodes() {
    //     return nodeDao.getAllNodes();
    // }
}
