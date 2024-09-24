package io.csd.cloudtechnology.aflowgent.rest;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.csd.cloudtechnology.aflowgent.dtoMapper.KMDtoMapper;
import io.csd.cloudtechnology.aflowgent.dtoResponse.KMAnswerDtoResponse;
import io.csd.cloudtechnology.aflowgent.interfaces.api.ChatApi;
import io.csd.cloudtechnology.aflowgent.model.KM;
import io.csd.cloudtechnology.aflowgent.service.ChatService;
import io.csd.cloudtechnology.aflowgent.service.KMService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatRestApi implements ChatApi {

     private final ChatService chatService;
     private final KMService kmService;
     private final KMDtoMapper kmDtoMapper;

    @Override
    public ResponseEntity<KMAnswerDtoResponse> createKM(
        @PathVariable("KMId") String KMId, 
        @RequestBody String query) throws Exception {

        Optional<KM> kmOpt = kmService.getKMById(KMId);
        if (kmOpt.isPresent()) {
            KM km = kmOpt.get();

            String response = chatService.handleUserQuery(query, km);
        
            // 封裝為 DTO 回應
            KMAnswerDtoResponse kmResponse = new KMAnswerDtoResponse();
            kmResponse.setAnswer(response);

            return ResponseEntity.status(HttpStatus.CREATED).body(kmResponse);
            
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
