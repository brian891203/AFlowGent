package io.csd.cloudtechnology.aflowgent.model.aggregates;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.AbstractAggregateRoot;

import io.csd.cloudtechnology.aflowgent.model.Node;
import io.csd.cloudtechnology.aflowgent.model.aggregates.commands.CreateWorkFlowCommand;
import io.csd.cloudtechnology.aflowgent.model.aggregates.commands.UpdateWorkFlowCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkFlow extends AbstractAggregateRoot<WorkFlow>{
    String id;
    String hash;
    String created_by;
    String updated_by;
    Timestamp created_at;
    Timestamp updated_at;
    Boolean tool_published;
    String description;

    List<Node> nodes = new ArrayList<>();

    public void on(CreateWorkFlowCommand command) {
        log.debug("CreateWorkFlowCommand: {}", command);
        // if (StringUtils.hasText(command.getId())) {
        //     this.id = command.getId();
        // } else {
        //     this.id = UUID.randomUUID().toString();
        // }

        this.id = command.getId();
        // this.hash = generateHash(command);
        this.created_by = command.getCreatedBy();
        this.tool_published = command.getToolPublished();
        this.description = command.getDescription();
        this.hash = generateHash();
    }

    public void on(UpdateWorkFlowCommand command) {
        log.debug("UpdateWorkFlowCommand: {}", command);

        // this.hash = generateHash(command);
        this.updated_by = command.getUpdatedBy();
        if (command.getToolPublished() != null) {
            this.tool_published = command.getToolPublished();
        }
        if (command.getDescription() != null) {
            this.description = command.getDescription();
        }

        this.hash = generateHash();
        this.updated_at = Timestamp.from(ZonedDateTime.now().toInstant());

    } 

    private String generateHash() {
        try {
            // Generate a hash based on the command properties using SHA-256 algorithm
            // String data = command.getId() + command.getDescription() + command.getCreatedBy() + command.getToolPublished();
            String data = this.id + this.description + this.created_by + this.tool_published;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hash = new StringBuilder();
            for (byte b : hashBytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("Failed to generate hash: {}", e.getMessage());
            return null;
        }
    }

    public void addNode(Node node) {
        nodes.add(node);
        node.setWorkflowId(this.id); // Set workflowId for the node
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }
}
