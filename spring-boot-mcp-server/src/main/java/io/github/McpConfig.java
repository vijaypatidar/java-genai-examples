package io.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.WebMvcStreamableServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
@EnableWebMvc
class McpConfig {
  @Bean
  WebMvcStreamableServerTransportProvider webMvcStreamableHttpServerTransportProvider(
      ObjectMapper mapper) {
    return WebMvcStreamableServerTransportProvider.builder()
        .objectMapper(mapper)
        .mcpEndpoint("/api/mcp")
        .build();
  }

  @Bean
  RouterFunction<ServerResponse> mcpRouterFunction(
      WebMvcStreamableServerTransportProvider transportProvider) {
    return transportProvider.getRouterFunction();
  }

  @Bean
  McpSchema.ServerCapabilities serverCapabilities() {
    return McpSchema.ServerCapabilities.builder()
        .resources(false, true)  // Resource support with list changes notifications
        .tools(true)            // Tool support with list changes notifications
        .prompts(false)          // Prompt support with list changes notifications
        .logging()              // Enable logging support (enabled by default with logging level INFO)
        .build();
  }


  @Bean
  McpSyncServer mcpSyncServer(McpSchema.ServerCapabilities serverCapabilities,
                              WebMvcStreamableServerTransportProvider transportProvider,
                              List<McpServerFeatures.SyncToolSpecification> toolSpecifications) {
    return McpServer.sync(transportProvider)
        .serverInfo("Math MCP Server", "1.0.0")
        .capabilities(serverCapabilities)
        .tools(toolSpecifications)
        .build();
  }
}
