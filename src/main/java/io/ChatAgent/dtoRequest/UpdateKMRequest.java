package io.ChatAgent.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateKMRequest {
    private String fileName; // 文件名
    private String fileType; // 文件類型 (如 txt, pdf 等)
    private String uploadedBy; // 上傳文件的用戶 ID
    private String systemPrompt; // 系統提示
    private String description;
}
