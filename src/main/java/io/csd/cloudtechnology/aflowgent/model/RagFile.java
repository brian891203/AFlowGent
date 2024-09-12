package io.csd.cloudtechnology.aflowgent.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RagFile {
    private UUID id; // 文件的唯一標識符
    private String fileName; // 文件名
    private String fileType; // 文件類型 (如 txt, pdf 等)
    private LocalDateTime uploadedAt; // 文件上傳時間
    private String uploadedBy; // 上傳文件的用戶 ID
    private UUID vectorStoreId; // 引用 vector_store 表中的 ID
    private String metadata; // 文件的元數據 (JSON 格式)
}
