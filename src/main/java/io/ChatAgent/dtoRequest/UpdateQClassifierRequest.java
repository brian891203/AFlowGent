package io.ChatAgent.dtoRequest;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateQClassifierRequest extends UpdateNodeRequest{
    private Map<String, String> classes; // 使用 Map 來表示 JSON 結構
    // private JSONObject queryVariableSelector;
}
