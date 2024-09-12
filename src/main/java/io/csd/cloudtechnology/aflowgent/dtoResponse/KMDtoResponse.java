package io.csd.cloudtechnology.aflowgent.dtoResponse;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KMDtoResponse {
    private String id; // 文件的唯一標識符
    private String fileName; // 文件名
    private String fileType; // 文件類型
    private Timestamp uploadedAt; // 文件上傳時間
    private String uploadedBy; // 上傳文件的用戶 ID
    private String vectorStoreId; // 引用 vector_store 表中的 ID
    // private String metadata; // 文件的元數據 (JSON 格式)
}
