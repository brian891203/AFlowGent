package io.ChatAgent.mapper;
// package com.example.chat.mapper;

// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.UUID;

// import org.springframework.jdbc.core.RowMapper;

// import com.example.chat.model.Node;

// public class NodeRowMapper implements RowMapper<Node> {
//     @Override
//     public Node mapRow(ResultSet rs, int rowNum) throws SQLException {
//         Node node = new Node();
//         node.setId(UUID.fromString(rs.getString("id")));
//         node.setWorkflowId(UUID.fromString(rs.getString("workflow_id")));
//         node.setParentId(rs.getString("parent_id") != null ? UUID.fromString(rs.getString("parent_id")) : null);
//         node.setTitle(rs.getString("title"));
//         node.setType(rs.getString("type"));
//         node.setDescription(rs.getString("description"));
//         node.setPositionX(rs.getDouble("position_x"));
//         node.setPositionY(rs.getDouble("position_y"));
//         return node;
//     }
// }
