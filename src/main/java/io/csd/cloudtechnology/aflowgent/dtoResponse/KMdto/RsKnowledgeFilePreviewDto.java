package io.csd.cloudtechnology.aflowgent.dtoResponse.KMdto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RsKnowledgeFilePreviewDto {
    private List<String> segments;
}
