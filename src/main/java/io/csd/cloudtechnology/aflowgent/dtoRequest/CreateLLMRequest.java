package io.csd.cloudtechnology.aflowgent.dtoRequest;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLLMRequest extends CreateNodeRequest {
    
    private Map<String, Object> modelConfig; // Holds configuration for the LLM model
    private Map<String, Object> memory;      // Represents memory configuration
    private Map<String, Object> promptTemplate; // Template for the prompt
    private Map<String, Object> context;     // Context in which the LLM operates
    private Map<String, Object> vision;      // Vision-related configurations, if any
}
