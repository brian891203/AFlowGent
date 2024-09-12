package io.csd.cloudtechnology.aflowgent.dtoResponse.KMdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateKnowledgeDto {
    private String id;
    private String name;
    private String description;
    private String createdBy;
    private String createdTime;
    private String modifiedBy;
    private String modifiedTime;
}
