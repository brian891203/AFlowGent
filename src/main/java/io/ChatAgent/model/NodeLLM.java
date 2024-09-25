package io.ChatAgent.model;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeLLM extends Node{

    private JSONObject modelConfig;
    private JSONObject memory;
    private JSONObject promptTemplate;
    private JSONObject context;
    private JSONObject vision;
    // private float[] embedding;

    private String vectorStoreId;
}
