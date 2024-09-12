package io.csd.cloudtechnology.aflowgent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    private String id;
    private String workflowId;
    private String parentId;
    private String title;
    private String type;
    private String description;
    private double positionX;
    private double positionY;
}