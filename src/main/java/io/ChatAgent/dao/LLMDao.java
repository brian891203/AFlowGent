package io.ChatAgent.dao;

import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import io.ChatAgent.mapper.NodeLLMRowMapper;
import io.ChatAgent.model.Node;
import io.ChatAgent.model.NodeLLM;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("LLMDao")
public class LLMDao extends NodeDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public LLMDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void createNode(Node node) {
        // Call the createNode method of the parent class
        super.createNode(node);

        if (node instanceof NodeLLM) {
            NodeLLM nodeLLM = (NodeLLM) node;

            // Write to the tb_node_llm table specific to NodeLLM
            String llmSql = "INSERT INTO tb_node_llm (id, model_config, memory, prompt_template, context, vision, vector_store_id) " +
                        "VALUES (:id, :modelConfig, :memory, :promptTemplate, :context, :vision, :vectorStoreId)";
                        
            log.info("Inserting node with modelConfig: {}", nodeLLM.getModelConfig().toString());

            MapSqlParameterSource llmParams = new MapSqlParameterSource()
                .addValue("id", nodeLLM.getId())
                .addValue("modelConfig", new SqlParameterValue(Types.OTHER, nodeLLM.getModelConfig().toString()))
                .addValue("memory", new SqlParameterValue(Types.OTHER, nodeLLM.getMemory().toString()))
                .addValue("promptTemplate", new SqlParameterValue(Types.OTHER, nodeLLM.getPromptTemplate().toString()))
                .addValue("context", new SqlParameterValue(Types.OTHER, nodeLLM.getContext().toString()))
                .addValue("vision", new SqlParameterValue(Types.OTHER, nodeLLM.getVision().toString()))
                .addValue("vectorStoreId", UUID.fromString(nodeLLM.getVectorStoreId()));

            namedParameterJdbcTemplate.update(llmSql, llmParams);
        } else {
            throw new IllegalArgumentException("Node is not of type NodeLLM");
        }
    }

    @Override
    public Optional<Node> getNodeById(String id) {
        String sql = "SELECT n.id, n.workflow_id, n.parent_id, n.title, n.type, n.description, n.position_x, n.position_y, " +
                    "nl.model_config, nl.memory, nl.prompt_template, nl.context, nl.vision, nl.vector_store_id, " +
                    "vs.content AS vector_content, vs.metadata AS vector_metadata, vs.embedding AS vector_embedding " +
                    "FROM tb_node n " +
                    "JOIN tb_node_llm nl ON n.id = nl.id " +
                    "LEFT JOIN vector_store vs ON nl.vector_store_id = vs.id " +
                    "WHERE n.id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        List<NodeLLM> results = namedParameterJdbcTemplate.query(sql, params, new NodeLLMRowMapper());

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }


    @Override
    public void updateNode(Node node) {
        // Update the tb_node table
        super.updateNode(node);

        if (node instanceof NodeLLM) {
            NodeLLM nodeLLM = (NodeLLM) node;

            // Update the tb_node_llm table
            String llmSql = "UPDATE tb_node_llm SET model_config = :modelConfig, memory = :memory, " +
                            "prompt_template = :promptTemplate, context = :context, vision = :vision " +
                            "WHERE id = :id";

            MapSqlParameterSource llmParams = new MapSqlParameterSource()
                .addValue("id", nodeLLM.getId())
                .addValue("modelConfig", new SqlParameterValue(Types.OTHER, nodeLLM.getModelConfig().toString()))
                .addValue("memory", new SqlParameterValue(Types.OTHER, nodeLLM.getMemory().toString()))
                .addValue("promptTemplate", new SqlParameterValue(Types.OTHER, nodeLLM.getPromptTemplate().toString()))
                .addValue("context", new SqlParameterValue(Types.OTHER, nodeLLM.getContext().toString()))
                .addValue("vision", new SqlParameterValue(Types.OTHER, nodeLLM.getVision().toString()));

            namedParameterJdbcTemplate.update(llmSql, llmParams);
        }
    }

        @Override
        public List<NodeLLM> getAllNodes() {
            String sql = "SELECT n.id, n.workflow_id, n.parent_id, n.title, n.type, n.description, n.position_x, n.position_y, " +
                        "nl.model_config, nl.memory, nl.prompt_template, nl.context, nl.vision " +
                        "FROM tb_node n " +
                        "JOIN tb_node_llm nl ON n.id = nl.id";

            return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(), new NodeLLMRowMapper());
        }
}
