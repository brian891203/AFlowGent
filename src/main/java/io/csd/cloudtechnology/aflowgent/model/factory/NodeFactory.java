package io.csd.cloudtechnology.aflowgent.model.factory;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import io.csd.cloudtechnology.aflowgent.dtoRequest.CreateLLMRequest;
import io.csd.cloudtechnology.aflowgent.dtoRequest.CreateNodeRequest;
import io.csd.cloudtechnology.aflowgent.dtoRequest.CreateQClassifierRequest;
import io.csd.cloudtechnology.aflowgent.model.Node;
import io.csd.cloudtechnology.aflowgent.model.NodeClassifier;
import io.csd.cloudtechnology.aflowgent.model.NodeLLM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NodeFactory {

    public static Node createNode(CreateNodeRequest request, String workFlowId) {
        if (request instanceof CreateQClassifierRequest) {
            return createQClassifierNode((CreateQClassifierRequest) request, workFlowId);
        } else if (request instanceof CreateLLMRequest) {
            return createLLMNode((CreateLLMRequest) request, workFlowId);
        }
        
        throw new IllegalArgumentException("Unsupported request type: " + request.getClass().getName());
    }

    private static NodeClassifier createQClassifierNode(CreateQClassifierRequest request, String workFlowId) {
        NodeClassifier node = new NodeClassifier();
        node.setId(StringUtils.lowerCase(RandomStringUtils.randomAlphanumeric(6)));

        node.setTitle(request.getTitle());

        node.setWorkflowId(workFlowId);
        node.setParentId(request.getParentId());

        node.setType(node.getClass().getSimpleName());
        node.setDescription(request.getDescription());
        node.setPositionX(request.getPositionX());
        node.setPositionY(request.getPositionY());
        // node.setClasses(request.getClasses());

        if (request.getClasses() != null) {
            node.setClasses(new JSONObject(request.getClasses()));
        }
        
        // node.setQueryVariableSelector(request.getQueryVariableSelector());

        log.info("Node created with classes: {}", node.getClasses().toString());

        return node;
    }

    private static NodeLLM createLLMNode(CreateLLMRequest request, String workFlowId) {
        NodeLLM node = new NodeLLM();
        node.setId(StringUtils.lowerCase(RandomStringUtils.randomAlphanumeric(6)));

        node.setTitle(request.getTitle());

        node.setWorkflowId(workFlowId);
        node.setParentId(request.getParentId());

        node.setType(node.getClass().getSimpleName());
        node.setDescription(request.getDescription());
        node.setPositionX(request.getPositionX());
        node.setPositionY(request.getPositionY());

        node.setModelConfig(new JSONObject(request.getModelConfig()));
        node.setMemory(new JSONObject(request.getMemory()));
        node.setPromptTemplate(new JSONObject(request.getPromptTemplate()));
        node.setContext(new JSONObject(request.getContext()));
        node.setVision(new JSONObject(request.getVision()));

        // if (request.getModelConfig() != null) {
        //     node.setModelConfig(new JSONObject(request.getModelConfig()));
        // }

        // if (request.getMemory() != null) {
        //     node.setMemory(new JSONObject(request.getMemory()));
        // }

        // if (request.getPromptTemplate() != null) {
        //     node.setPromptTemplate(new JSONObject(request.getPromptTemplate()));
        // }

        // if (request.getContext() != null) {
        //     node.setContext(new JSONObject(request.getContext()));
        // }

        // if (request.getVision() != null) {
        //     node.setVision(new JSONObject(request.getVision()));
        // }

        log.info("NodeLLM created with modelConfig: {}", node.getModelConfig().toString());

        return node;
    }
}
