package io.github.config;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ChatConfig {

  @Bean
  public ChatModel chatModel() {
    return OllamaChatModel.builder()
        .modelName("qwen3:4b")
        .baseUrl("http://localhost:11434")
        .build();
  }

  @Bean
  public Assistant assistant(ChatModel chatModel, McpToolProvider toolProvider) {
    return AiServices.builder(Assistant.class)
        .chatModel(chatModel)
        .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(30))
        .toolProvider(toolProvider)
        .build();
  }
}
