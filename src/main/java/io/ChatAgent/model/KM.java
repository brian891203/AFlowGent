package io.ChatAgent.model;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KM {
    private String id; // 文件的唯一標識符
    private String fileName; // 文件名
    private String fileType; // 文件類型 (如 txt, pdf 等)
    private Timestamp uploadedAt; // 文件上傳時間
    private String uploadedBy; // 上傳文件的用戶 ID
    private String vectorStoreId; // 引用 vector_store 表中的 ID
    private String systemPrompt;
    private String content;
    private String description;
}
