package io.csd.cloudtechnology.aflowgent.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import io.csd.cloudtechnology.aflowgent.model.NodeLLM;

public class NodeLLMRowMapper implements RowMapper<NodeLLM> {
    
    @Autowired
    private PgVectorStore pgVectorStore;

    @Override
    public NodeLLM mapRow(ResultSet rs, int rowNum) throws SQLException {
        NodeLLM node = new NodeLLM();
        node.setId(rs.getString("id"));
        node.setWorkflowId(rs.getString("workflow_id"));
        node.setParentId(rs.getString("parent_id"));
        node.setTitle(rs.getString("title"));
        node.setType(rs.getString("type"));
        node.setDescription(rs.getString("description"));
        node.setPositionX(rs.getDouble("position_x"));
        node.setPositionY(rs.getDouble("position_y"));
        
        node.setModelConfig(new JSONObject(rs.getString("model_config")));
        node.setMemory(new JSONObject(rs.getString("memory")));
        node.setPromptTemplate(new JSONObject(rs.getString("prompt_template")));
        node.setContext(new JSONObject(rs.getString("context")));
        node.setVision(new JSONObject(rs.getString("vision")));
        node.setVectorStoreId(rs.getString("vector_store_id"));

        // // 使用 PgVectorStore 获取 Document 对象
        // if (node.getVectorStoreId() != null) {
        //     Document document = pgVectorStore.getDocumentById(node.getVectorStoreId());
        //     node.setVectorStore(document);
        // }

        return node;
    }
}
