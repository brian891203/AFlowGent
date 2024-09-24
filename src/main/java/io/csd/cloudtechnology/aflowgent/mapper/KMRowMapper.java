package io.csd.cloudtechnology.aflowgent.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import io.csd.cloudtechnology.aflowgent.model.KM;

public class KMRowMapper implements RowMapper<KM> {

    @Override
    public KM mapRow(ResultSet rs, int rowNum) throws SQLException {
        KM km = new KM();
        km.setId(rs.getString("id"));
        km.setFileName(rs.getString("file_name"));
        km.setFileType(rs.getString("file_type"));
        km.setUploadedBy(rs.getString("uploaded_by"));
        km.setUploadedAt(rs.getTimestamp("uploaded_at"));
        km.setVectorStoreId(rs.getString("vector_store_id"));
        km.setSystemPrompt(rs.getString("system_prompt"));
        km.setContent(rs.getString("content"));
        km.setDescription(rs.getString("description"));
        return km;
    }
}
