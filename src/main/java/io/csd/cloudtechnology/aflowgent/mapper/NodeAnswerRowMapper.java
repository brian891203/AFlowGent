// package com.example.chat.mapper;

// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.UUID;

// import org.json.JSONObject;
// import org.springframework.jdbc.core.RowMapper;

// import com.example.chat.model.NodeAnswer;

// public class NodeAnswerRowMapper implements RowMapper<NodeAnswer> {
    
//     @Override
//     public NodeAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {
//         NodeAnswer nodeAnswer = new NodeAnswer();
//         nodeAnswer.setId(UUID.fromString(rs.getString("id")));
//         nodeAnswer.setWorkflowId(UUID.fromString(rs.getString("workflow_id")));
//         nodeAnswer.setParentId(rs.getString("parent_id") != null ? UUID.fromString(rs.getString("parent_id")) : null);
//         nodeAnswer.setTitle(rs.getString("title"));
//         nodeAnswer.setType(rs.getString("type"));
//         nodeAnswer.setDescription(rs.getString("description"));
//         nodeAnswer.setPositionX(rs.getDouble("position_x"));
//         nodeAnswer.setPositionY(rs.getDouble("position_y"));
//         nodeAnswer.setAnswer(rs.getString("answer"));
//         nodeAnswer.setVariables(new JSONObject(rs.getString("variables")));
//         return nodeAnswer;
//     }
// }
