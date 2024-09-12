package io.csd.cloudtechnology.aflowgent.dtoRequest;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLLMRequest extends UpdateNodeRequest {
    
    private Map<String, Object> modelConfig; // Optional: Update configuration for the LLM model
    private Map<String, Object> memory;      // Optional: Update memory configuration
    private Map<String, Object> promptTemplate; // Optional: Update template for the prompt
    private Map<String, Object> context;     // Optional: Update context in which the LLM operates
    private Map<String, Object> vision;      // Optional: Update vision-related configurations, if any
}
