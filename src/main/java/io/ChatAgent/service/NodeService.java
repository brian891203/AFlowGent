package io.ChatAgent.service;

import java.util.Optional;

import io.ChatAgent.dtoRequest.CreateNodeRequest;
import io.ChatAgent.dtoRequest.UpdateNodeRequest;
import io.ChatAgent.model.Node;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class NodeService {

    // Create a new node
    public abstract Node createNode(CreateNodeRequest request, String workFlowId);

    // Retrieve a node by ID
    public abstract Optional<Node> getNodeById(String id);

    // Update an existing node
    public abstract Optional<Node> updateNode(
        String nodeId, UpdateNodeRequest request, String workFlowId);

    // Delete a node by ID
    public abstract boolean deleteNode(String id);

    // // Retrieve all nodes
    // public abstract List<T> getAllNodes();
}
