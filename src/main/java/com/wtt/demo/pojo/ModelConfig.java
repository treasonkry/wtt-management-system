package com.wtt.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelConfig {
    private String defaultModel;
    private List<ModelInfo> models;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModelInfo {
        private String key;
        private String label;
        private String description;
    }

    public static ModelConfig getDefaultConfig() {
        ModelConfig config = new ModelConfig();
        config.setDefaultModel("deepseek");

        List<ModelInfo> models = List.of(
            new ModelInfo("deepseek", "DeepSeek", "通用对话、中文理解（默认）"),
            new ModelInfo("openai", "OpenAI GPT-4", "高精度分析、复杂推理"),
            new ModelInfo("ollama", "Qwen3:8b (Ollama)", "本地部署、低延迟")
        );
        config.setModels(models);

        return config;
    }
}