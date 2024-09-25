package io.ChatAgent.dtoResponse;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true) // Ensure that the superclass's equals and hashCode methods are called
public class NodeLLMDto extends NodeDto {
    private Map<String, Object> modelConfig;
    private Map<String, Object> memory;
    private Map<String, Object> promptTemplate;
    private Map<String, Object> context;
    private Map<String, Object> vision;
    private String vectorStoreId;
}
