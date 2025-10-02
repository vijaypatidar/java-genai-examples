package io.github.controllers;

import io.github.config.Assistant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
  private final Assistant assistant;

  public ChatController(Assistant assistant) {
    this.assistant = assistant;
  }

  @PostMapping("/ask")
  public AskResponse ask(@RequestBody AskRequest request) {
    return new AskResponse(assistant.ask(1, request.message));
  }

  public record AskRequest(String message) {
  }

  public record AskResponse(String message) {
  }
}
