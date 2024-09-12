package io.csd.cloudtechnology.aflowgent.model.aggregates.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UpdateWorkFlowCommand {
    String description;
    String updatedBy;
    Boolean toolPublished;
}
