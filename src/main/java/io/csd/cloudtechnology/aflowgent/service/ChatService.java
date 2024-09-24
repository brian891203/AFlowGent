package io.csd.cloudtechnology.aflowgent.service;

import java.util.List;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.stereotype.Service;

import io.csd.cloudtechnology.aflowgent.model.KM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatModel chatModel;
    private final EmbeddingModel embeddingModel;
    private final PgVectorStore pgVectorStore;
    private final KMService kmService;

    public String handleUserQuery(String query, KM km) {
        String kmContent = km.getContent();
        String systemPrompt = km.getSystemPrompt();

        String promptTemplate = """
            System prompt : %s
            基於以下上下文及System prompt信息回答用戶的問題。如果上下文中沒有相關信息，請使用你的知識來回答。
            
            上下文：
            %s
            
            用戶問題：%s
            """;
            
        String fullPrompt = String.format(promptTemplate, systemPrompt, kmContent, query);
        System.out.println(fullPrompt);

        // ChatResponse response = chatModel.call(
        //     new Prompt(
        //         List.of(new UserMessage(fullPrompt)),
        //         OllamaOptions.builder()
        //             .withModel(OllamaModel.LLAMA3_1)
        //             .withTemperature(0.4)
        //             .build();
        //     ));
        ChatResponse response = chatModel.call(new Prompt(List.of(new UserMessage(fullPrompt))));
        System.out.println(response.getResult().getOutput().getContent());

        return response.getResult().getOutput().getContent();
    }

    // public String handleUserQuery(String query, KM km) {
    //     List<Double> queryEmbedding = embeddingModel.embed(query);

    //     return findMostRelevantAnswer(queryEmbedding);
    // }

    // // 根據用戶查詢的向量進行相似度匹配
    // private String findMostRelevantAnswer(List<Double> queryEmbedding) {
    //     float maxSimilarity = -1;
    //     String bestAnswer = null;

    //     // 動態檢索已存入的所有文檔
    //     List<Document> allDocuments = pgVectorStore.getAll();  // 根據實際情況改成適合的方法

    //     for (Document document : allDocuments) {
    //         float[] docEmbedding = document.getEmbedding();
    //         float similarity = cosineSimilarity(queryEmbedding, docEmbedding);

    //         if (similarity > maxSimilarity) {
    //             maxSimilarity = similarity;
    //             bestAnswer = document.getContent();  // 返回匹配的文檔內容
    //         }
    //     }

    //     return bestAnswer != null ? bestAnswer : "Sorry, I don't understand your question.";
    // }

    private float cosineSimilarity(float[] vectorA, float[] vectorB) {
        float dotProduct = 0f;
        float normA = 0f;
        float normB = 0f;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (float) (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
