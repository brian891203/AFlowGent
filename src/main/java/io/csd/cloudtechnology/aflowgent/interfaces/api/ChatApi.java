package io.csd.cloudtechnology.aflowgent.interfaces.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.csd.cloudtechnology.aflowgent.dtoResponse.KMAnswerDtoResponse;

@RequestMapping("/ChatAgent/v1/chat")
public interface ChatApi {
    @PostMapping("/{KMId}/execute")
    public ResponseEntity<KMAnswerDtoResponse> createKM(
        @PathVariable("KMId") String KMId,
        @RequestBody String query) throws Exception;
}
