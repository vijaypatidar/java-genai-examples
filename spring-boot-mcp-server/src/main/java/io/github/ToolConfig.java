package io.github;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ToolConfig {

  @Bean
  public McpServerFeatures.SyncToolSpecification calculatorTool() {
    var schema = """
        {
          "type" : "object",
          "id" : "urn:jsonschema:Operation",
          "properties" : {
            "operation" : {
              "type" : "string"
            },
            "a" : {
              "type" : "number"
            },
            "b" : {
              "type" : "number"
            }
          }
        }
        """;
    return McpServerFeatures.SyncToolSpecification
        .builder()
        .tool(McpSchema.Tool.builder()
            .name("calculator")
            .description("Basic calculator")
            .inputSchema(schema).build())
        .callHandler((exchange, callToolRequest) -> {
              var arguments = callToolRequest.arguments();
              double a = Double.parseDouble(arguments.get("a").toString());
              double b = Double.parseDouble(arguments.get("b").toString());
              double res = switch (arguments.get("operation").toString()) {
                case "add" -> a + b;
                case "subtract" -> a - b;
                case "multiply" -> a * b;
                case "divide" -> a / b;
                default -> throw new IllegalStateException();
              };
              return new McpSchema.CallToolResult(Double.toString(res), false);
            }
        ).build();
  }
}
