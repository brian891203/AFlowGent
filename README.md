# AFlowGent

什麼是 AFlowGent:
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
