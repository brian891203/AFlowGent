package io.csd.cloudtechnology.aflowgent.dtoResponse;

import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import io.csd.cloudtechnology.aflowgent.model.aggregates.WorkFlow;
import io.csd.cloudtechnology.aflowgent.service.WorkFlowService;
import lombok.RequiredArgsConstructor;

// for checking the workflowId provied by WorkflowContext is the singleton in one request (SCOPE_REQUEST)
@Component
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WorkflowContext {

    private final WorkFlowService workflowService;    

    private String currentWorkflowId;

    public void setCurrentWorkflowId(String workflowId) {
        if (workflowExists(workflowId)) {
            this.currentWorkflowId = workflowId;
        } else {
            throw new IllegalArgumentException("Workflow with ID " + workflowId + " not found");
        }
    }

    public String getCurrentWorkflowId() {
        return currentWorkflowId;
    }
    public boolean workflowExists(String workflowId) {
        // 透過 WorkflowService 檢查 workflowId 是否存在
        try {
            Optional<WorkFlow> optional = workflowService.getWorkFlowByFlowId(workflowId);
            return optional.isPresent();
        } catch (Exception e) {
            return false; // 如果發生異常，也視為不存在
        }
    }
}