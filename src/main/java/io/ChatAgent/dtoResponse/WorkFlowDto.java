package io.ChatAgent.dtoResponse;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class WorkFlowDto {

    String id;
    String description;    
    String createdBy;
    String updatedBy;
    Date createdAt;
    Date updatedAt;
    boolean toolPublished;
}
