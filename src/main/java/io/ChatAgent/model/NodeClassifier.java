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
public class NodeClassifier extends Node{

    private JSONObject classes;
    // private JSONObject queryVariableSelector;

    // public void setClasses(Map<String, Object> classesMap) {
    //     this.classes = new JSONObject(classesMap); // 將 Map 轉換為 JSONObject
    // }

    // public void setClasses(JSONObject classes) {
    //     this.classes = classes;
    // }

}
