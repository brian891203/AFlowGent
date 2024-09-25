package io.ChatAgent.dtoResponse;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true) // 確保調用超類的方法
public class NodeQClassifierDto extends NodeDto{
    private Map<String, Object> classes;
    // private JSONObject queryVariableSelector;
}
