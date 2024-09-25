package io.ChatAgent.vectorStore;

// import org.springframework.ai.vectorstore.PgVectorStore;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.stereotype.Component;

// import io.csd.cloudtechnology.aflowgent.model.NodeLLM;

// @Component
// public class LLMVectorStore {
//     private final PgVectorStore pgVectorStore;

//     @Autowired
//     public LLMVectorStore(JdbcTemplate jdbcTemplate) {
//         this.pgVectorStore = new PgVectorStore(jdbcTemplate, "tb_node_llm", "id", "embedding");
//     }

//     public void addNodeLLM(NodeLLM nodeLLM) {
//         pgVectorStore.add(nodeLLM.getId(), nodeLLM.getEmbedding());
//     }
// }
