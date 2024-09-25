package io.ChatAgent.dtoRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWorkFlowRequest {
    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message = "Created by is required")
    private String createdBy;

    private boolean toolPublished;
}
