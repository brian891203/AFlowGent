package io.csd.cloudtechnology.aflowgent.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.csd.cloudtechnology.aflowgent.dao.KMDao;
import io.csd.cloudtechnology.aflowgent.dtoRequest.CreateKMRequest;
import io.csd.cloudtechnology.aflowgent.dtoRequest.UpdateKMRequest;
import io.csd.cloudtechnology.aflowgent.model.KM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KMService {

    @Autowired
    private KMDao kmDao;

    private final EmbeddingModel embeddingModel;
    private final PgVectorStore pgVectorStore;

    // Create KM with a file
    public KM createKM(CreateKMRequest request, MultipartFile file) throws IOException {
        KM km = new KM();
        km.setId(StringUtils.lowerCase(RandomStringUtils.randomAlphanumeric(6)));
        km.setFileName(file.getOriginalFilename());
        km.setFileType(determineFileType(file));
        km.setUploadedBy(request.getUploadedBy());
        km.setVectorStoreId(UUID.randomUUID().toString());
        km.setSystemPrompt(request.getSystemPrompt());

        km.setUploadedAt(Timestamp.from(ZonedDateTime.now().toInstant()));
        kmDao.createKM(km);

        String content = extractContent(file);
        List<Double> embedding = embeddingModel.embed(content);

        Document document = new Document(km.getVectorStoreId().toString(), content, Map.of("filename", file.getOriginalFilename()));
        document.setEmbedding(embedding);

        pgVectorStore.add(Collections.singletonList(document));

        log.info("Uploaded and processed document with ID: {}", km.getId());
        return km;
    }

    // Update an existing KM
    public Optional<KM> updateKM(String kmId, UpdateKMRequest request, MultipartFile file) throws IOException {
        Optional<KM> existingKMOpt = getKMById(kmId);
        if (existingKMOpt.isPresent()) {
            KM existingKM = existingKMOpt.get();
            existingKM.setUploadedAt(Timestamp.from(ZonedDateTime.now().toInstant()));

            if (request.getFileName() != null) {
                existingKM.setFileName(file.getOriginalFilename());
            }
            if (request.getFileType() != null) {
                existingKM.setFileType(request.getFileType());
            }
            if (request.getUploadedBy() != null) {
                existingKM.setUploadedBy(request.getUploadedBy());
            }

            if (file != null) {
                String content = extractContent(file);
                List<Double> embedding = embeddingModel.embed(content);

                pgVectorStore.delete(Collections.singletonList(existingKM.getVectorStoreId().toString()));
        
                Document document = new Document(existingKM.getVectorStoreId().toString(), content, Map.of("filename", file.getOriginalFilename()));
                document.setEmbedding(embedding);
                pgVectorStore.add(Collections.singletonList(document));
            }

            kmDao.updateKM(existingKM);

            log.info("Updated KM: {}", existingKM);
            return Optional.of(existingKM);
        } else {
            log.warn("KM with ID {} not found", kmId);
            return Optional.empty();
        }
    }

    // Retrieve a KM by ID
    public Optional<KM> getKMById(String id) {
        return kmDao.getKMById(id);
    }

    public List<KM> getAllKM() {
        return kmDao.getAllKM();
    }

    // Delete a KM by ID
    public boolean deleteKM(String id) {
        Optional<KM> kmOpt = getKMById(id);
        if (kmOpt.isPresent()) {
            kmDao.deleteKM(id);
            log.info("Deleted KM with ID: {}", id);
            return true;
        } else {
            log.warn("KM with ID {} not found", id);
            return false;
        }
    }

    // Extract content based on file type
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

    // Extract content from a PDF file
    private String extractPdfContent(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    // Extract content from a CSV file
    private String extractCsvContent(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
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

    // Determine file type based on file extension
    private String determineFileType(MultipartFile file) {
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
}
