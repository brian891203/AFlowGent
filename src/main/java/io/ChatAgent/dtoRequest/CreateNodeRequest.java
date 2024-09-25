package io.ChatAgent.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNodeRequest {
    // private String workflowId;
    private String parentId;
    private String title;
    private String type;
    private String description;
    private double positionX;
    private double positionY;
}
