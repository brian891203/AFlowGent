package io.ChatAgent.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import io.ChatAgent.mapper.KMRowMapper;
import io.ChatAgent.model.KM;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("KMDao")
public class KMDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public KMDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    // Create KM record
    public void createKM(KM km) {
        String sql = "INSERT INTO tb_rag_files (id, file_name, file_type, uploaded_at, uploaded_by, vector_store_id, system_prompt, content, description) " +
                "VALUES (:id, :fileName, :fileType, :uploadedAt, :uploadedBy, :vectorStoreId, :systemPrompt, :content, :description)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", km.getId())
                .addValue("fileName", km.getFileName())
                .addValue("fileType", km.getFileType())
                .addValue("uploadedAt", km.getUploadedAt())
                .addValue("uploadedBy", km.getUploadedBy())
                .addValue("vectorStoreId", UUID.fromString(km.getVectorStoreId().toString()))
                .addValue("systemPrompt", km.getSystemPrompt())
                .addValue("content", km.getContent())
                .addValue("description", km.getDescription());

        namedParameterJdbcTemplate.update(sql, params);
        log.info("Created KM with ID: {}", km.getId());
    }

    // Retrieve KM by ID
    public Optional<KM> getKMById(String id) {
        String sql = "SELECT id, file_name, file_type, uploaded_at, uploaded_by, vector_store_id, system_prompt, content, description " +
                "FROM tb_rag_files WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        List<KM> results = namedParameterJdbcTemplate.query(sql, params, new KMRowMapper());

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    // Update KM record
    public void updateKM(KM km) {
        String sql = "UPDATE tb_rag_files SET file_name = :fileName, file_type = :fileType, " +
                "uploaded_by = :uploadedBy, system_prompt = :systemPrompt, content = :content, description = :description WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", km.getId())
                .addValue("fileName", km.getFileName())
                .addValue("fileType", km.getFileType())
                .addValue("uploadedBy", km.getUploadedBy())
                .addValue("systemPrompt", km.getSystemPrompt())
                .addValue("content", km.getContent())
                .addValue("description", km.getDescription());

        namedParameterJdbcTemplate.update(sql, params);
        log.info("Updated KM with ID: {}", km.getId());
    }

    // Delete KM by ID
    public void deleteKM(String id) {
        String sql = "DELETE FROM tb_rag_files WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        namedParameterJdbcTemplate.update(sql, params);
        log.info("Deleted KM with ID: {}", id);
    }

    // Get all KMs
    public List<KM> getAllKM() {
        String sql = "SELECT id, file_name, file_type, uploaded_at, uploaded_by, vector_store_id, system_prompt, content, description FROM tb_rag_files";

        return namedParameterJdbcTemplate.query(sql, new KMRowMapper());
    }
}
