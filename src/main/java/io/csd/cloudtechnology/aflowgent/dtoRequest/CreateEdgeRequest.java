package io.csd.cloudtechnology.aflowgent.dtoRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEdgeRequest {
    
    @NotNull(message = "sourceNodeId is required")
    private String sourceNodeId;

    @NotNull(message = "targetNodeId is required")
    private String targetNodeId;
    private String sourceHandle;
    private String targetHandle;
    private String sourceType;
    private String targetType;
}
