package io.ChatAgent.rest;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.ChatAgent.dtoMapper.KMDtoMapper;
import io.ChatAgent.dtoResponse.KMAnswerDtoResponse;
import io.ChatAgent.interfaces.api.ChatApi;
import io.ChatAgent.model.KM;
import io.ChatAgent.service.ChatService;
import io.ChatAgent.service.KMService;
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
