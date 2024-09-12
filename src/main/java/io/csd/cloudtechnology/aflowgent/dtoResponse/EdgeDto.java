package io.csd.cloudtechnology.aflowgent.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EdgeDto {
    private String id;
    private String workflowId;
    private String sourceNodeId;
    private String targetNodeId;
    private String sourceHandle;
    private String targetHandle;
    private String sourceType;
    private String targetType;
}
