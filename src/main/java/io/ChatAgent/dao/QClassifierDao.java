package io.ChatAgent.dao;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import io.ChatAgent.mapper.NodeClassifierRowMapper;
import io.ChatAgent.model.Node;
import io.ChatAgent.model.NodeClassifier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("QClassifierDao")
public class QClassifierDao extends NodeDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public QClassifierDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void createNode(Node node) {
        // Call the createNode method of the parent class
        super.createNode(node);

        if (node instanceof NodeClassifier) {
            NodeClassifier nodeClassifier = (NodeClassifier) node;

            // Write to the tb_node_classifier table specific to NodeClassifier
            String classifierSql = "INSERT INTO tb_node_classifier (id, classes) " +
                                "VALUES (:id, :classes)";
            
            log.info("Inserting node with classes: {}", nodeClassifier.getClasses().toString());

            MapSqlParameterSource classifierParams = new MapSqlParameterSource()
                .addValue("id", nodeClassifier.getId())
                .addValue("classes", new SqlParameterValue(Types.OTHER, nodeClassifier.getClasses().toString()));
                // .addValue("queryVariableSelector", nodeClassifier.getQueryVariableSelector().toString());

            namedParameterJdbcTemplate.update(classifierSql, classifierParams);
        } else {
            throw new IllegalArgumentException("Node is not of type NodeClassifier");
        }
    }

    @Override
    public Optional<Node> getNodeById(String id) {
        String sql = "SELECT n.id, n.workflow_id, n.parent_id, n.title, n.type, n.description, n.position_x, n.position_y, " +
                     "nc.classes, nc.query_variable_selector " +
                     "FROM tb_node n " +
                     "JOIN tb_node_classifier nc ON n.id = nc.id " +
                     "WHERE n.id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        // List<NodeClassifier> results = namedParameterJdbcTemplate.query(sql, params, this::mapRowToNodeClassifier);
        List<NodeClassifier> results = namedParameterJdbcTemplate.query(sql, params, new NodeClassifierRowMapper());

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public void updateNode(Node node) {
        // Update the tb_node table
        super.updateNode(node);

        if (node instanceof NodeClassifier) {
            NodeClassifier nodeClassifier = (NodeClassifier) node;

            // Update the tb_node_classifier table
            String classifierSql = "UPDATE tb_node_classifier SET classes = :classes " +
                                "WHERE id = :id";

            MapSqlParameterSource classifierParams = new MapSqlParameterSource()
                .addValue("id", nodeClassifier.getId())
                .addValue("classes", new SqlParameterValue(Types.OTHER, nodeClassifier.getClasses().toString()));
                // .addValue("queryVariableSelector", node.getQueryVariableSelector().toString());

            namedParameterJdbcTemplate.update(classifierSql, classifierParams);
        }
    }

    @Override
    public List<NodeClassifier> getAllNodes() {
        String sql = "SELECT n.id, n.workflow_id, n.parent_id, n.title, n.type, n.description, n.position_x, n.position_y, " +
                     "nc.classes, nc.query_variable_selector " +
                     "FROM tb_node n " +
                     "JOIN tb_node_classifier nc ON n.id = nc.id";

        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(), new NodeClassifierRowMapper());
    }

    // private NodeClassifier mapRowToNodeClassifier(ResultSet rs, int rowNum) throws SQLException {
    //     NodeClassifier node = new NodeClassifier();
    //     node.setId(rs.getString("id"));
    //     node.setWorkflowId(rs.getString("workflow_id"));
    //     node.setParentId(rs.getString("parent_id"));
    //     node.setTitle(rs.getString("title"));
    //     node.setType(rs.getString("type"));
    //     node.setDescription(rs.getString("description"));
    //     node.setPositionX(rs.getDouble("position_x"));
    //     node.setPositionY(rs.getDouble("position_y"));
    //     node.setClasses(new JSONObject(rs.getString("classes")));

    //     // String classesJson = rs.getString("classes");
    //     // log.info("Retrieved classes: {}", classesJson);

    //     // if (classesJson != null) {
    //     //     JSONObject jsonObject = new JSONObject(classesJson);
    //     //     Map<String, Object> classesMap = jsonObject.toMap();
    //     //     node.setClasses(classesMap);
    //     // }

    //     // node.setQueryVariableSelector(new JSONObject(rs.getString("query_variable_selector")));
    //     return node;
    // }
}
