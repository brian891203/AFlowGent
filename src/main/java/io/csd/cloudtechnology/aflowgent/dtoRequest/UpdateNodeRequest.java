package io.csd.cloudtechnology.aflowgent.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNodeRequest {
    private String parentId;
    private String title;
    private String type;
    private String description;
    private Double positionX;
    private Double positionY;
}
