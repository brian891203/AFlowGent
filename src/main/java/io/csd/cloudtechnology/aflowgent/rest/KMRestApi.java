package io.csd.cloudtechnology.aflowgent.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.csd.cloudtechnology.aflowgent.dtoResponse.KMdto.CreateKnowledgeDto;
import io.csd.cloudtechnology.aflowgent.dtoResponse.KMdto.KnowledgeDto;
import io.csd.cloudtechnology.aflowgent.dtoResponse.KMdto.RsKnowledgeFilePreviewDto;
import io.csd.cloudtechnology.aflowgent.interfaces.api.KMApi;
import io.csd.cloudtechnology.aflowgent.service.KMService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class KMRestApi implements KMApi {

    private final KMService kmService;

    @Override
    public ResponseEntity<KnowledgeDto> createKnowledge(
        @RequestBody CreateKnowledgeDto createKnowledgeDto) {

        KnowledgeDto createdKnowledge = kmService.createKnowledge(createKnowledgeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdKnowledge);
    }

    @Override
    public ResponseEntity<KnowledgeDto> getKnowledgeById(
        @PathVariable("knowledgeId") String knowledgeId) {

        KnowledgeDto knowledge = kmService.getKnowledgeById(knowledgeId);
        if (knowledge != null) {
            return ResponseEntity.ok(knowledge);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<RsKnowledgeFilePreviewDto> reviewKnowledgeFile(
        @PathVariable("knowledgeId") String knowledgeId,
        @RequestParam("file") MultipartFile file,
        @RequestParam("maxSegmentSize") Integer maxSegmentSize,
        @RequestParam("maxOverlapSize") Integer maxOverlapSize) {

        RsKnowledgeFilePreviewDto previewResponse = kmService.reviewKnowledgeFile(knowledgeId, file, maxSegmentSize, maxOverlapSize);
        return ResponseEntity.ok(previewResponse);
    }
}
