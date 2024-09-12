package io.csd.cloudtechnology.aflowgent.dtoResponse.KMdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RqKnowledgeFilePreviewDto {
    private Integer maxSegmentSize;
    private Integer maxOverlapSize;
}
