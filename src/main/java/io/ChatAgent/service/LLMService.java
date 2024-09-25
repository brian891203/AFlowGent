package io.ChatAgent.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONObject;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.ChatAgent.dao.NodeDao;
import io.ChatAgent.dtoRequest.CreateNodeRequest;
import io.ChatAgent.dtoRequest.UpdateLLMRequest;
import io.ChatAgent.dtoRequest.UpdateNodeRequest;
import io.ChatAgent.model.Node;
import io.ChatAgent.model.NodeLLM;
import io.ChatAgent.model.factory.NodeFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LLMService extends NodeService {

    @Autowired
    @Qualifier("LLMDao")
    private NodeDao nodeDao;

    private final EmbeddingModel embeddingModel;
    private final PgVectorStore pgVectorStore;

    @Override
    public NodeLLM createNode(CreateNodeRequest request, String workFlowId) {

        NodeLLM node = (NodeLLM) NodeFactory.createNode(request, workFlowId);

        nodeDao.createNode(node);
        log.info("Created NodeLLM: {}", node);
        return node;
    }

    public NodeLLM createNode(CreateNodeRequest request, String workFlowId, MultipartFile file) throws IOException {
        NodeLLM node = (NodeLLM) NodeFactory.createNode(request, workFlowId);
        node.setVectorStoreId(UUID.randomUUID().toString());
        nodeDao.createNode(node);

        String content = extractContent(file);
        List<Double> embedding = embeddingModel.embed(content);

        Document document = new Document(node.getVectorStoreId(), content, Map.of("filename", file.getOriginalFilename()));
        document.setEmbedding(embedding);

        pgVectorStore.add(Collections.singletonList(document));

        log.info("Uploaded and processed document with ID: {}", node.getId());
        return node;
    }

    private String extractContent(MultipartFile file) throws IOException {
        String fileType = determineFileType(file);
        String content;
    
        switch (fileType) {
            case "pdf":
                content = extractPdfContent(file);
                break;
            case "csv":
                content = extractCsvContent(file);
                break;
            default:
                // 对于其他文件类型，直接读取文本内容
                content = new String(file.getBytes(), StandardCharsets.UTF_8);
                break;
        }
    
        int byteSize = content.getBytes(StandardCharsets.UTF_8).length;
        int maxAllowedSize = 65535;
        if (byteSize > maxAllowedSize) {
            throw new IOException("Content exceeds the maximum allowed size of " + maxAllowedSize + " bytes");
        }
        log.info("Content size: {} bytes", byteSize);
        return content;
    }

    private String extractPdfContent(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }
    
    private String extractCsvContent(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) 
        {
            CSVFormat csvFormat = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                                                    .setHeader() 
                                                    .setSkipHeaderRecord(true)
                                                    .build();
            
            CSVParser parser = csvFormat.parse(reader);
            List<CSVRecord> records = parser.getRecords();
            
            return records.stream()
                    .map(record -> record.stream().collect(Collectors.joining(",")))
                    .collect(Collectors.joining("\n"));
        }
    }

    private String determineFileType(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        if (fileName != null) {
            if (fileName.toLowerCase().endsWith(".pdf")) {
                return "pdf";
            } else if (fileName.toLowerCase().endsWith(".csv")) {
                return "csv";
            }
        }
        return "txt";
    }
    

    @Override
    public Optional<Node> getNodeById(String id) {
        return nodeDao.getNodeById(id);
    }

    @Override
    public Optional<Node> updateNode(String nodeId, UpdateNodeRequest request, String workFlowId) {
        Optional<Node> existingNodeOpt = getNodeById(nodeId);
        if (existingNodeOpt.isPresent()) {
            NodeLLM existingNode = (NodeLLM) existingNodeOpt.get();

            if (request.getTitle() != null) {
                existingNode.setTitle(request.getTitle());
            }
            if (request.getDescription() != null) {
                existingNode.setDescription(request.getDescription());
            }
            if (request.getPositionX() != null) {
                existingNode.setPositionX(request.getPositionX());
            }
            if (request.getPositionY() != null) {
                existingNode.setPositionY(request.getPositionY());
            }
            if (request.getParentId() != null) {
                existingNode.setParentId(request.getParentId());
            }
            
            UpdateLLMRequest llmRequest = (UpdateLLMRequest) request;
            if (llmRequest.getModelConfig() != null) {
                existingNode.setModelConfig(new JSONObject(llmRequest.getModelConfig()));
            }
            if (llmRequest.getMemory() != null) {
                existingNode.setMemory(new JSONObject(llmRequest.getMemory()));
            }
            if (llmRequest.getPromptTemplate() != null) {
                existingNode.setPromptTemplate(new JSONObject(llmRequest.getPromptTemplate()));
            }
            if (llmRequest.getContext() != null) {
                existingNode.setContext(new JSONObject(llmRequest.getContext()));
            }
            if (llmRequest.getVision() != null) {
                existingNode.setVision(new JSONObject(llmRequest.getVision()));
            }

            nodeDao.updateNode(existingNode);

            log.info("Updated NodeLLM: {}", existingNode);
            return Optional.of(existingNode);
        } else {
            log.warn("NodeLLM with ID {} not found", nodeId);
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteNode(String id) {
        Optional<Node> nodeOpt = getNodeById(id);
        if (nodeOpt.isPresent()) {
            nodeDao.deleteNode(id);
            log.info("Deleted NodeLLM with ID: {}", id);
            return true;
        } else {
            log.warn("NodeLLM with ID {} not found", id);
            return false;
        }
    }

    // Additional methods for LLM-specific logic can be added here
}
