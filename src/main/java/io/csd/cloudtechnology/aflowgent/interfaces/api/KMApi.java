// package io.csd.cloudtechnology.aflowgent.interfaces.api;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;

// import io.csd.cloudtechnology.aflowgent.dtoResponse.KMdto.CreateKnowledgeDto;
// import io.csd.cloudtechnology.aflowgent.dtoResponse.KMdto.KnowledgeDto;
// import io.csd.cloudtechnology.aflowgent.dtoResponse.KMdto.RsKnowledgeFilePreviewDto;viewDto;

// @RequestMapping("/AflowGent/v1/knowledges")
// public interface KMApi {

//     @PostMapping
//     public ResponseEntity<KnowledgeDto> createKnowledge(
//         @RequestBody CreateKnowledgeDto createKnowledgeDto);

//     @GetMapping("/{knowledgeId}")
//     public ResponseEntity<KnowledgeDto> getKnowledgeById(
//         @PathVariable("knowledgeId") String knowledgeId);

//     @PostMapping("/{knowledgeId}/filePreview")
//     public ResponseEntity<RsKnowledgeFilePreviewDto> reviewKnowledgeFile(
//         @PathVariable("knowledgeId") String knowledgeId,
//         @RequestParam("file") MultipartFile file,
//         @RequestParam("maxSegmentSize") Integer maxSegmentSize,
//         @RequestParam("maxOverlapSize") Integer maxOverlapSize);
// }
