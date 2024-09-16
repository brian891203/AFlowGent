package io.csd.cloudtechnology.aflowgent.interfaces.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.csd.cloudtechnology.aflowgent.dtoResponse.KMDtoResponse;
@RequestMapping("/ChatAgent/v1/knowledges")
public interface KMApi {

    @PostMapping
    public ResponseEntity<KMDtoResponse> createKM(
        @RequestParam("request") String requestJson,
        @RequestParam("file") MultipartFile file) throws Exception;

    @GetMapping("/{KMId}")
    public ResponseEntity<KMDtoResponse> getKMById(
        @PathVariable("KMId") String KMId) throws Exception;

    @GetMapping("/all")
    public ResponseEntity<List<KMDtoResponse>> getAllKM() throws Exception;

    @PutMapping("/{KMId}")
    public ResponseEntity<KMDtoResponse> updateKM(
        @PathVariable("KMId") String KMId, 
        @RequestParam("request") String requestJson,
        @RequestParam(value = "file", required = false) MultipartFile file) throws Exception;

    @DeleteMapping("/{KMId}")
    public ResponseEntity<Void> deleteKM(
        @PathVariable("KMId") String KMId) throws Exception;
}
