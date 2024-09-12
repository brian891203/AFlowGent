package io.csd.cloudtechnology.aflowgent.dtoRequest;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWorkFlowRequest {

    @NotEmpty(message = "UpdatedBy is required")
    private String updatedBy;

    private String description;
    private Boolean toolPublished;
}
