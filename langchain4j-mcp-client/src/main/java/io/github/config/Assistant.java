package io.github.config;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface Assistant {

  @SystemMessage("""
      You are expert math assistant and your task is to answer user query and use math tool for accurate calculation
      """)
  String ask(@MemoryId int memoryId, @UserMessage String userMessage);

}
