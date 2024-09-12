package io.csd.cloudtechnology.aflowgent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {
    private String id;
    private String workflowId;
    private String sourceNodeId;
    private String targetNodeId;
    private String sourceHandle;
    private String targetHandle;
    private String sourceType;
    private String targetType;
}
