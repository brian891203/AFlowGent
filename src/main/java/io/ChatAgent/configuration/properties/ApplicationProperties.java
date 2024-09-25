package io.ChatAgent.configuration.properties;

import java.util.Map;
import java.util.Properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
// @FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Configuration
// @ConfigurationProperties(prefix = "gai")
public class ApplicationProperties {

  /** 應用程式的域名 ex: gai.60sec.dev */
  @NotBlank(message = "Domain must not be blank")
  String domain;

  /** 用於存儲相關數據的存儲桶名稱。 */
//   @NotBlank(message = "Bucket name must not be blank")
//   String bucketName;

//   @NotBlank(message = "Bucket name must not be blank")
//   String imageBucketName;

//   /** 與加密相關的配置屬性。 */
//   @NotNull(message = "Encryption properties must not be null")
//   EncryptionProperties encryption;

//   /** 定義使用的嵌入模型的相關配置屬性。 */
//   @NotNull(message = "Embedding properties must not be null")
//   EmbeddingProperties embedding;

//   /** 包含多個語言模型配置的列表。 */
//   @Valid
//   @NotEmpty(message = "At least one language model configuration is required")
//   List<LLMProperties> models;

  Map<String, Properties> tools;
}

