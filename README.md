# ChatAgent

什麼是 ChatAgent:
可以讓使用者先 upload 自己的 data。之後可以透過判斷使用者輸入是否為請假相關或是公文相關，檢索自己上傳的資料加上使用者本身的問題再給予回覆。若不是以上兩類的問題，都不予回覆。

## 環境需求

- Java 21

- 下載模板 [start.spring.io](https://start.spring.io/#!type=gradle-project&language=java&platformVersion=3.2.7&packaging=jar&jvmVersion=21&groupId=io.csd.cloudtechnology&artifactId=a-flow-gent&name=a-flow-gent&description=Demo%20project%20for%20Spring%20Boot&packageName=io.csd.cloudtechnology.aflowgent&dependencies=devtools,lombok,configuration-processor,docker-compose,modulith,web,data-rest,thymeleaf,oauth2-resource-server,data-jpa,data-jdbc,liquibase,postgresql,validation,cache,prometheus,distributed-tracing,testcontainers,cloud-stream,actuator,spring-ai-ollama,cloud-gcp,cloud-gcp-pubsub,spring-ai-vectordb-pgvector,spring-ai-azure-openai,spring-ai-vertexai-gemini,cloud-gcp-storage)

## 員工助手流程

::: mermaid
graph LR
A[起點] --> B[分類]
B --> C[請假相關]
B --> D[公文相關]
B --> E[其他問題]
C --> F[請假知識庫]
D --> G[公文知識庫]
E --> H[直接回覆: 你的問題我無法處理]
F --> I[回覆解答]
F --> J[執行請假]
G --> K[回覆解答]
:::

schema:

CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 存儲上傳的 RAG 文件的基本信息
CREATE TABLE tb_rag_files (
    id UUID PRIMARY KEY, -- 文件的唯一標識符，使用 UUID
    file_name VARCHAR(255) NOT NULL, -- 文件名
    file_type VARCHAR(50) NOT NULL, -- 文件的類型（例如 txt, pdf 等）
    uploaded_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 文件上傳時間
    uploaded_by VARCHAR(36), -- 上傳該文件的用戶 ID
    vector_store_id UUID UNIQUE NOT NULL, -- 引用 vector_store 表中的 id
    metadata json -- 文件的元數據（可選，例如文件大小、頁數等）
);

COMMENT ON TABLE tb_rag_files  IS '儲存上傳的 RAG 文件的基本信息';
COMMENT ON COLUMN tb_rag_files .id IS '文件的唯一標識符，使用 UUID';
COMMENT ON COLUMN tb_rag_files .file_name IS '文件的名稱';
COMMENT ON COLUMN tb_rag_files .file_type IS '文件的類型，例如 txt, pdf';
COMMENT ON COLUMN tb_rag_files .uploaded_at IS '文件上傳時間';
COMMENT ON COLUMN tb_rag_files .uploaded_by IS '上傳該文件的用戶 ID';
COMMENT ON COLUMN tb_rag_files .metadata IS '文件的元數據，以 json 格式儲存';

-- 存儲 RAG 文件的嵌入向量
CREATE TABLE IF NOT EXISTS vector_store (
  id UUID PRIMARY KEY,
  content text,
  metadata json,
  embedding vector(4096),
  FOREIGN KEY (id) REFERENCES tb_rag_files(vector_store_id) ON DELETE CASCADE -- 设置外键约束
);

-- using HNSW indexes for high performance similaritySearch or some vector_cosine_ops
-- replace the 1536 with the actual embedding dimension if you are using a different dimension. 
-- PGvector supports at most 2000 dimensions for HNSW indexes.

-- CREATE TABLE IF NOT EXISTS vector_store (
--   id UUID PRIMARY KEY,
--   content text,
--   metadata json,
--   embedding vector(1536),
--   FOREIGN KEY (id) REFERENCES tb_node_llm(vector_store_id) ON DELETE CASCADE -- 设置外键约束
-- );

-- CREATE INDEX ON vector_store USING HNSW (embedding vector_cosine_ops);
