package io.csd.cloudtechnology.aflowgent.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeDto {
    private String id;
    private String workflowId;
    private String parentId;
    private String title;
    private String type;
    private String description;
    private double positionX;
    private double positionY;
}
