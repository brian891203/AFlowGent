# ChatAgent

什麼是 ChatAgent:
可以讓使用者先 upload 自己的 data。之後可以透過判斷使用者輸入是否為請假相關或是公文相關，檢索自己上傳的資料加上使用者本身的問題再給予回覆。若不是以上兩類的問題，都不予回覆。

## 環境需求

- Java 21

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
