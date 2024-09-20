package io.csd.cloudtechnology.aflowgent.dtoRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateKMRequest {
    // @NotNull(message = "fileName is required")
    // private String fileName; // 文件名

    // @NotNull(message = "fileType is required")
    // private String fileType; // 文件類型 (如 txt, pdf 等)

    @NotNull(message = "uploadedBy is required")
    private String uploadedBy; // 上傳文件的用戶 ID
    
    @NotNull(message = "systemPrompt is required")
    private String systemPrompt; // 系統提示
}
