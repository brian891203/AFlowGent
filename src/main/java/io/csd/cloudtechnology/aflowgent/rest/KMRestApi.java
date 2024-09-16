package io.csd.cloudtechnology.aflowgent.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.csd.cloudtechnology.aflowgent.dtoMapper.KMDtoMapper;
import io.csd.cloudtechnology.aflowgent.dtoRequest.CreateKMRequest;
import io.csd.cloudtechnology.aflowgent.dtoRequest.UpdateKMRequest;
import io.csd.cloudtechnology.aflowgent.dtoResponse.KMDtoResponse;
import io.csd.cloudtechnology.aflowgent.interfaces.api.KMApi;
import io.csd.cloudtechnology.aflowgent.model.KM;
import io.csd.cloudtechnology.aflowgent.service.KMService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KMRestApi implements KMApi {

    private final KMService kmService;
    private final ObjectMapper objectMapper;
    private final KMDtoMapper kmDtoMapper;  // 新增 KMDtoMapper

    @Override
    public ResponseEntity<KMDtoResponse> createKM(
        @RequestParam("request") String requestJson,
        @RequestParam("file") MultipartFile file) throws Exception {

        // 將 requestJson 轉換為 CreateKMRequest 對象
        CreateKMRequest request = objectMapper.readValue(requestJson, CreateKMRequest.class);
        
        if (file != null) {
            KM createdKM = kmService.createKM(request, file);
            // 使用 kmDtoMapper 生成 KMDtoResponse
            KMDtoResponse kmDtoResponse = kmDtoMapper.toDto(createdKM);

            return ResponseEntity.status(HttpStatus.CREATED).body(kmDtoResponse);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @Override
    public ResponseEntity<KMDtoResponse> getKMById(
        @PathVariable("KMId") String KMId) throws Exception {

        Optional<KM> kmOpt = kmService.getKMById(KMId);
        if (kmOpt.isPresent()) {
            // 使用 kmDtoMapper 生成 KMDtoResponse
            KMDtoResponse kmDtoResponse = kmDtoMapper.toDto(kmOpt.get());
            return new ResponseEntity<>(kmDtoResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<KMDtoResponse>> getAllKM() {
        List<KM> kmList = kmService.getAllKM();
        List<KMDtoResponse> responseList = kmList.stream()
            .map(kmDtoMapper::toDto)
            .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @Override
    public ResponseEntity<KMDtoResponse> updateKM(
        @PathVariable("KMId") String KMId,
        @RequestParam("request") String requestJson,
        @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        // 將 requestJson 轉換為 CreateKMRequest 對象
        UpdateKMRequest request = objectMapper.readValue(requestJson, UpdateKMRequest.class);

        Optional<KM> kmOpt = kmService.updateKM(KMId, request, file);
        if (kmOpt.isPresent()) {
            // 使用 kmDtoMapper 生成 KMDtoResponse
            KMDtoResponse kmDtoResponse = kmDtoMapper.toDto(kmOpt.get());
            return ResponseEntity.status(HttpStatus.OK).body(kmDtoResponse);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> deleteKM(
        @PathVariable("KMId") String KMId) throws Exception {

        kmService.deleteKM(KMId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
