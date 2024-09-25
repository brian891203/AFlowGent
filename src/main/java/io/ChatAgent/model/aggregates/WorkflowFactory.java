package io.ChatAgent.model.aggregates;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// @FieldDefaults(level = lombok.AccessLevel.PRIVATE)。
public class WorkflowFactory {
    public WorkFlow createWorkFlow() {
        return new WorkFlow();
    }
}
