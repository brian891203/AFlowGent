package io.ChatAgent.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
// import org.hibernate.validator.constraints.UUID;
import org.springframework.stereotype.Service;

import io.ChatAgent.dao.NodeDao;
import io.ChatAgent.dao.WorkFlowDao;
import io.ChatAgent.model.Node;
import io.ChatAgent.model.aggregates.WorkFlow;
import io.ChatAgent.model.aggregates.WorkflowFactory;
import io.ChatAgent.model.aggregates.commands.CreateWorkFlowCommand;
import io.ChatAgent.model.aggregates.commands.UpdateWorkFlowCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkFlowService {

    final WorkflowFactory workflowFactory;
    // final WorkFlowRepository workFlowRepository;

    @Autowired
    WorkFlowDao workflowDao;

    @Autowired
    @Qualifier("NodeDao")
    private NodeDao nodeDao;

    public Mono<WorkFlow> createWorkFlow(CreateWorkFlowCommand command) {
        return Mono.defer(() -> {
            WorkFlow workFlow = workflowFactory.createWorkFlow(); 

            command.setId(StringUtils.lowerCase(RandomStringUtils.randomAlphanumeric(6)));
            workFlow.on(command);
            workflowDao.createWorkFlow(workFlow);
            
            return Mono.just(workFlow);
        });
    }

    public Optional<WorkFlow> getWorkFlowByFlowId(String flowId) {
        log.debug("flowId: {}", flowId);
        WorkFlow workFlow = workflowDao.getWorkFlowById(flowId);
        // log.debug("flowId = {}", flowId);

        return Optional.ofNullable(workFlow); 
    }

    public List<WorkFlow> getAllWorkFlows() {
        log.debug("Fetching all workflows");
        return workflowDao.getAllWorkFlows();  // 從 DAO 獲取所有的工作流
    }

    public WorkFlow updateWorkFlow(UpdateWorkFlowCommand command, WorkFlow workFlow) {

        // workflow.setUpdated_by(request.getUpdatedBy());

        // if (request.getDescription() != null) {
        //     workFlow.setDescription(request.getDescription());
        // }
        // workFlow.setTool_published(request.isToolPublished());

        // workflowDao.updateWorkFlow(workFlow);

        workFlow.on(command);
        workflowDao.updateWorkFlow(workFlow);

        return workFlow;
    }

    public void deleteWorkFlow(String flowId) {
        log.debug("DeleteWorkFlow: {}", flowId);
        workflowDao.deleteWorkFlow(flowId);
    }

    public List<Node> getAllNodesInfoByWorkFlowId(String workFlowId) {
        log.debug("Fetching all nodes for workflow ID: {}", workFlowId);
        return nodeDao.findAllNodesInfoByWorkFlowId(workFlowId);
    }

    public List<Node> getAllNodesByWorkFlowId(String workFlowId) {
        log.debug("Fetching all nodes for workflow ID: {}", workFlowId);
        return nodeDao.findAllNodesByWorkFlowId(workFlowId);
    }
}
