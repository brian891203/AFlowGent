package io.csd.cloudtechnology.aflowgent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import io.csd.cloudtechnology.aflowgent.model.Edge;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("EdgeDao")
public class EdgeDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void createEdge(Edge edge) {
        String sql = "INSERT INTO tb_edge (id, workflow_id, source_node_id, target_node_id, source_handle, target_handle, source_type, target_type) " +
                     "VALUES (:id, :workflowId, :sourceNodeId, :targetNodeId, :sourceHandle, :targetHandle, :sourceType, :targetType)";
        
        log.info("Inserting edge: {}", edge);

        MapSqlParameterSource edgeParams = new MapSqlParameterSource()
            .addValue("id", edge.getId())
            .addValue("workflowId", edge.getWorkflowId())
            .addValue("sourceNodeId", edge.getSourceNodeId())
            .addValue("targetNodeId", edge.getTargetNodeId())
            .addValue("sourceHandle", edge.getSourceHandle())
            .addValue("targetHandle", edge.getTargetHandle())
            .addValue("sourceType", edge.getSourceType())
            .addValue("targetType", edge.getTargetType());

        namedParameterJdbcTemplate.update(sql, edgeParams);
    }

     // Retrieve an edge by ID
    public Optional<Edge> getEdgeById(String id) {
        String sql = "SELECT * FROM tb_edge WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        List<Edge> results = namedParameterJdbcTemplate.query(sql, params, new EdgeRowMapper());

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    // Retrieve all edges by workflow ID
    public List<Edge> findAllEdgesByWorkflowId(String workflowId) {
        String sql = "SELECT * FROM tb_edge WHERE workflow_id = :workflowId";
        MapSqlParameterSource params = new MapSqlParameterSource("workflowId", workflowId);

        return namedParameterJdbcTemplate.query(sql, params, new EdgeRowMapper());
    }

    public Optional<Edge> findEdgeById(String edgeId) {
        String sql = "SELECT * FROM tb_edge WHERE id = :edgeId";
        MapSqlParameterSource params = new MapSqlParameterSource("edgeId", edgeId);
    
        List<Edge> results = namedParameterJdbcTemplate.query(sql, params, new EdgeRowMapper());
    
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    

    // Update an edge
    public void updateEdge(Edge edge) {
        String sql = "UPDATE tb_edge SET source_node_id = :sourceNodeId, target_node_id = :targetNodeId, " +
                     "source_handle = :sourceHandle, target_handle = :targetHandle, " +
                     "source_type = :sourceType, target_type = :targetType " +
                     "WHERE id = :id";
        
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", edge.getId())
            .addValue("sourceNodeId", edge.getSourceNodeId())
            .addValue("targetNodeId", edge.getTargetNodeId())
            .addValue("sourceHandle", edge.getSourceHandle())
            .addValue("targetHandle", edge.getTargetHandle())
            .addValue("sourceType", edge.getSourceType())
            .addValue("targetType", edge.getTargetType());

        namedParameterJdbcTemplate.update(sql, params);
    }

    // Delete an edge by ID
    public void deleteEdge(String id) {
        String sql = "DELETE FROM tb_edge WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        namedParameterJdbcTemplate.update(sql, params);
    }

    // RowMapper for Edge
    private static class EdgeRowMapper implements RowMapper<Edge> {
        @Override
        public Edge mapRow(ResultSet rs, int rowNum) throws SQLException {
            Edge edge = new Edge();
            edge.setId(rs.getString("id"));
            edge.setWorkflowId(rs.getString("workflow_id"));
            edge.setSourceNodeId(rs.getString("source_node_id"));
            edge.setTargetNodeId(rs.getString("target_node_id"));
            edge.setSourceHandle(rs.getString("source_handle"));
            edge.setTargetHandle(rs.getString("target_handle"));
            edge.setSourceType(rs.getString("source_type"));
            edge.setTargetType(rs.getString("target_type"));
            return edge;
        }
    }
}
