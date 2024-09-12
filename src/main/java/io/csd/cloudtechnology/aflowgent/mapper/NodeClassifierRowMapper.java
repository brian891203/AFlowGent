package io.csd.cloudtechnology.aflowgent.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import io.csd.cloudtechnology.aflowgent.model.NodeClassifier;

public class NodeClassifierRowMapper implements RowMapper<NodeClassifier> {
    
    @Override
    public NodeClassifier mapRow(ResultSet rs, int rowNum) throws SQLException {
        NodeClassifier node = new NodeClassifier();
        node.setId(rs.getString("id"));
        node.setWorkflowId(rs.getString("workflow_id"));
        node.setParentId(rs.getString("parent_id"));
        node.setTitle(rs.getString("title"));
        node.setType(rs.getString("type"));
        node.setDescription(rs.getString("description"));
        node.setPositionX(rs.getDouble("position_x"));
        node.setPositionY(rs.getDouble("position_y"));
        node.setClasses(new JSONObject(rs.getString("classes")));
        // String classesJson = rs.getString("classes");
        // System.out.println("Classes JSON from DB: " + classesJson);  // 添加這一行來檢查 `classes` 欄位的內容
        // node.setClasses(new JSONObject(classesJson));
        // node.setQueryVariableSelector(new JSONObject(rs.getString("query_variable_selector")));
        return node;
    }
}
