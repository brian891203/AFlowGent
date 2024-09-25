package io.ChatAgent.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import io.ChatAgent.mapper.NodeClassifierRowMapper;
import io.ChatAgent.mapper.NodeLLMRowMapper;
import io.ChatAgent.model.Node;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("NodeDao")
public class NodeDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // 通用的 Node 寫入邏輯
    public void createNode(Node node) {
        String sql = "INSERT INTO tb_node (id, workflow_id, parent_id, title, type, description, position_x, position_y) " +
                     "VALUES (:id, :workflowId, :parentId, :title, :type, :description, :positionX, :positionY)";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", node.getId())
            .addValue("workflowId", node.getWorkflowId())
            .addValue("parentId", node.getParentId())
            .addValue("title", node.getTitle())
            .addValue("type", node.getType())
            .addValue("description", node.getDescription())
            .addValue("positionX", node.getPositionX())
            .addValue("positionY", node.getPositionY());

        namedParameterJdbcTemplate.update(sql, params);
    }

    public Optional<Node> getNodeById(String id) {
        // 提供抽象方法，子類別負責實現特定查詢
        return Optional.empty();
    }

    public Node findNodeById(String nodeId) {
        String sql = "SELECT id, workflow_id, parent_id, title, type, description, position_x, position_y " +
                 "FROM tb_node WHERE id = :nodeId";
        
        Map<String, Object> params = new HashMap<>();
        params.put("nodeId", nodeId);
        
        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
            Node node = new Node();
            node.setId(rs.getString("id"));
            node.setWorkflowId(rs.getString("workflow_id"));
            node.setParentId(rs.getString("parent_id"));
            node.setTitle(rs.getString("title"));
            node.setType(rs.getString("type"));
            node.setDescription(rs.getString("description"));
            node.setPositionX(rs.getDouble("position_x"));
            node.setPositionY(rs.getDouble("position_y"));
            return node;
        });
    }

    public void updateNode(Node node) {
        // Update the tb_node table
        String nodeSql = "UPDATE tb_node SET workflow_id = :workflowId, parent_id = :parentId, title = :title, " +
                         "type = :type, description = :description, position_x = :positionX, position_y = :positionY " +
                         "WHERE id = :id";
        
        MapSqlParameterSource nodeParams = new MapSqlParameterSource()
            .addValue("id", node.getId())
            .addValue("workflowId", node.getWorkflowId())
            .addValue("parentId", node.getParentId())
            .addValue("title", node.getTitle())
            .addValue("type", node.getType())
            .addValue("description", node.getDescription())
            .addValue("positionX", node.getPositionX())
            .addValue("positionY", node.getPositionY());

        namedParameterJdbcTemplate.update(nodeSql, nodeParams);
    }

    public void deleteNode(String id) {
        String sql = "DELETE FROM tb_node WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public List<? extends Node> getAllNodes() {
        // 提供抽象方法，子類別負責實現特定查詢
        return null;
    }

    public List<Node> findAllNodesInfoByWorkFlowId(String workFlowId) {
        String sql = "SELECT id, workflow_id, parent_id, title, type, description, position_x, position_y " +
        "FROM tb_node WHERE workflow_id = :workFlowId";
        
        Map<String, Object> params = new HashMap<>();
        params.put("workFlowId", workFlowId);
        
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Node node = new Node();
            node.setId(rs.getString("id"));
            node.setWorkflowId(rs.getString("workflow_id"));
            node.setParentId(rs.getString("parent_id"));
            node.setTitle(rs.getString("title"));
            node.setType(rs.getString("type"));
            node.setDescription(rs.getString("description"));
            node.setPositionX(rs.getDouble("position_x"));
            node.setPositionY(rs.getDouble("position_y"));
            return node;
        });
    }

    public List<Node> findAllNodesByWorkFlowId(String workFlowId) {
        String sql = "SELECT n.id, n.workflow_id, n.parent_id, n.title, n.type, n.description, n.position_x, n.position_y, " +
                        "nc.classes, nc.query_variable_selector, " +
                        "nl.model_config, nl.memory, nl.prompt_template, nl.context, nl.vision, nl.vector_store_id " +
                        "FROM tb_node n " +
                        "LEFT JOIN tb_node_classifier nc ON n.id = nc.id " +
                        "LEFT JOIN tb_node_llm nl ON n.id = nl.id " +
                        "WHERE n.workflow_id = :workFlowId";
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("workFlowId", workFlowId);
        
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            String nodeType = rs.getString("type");

            if ("NodeClassifier".equals(nodeType)) {
                return new NodeClassifierRowMapper().mapRow(rs, rowNum);

            } else if ("NodeLLM".equals(nodeType)) {
                return new NodeLLMRowMapper().mapRow(rs, rowNum);

            } else {
                Node node = new Node();
                node.setId(rs.getString("id"));
                node.setWorkflowId(rs.getString("workflow_id"));
                node.setParentId(rs.getString("parent_id"));
                node.setTitle(rs.getString("title"));
                node.setType(nodeType);
                node.setDescription(rs.getString("description"));
                node.setPositionX(rs.getDouble("position_x"));
                node.setPositionY(rs.getDouble("position_y"));
                return node;
            }
        });
    }
}
