package io.ChatAgent.model.aggregates.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CreateWorkFlowCommand {
    
    String id;
    String description;
    String createdBy;
    Boolean toolPublished;
}
