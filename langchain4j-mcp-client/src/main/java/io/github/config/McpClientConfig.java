package io.github.config;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class McpClientConfig {

  @Bean
  public McpToolProvider mathToolProvider() {
    McpTransport transport = new StreamableHttpMcpTransport.Builder()
        .url("http://localhost:8080/api/mcp")
        .logRequests(true) // if you want to see the traffic in the log
        .logResponses(true)
        .build();

    McpClient mcpClient = new DefaultMcpClient.Builder()
        .key("MathMCPClient")
        .transport(transport)
        .build();

    return McpToolProvider.builder()
        .mcpClients(mcpClient)
        .build();
  }
}
