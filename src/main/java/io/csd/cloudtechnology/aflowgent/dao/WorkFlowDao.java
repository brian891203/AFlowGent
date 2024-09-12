package io.csd.cloudtechnology.aflowgent.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import io.csd.cloudtechnology.aflowgent.model.aggregates.WorkFlow;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WorkFlowDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void createWorkFlow(WorkFlow workflow) {
        // Define the SQL INSERT statement
        String sql = "INSERT INTO tb_workflow (id, hash, created_by, tool_published, description) " +
                     "VALUES (:id, :hash, :createdBy, :toolPublished, :description)";

        // Map WorkFlow properties to SQL parameters
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", workflow.getId())
            .addValue("hash", workflow.getHash())
            .addValue("createdBy", workflow.getCreated_by())
            .addValue("toolPublished", workflow.getTool_published())
            .addValue("description", workflow.getDescription());

        // Execute the update
        namedParameterJdbcTemplate.update(sql, params);

    }

    public WorkFlow getWorkFlowById(String flowId){
        String sql = "SELECT * FROM tb_workflow WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", flowId);
        List<WorkFlow> results = namedParameterJdbcTemplate.query(
            sql, params, (rs, rowNum) -> {
                WorkFlow wf = new WorkFlow();
                wf.setId(rs.getString("id"));
                wf.setHash(rs.getString("hash"));
                wf.setCreated_by(rs.getString("created_by"));
                wf.setUpdated_by(rs.getString("updated_by"));
                wf.setCreated_at(rs.getTimestamp("created_at"));
                wf.setUpdated_at(rs.getTimestamp("updated_at"));
                wf.setTool_published(rs.getBoolean("tool_published"));
                wf.setDescription(rs.getString("description"));
                return wf;
            });

        return results.isEmpty() ? null : results.get(0);
    }

    public List<WorkFlow> getAllWorkFlows() {
        String sql = "SELECT * FROM tb_workflow";

        List<WorkFlow> results = namedParameterJdbcTemplate.query(
            sql, (rs, rowNum) -> {
                WorkFlow wf = new WorkFlow();
                wf.setId(rs.getString("id"));
                wf.setHash(rs.getString("hash"));
                wf.setCreated_by(rs.getString("created_by"));
                wf.setUpdated_by(rs.getString("updated_by"));
                wf.setCreated_at(rs.getTimestamp("created_at"));
                wf.setUpdated_at(rs.getTimestamp("updated_at"));
                wf.setTool_published(rs.getBoolean("tool_published"));
                wf.setDescription(rs.getString("description"));
                return wf;
            });

        return results;
    }

    public void updateWorkFlow(WorkFlow workFlow) {
        String sql = "UPDATE tb_workflow SET updated_by = :updatedBy, description = :description, tool_published = :toolPublished, updated_at = :updatedAt WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("updatedBy", workFlow.getUpdated_by())
            .addValue("description", workFlow.getDescription())
            .addValue("toolPublished", workFlow.getTool_published())
            .addValue("updatedAt", workFlow.getUpdated_at())
            .addValue("id", workFlow.getId());

        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteWorkFlow(String flowId) {
        String sql = "DELETE FROM tb_workflow WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", flowId);

        namedParameterJdbcTemplate.update(sql, params);
    }
}
