package com.wtt.demo.service.Impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wtt.demo.pojo.*;
import com.wtt.demo.service.ChatService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class ChatServiceImpl implements ChatService {
    
    @Value("${ai.deepseek.api-url}")
    private String deepSeekApiUrl;
    
    @Value("${ai.deepseek.api-key:}")
    private String deepSeekApiKey;
    
    @Value("${ai.deepseek.model}")
    private String deepSeekModel;
    
    @Value("${ai.ollama.api-url}")
    private String ollamaApiUrl;
    
    @Value("${ai.ollama.model}")
    private String ollamaModel;
    
    private final OkHttpClient httpClient;
    private final Gson gson;
    
    public ChatServiceImpl() {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        this.gson = new Gson();
    }
    
    @Override
    public String queryChat(String content, String model) {
        if (content == null || content.trim().isEmpty()) {
            return "问题内容不能为空";
        }
        
        try {
            String response = callAIAPI(content, model);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "AI服务调用失败: " + e.getMessage();
        }
    }
    
    @Override
    public String analyzePlayerLevel(Player player) {
        if (player == null) {
            return "球员信息不能为空";
        }
        
        StringBuilder prompt = new StringBuilder();
        prompt.append("请根据以下乒乓球运动员的信息进行水平分析：\n");
        prompt.append("姓名：").append(player.getName()).append("\n");
        prompt.append("积分：").append(player.getPoints() != null ? player.getPoints() : "未知").append("\n");
        prompt.append("\n请从以下几个方面进行分析：\n");
        prompt.append("1. 技术水平评估\n");
        prompt.append("2. 比赛经验推测\n");
        prompt.append("3. 优势与不足\n");
        prompt.append("4. 提升建议\n");
        
        try {
            // 默认使用Ollama本地模型进行球员分析
            String response = callAIAPI(prompt.toString(), "ollama");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "球员水平分析失败: " + e.getMessage();
        }
    }
    
    @Override
    public String suggestPlayingStyle(Player player, String style) {
        if (player == null) {
            return "球员信息不能为空";
        }

        StringBuilder prompt = new StringBuilder();
        prompt.append("请根据以下乒乓球运动员的信息提供打法建议：\n");
        prompt.append("姓名：").append(player.getName()).append("\n");
        prompt.append("积分：").append(player.getPoints() != null ? player.getPoints() : "未知").append("\n");

        if (style != null && !style.trim().isEmpty()) {
            prompt.append("偏好打法风格：").append(style).append("\n");
        }

        prompt.append("\n请从以下几个方面提供建议：\n");
        prompt.append("1. 适合的打法风格\n");
        prompt.append("2. 技术训练重点\n");
        prompt.append("3. 战术运用建议\n");
        prompt.append("4. 装备选择建议\n");

        try {
            String response = callAIAPI(prompt.toString(), "deepseek");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "打法建议生成失败: " + e.getMessage();
        }
    }

    @Override
    public String analyzeWithComprehensiveData(Player player, Equipment equipment, ComprehensivePlayerAnalysis analysis, String focus) {
        if (player == null) {
            return "球员信息不能为空";
        }

        StringBuilder prompt = new StringBuilder();
        prompt.append("请对以下乒乓球运动员进行综合分析：\n\n");

        prompt.append("【基本信息】\n");
        prompt.append("姓名：").append(player.getName()).append("\n");
        prompt.append("积分：").append(player.getPoints() != null ? player.getPoints() : "未知").append("\n\n");

        if (equipment != null) {
            prompt.append("【器材配置】\n");
            prompt.append("底板：").append(equipment.getBlade() != null ? equipment.getBlade() : "未配置").append("\n");
            prompt.append("正手胶皮：").append(equipment.getForehandRubber() != null ? equipment.getForehandRubber() : "未配置").append("\n");
            prompt.append("反手胶皮：").append(equipment.getBackhandRubber() != null ? equipment.getBackhandRubber() : "未配置").append("\n\n");
        }

        if (analysis.getStats() != null) {
            ComprehensivePlayerAnalysis.MatchStats stats = analysis.getStats();
            prompt.append("【比赛数据统计】\n");
            prompt.append("总比赛数：").append(stats.getTotalMatches()).append("\n");
            prompt.append("胜场：").append(stats.getWinCount()).append("\n");
            prompt.append("负场：").append(stats.getLoseCount()).append("\n");
            prompt.append("胜率：").append(String.format("%.1f", stats.getWinRate())).append("%\n");
            prompt.append("近期状态(近期5场)：").append(stats.getRecentForm() > 0 ? "上升↑" : (stats.getRecentForm() < 0 ? "下降↓" : "平稳")).append("\n\n");
        }

        if (analysis.getMatches() != null && !analysis.getMatches().isEmpty()) {
            prompt.append("【近期比赛记录】\n");
            int count = Math.min(5, analysis.getMatches().size());
            for (int i = 0; i < count; i++) {
                ComprehensivePlayerAnalysis.MatchRecord match = analysis.getMatches().get(i);
                String result = match.getIsWin() ? "胜" : "负";
                prompt.append("vs ").append(match.getOpponentName())
                      .append(" ").append(result)
                      .append(" (").append(match.getLargeScore()).append(")")
                      .append(" - ").append(match.getMatchTime()).append("\n");
            }
            prompt.append("\n");
        }

        if (analysis.getOpponentAnalysis() != null && !analysis.getOpponentAnalysis().isEmpty()) {
            prompt.append("【对手分析】\n");
            for (ComprehensivePlayerAnalysis.OpponentAnalysis opp : analysis.getOpponentAnalysis()) {
                prompt.append("vs ").append(opp.getOpponentName())
                      .append("：战绩 ").append(opp.getWins()).append("胜").append(opp.getLosses()).append("负\n");
            }
            prompt.append("\n");
        }

        if ("weakness".equalsIgnoreCase(focus)) {
            prompt.append("【重点分析】请根据以上数据，重点分析：\n");
            prompt.append("1. 该球员的弱点是什么？\n");
            prompt.append("2. 在输掉的比赛中，对手是如何针对他的？\n");
            prompt.append("3. 根据他输给的对手特点，给出改进建议\n");
        } else if ("improvement".equalsIgnoreCase(focus)) {
            prompt.append("【重点分析】请根据以上数据，重点分析：\n");
            prompt.append("1. 该球员近期水平是否有提升？\n");
            prompt.append("2. 技术特点和改进空间\n");
            prompt.append("3. 针对性的训练建议\n");
        } else {
            prompt.append("【综合分析】请从以下几个方面进行全面分析：\n");
            prompt.append("1. 技术水平评估（正手/反手/发球/接发球）\n");
            prompt.append("2. 打法特点分析\n");
            prompt.append("3. 近期状态和水平变化趋势\n");
            prompt.append("4. 优势与不足\n");
            prompt.append("5. 改进建议\n");
            prompt.append("6. 器材适配建议（如有器材信息）\n");
        }

        try {
            String response = callAIAPI(prompt.toString(), "deepseek");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "综合分析失败: " + e.getMessage();
        }
    }
    
    /**
     * 调用AI API
     * @param message 用户消息
     * @param model AI模型标识（deepseek/ollama）
     * @return AI回复
     */
    private String callAIAPI(String message, String model) throws IOException {
        // 根据model参数选择API
        if ("ollama".equalsIgnoreCase(model)) {
            return callOllamaAPI(message);
        } else {
            // 默认使用DeepSeek，或当model为"deepseek"时
            return callDeepSeekAPI(message);
        }
    }
    
    /**
     * 调用DeepSeek API
     * @param message 用户消息
     * @return AI回复
     */
    private String callDeepSeekAPI(String message) throws IOException {
        if (deepSeekApiKey == null || deepSeekApiKey.trim().isEmpty()) {
            return generateMockResponse(message + " [DeepSeek]");
        }
        
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("model", deepSeekModel);
        
        JsonArray messages = new JsonArray();
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", message);
        messages.add(userMessage);
        
        jsonBody.add("messages", messages);
        jsonBody.addProperty("temperature", 0.7);
        jsonBody.addProperty("max_tokens", 1000);
        
        RequestBody body = RequestBody.create(
                gson.toJson(jsonBody),
                MediaType.parse("application/json; charset=utf-8")
        );
        
        Request request = new Request.Builder()
                .url(deepSeekApiUrl)
                .post(body)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Authorization", "Bearer " + deepSeekApiKey)
                .build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("AI API请求失败: " + response.code());
            }
            
            String responseBody = response.body() != null ? response.body().string() : "";
            JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
            
            // 解析OpenAI格式的响应
            if (jsonResponse.has("choices")) {
                JsonArray choices = jsonResponse.getAsJsonArray("choices");
                if (choices.size() > 0) {
                    JsonObject firstChoice = choices.get(0).getAsJsonObject();
                    if (firstChoice.has("message")) {
                        JsonObject messageObj = firstChoice.getAsJsonObject("message");
                        return messageObj.get("content").getAsString();
                    }
                }
            }
            
            return "无法解析DeepSeek响应";
        }
    }
    
    /**
     * 调用Ollama API
     * @param message 用户消息
     * @return AI回复
     */
    private String callOllamaAPI(String message) throws IOException {
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("model", ollamaModel);
        jsonBody.addProperty("stream", false);
        
        JsonArray messages = new JsonArray();
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", message);
        messages.add(userMessage);
        
        jsonBody.add("messages", messages);
        
        RequestBody body = RequestBody.create(
                gson.toJson(jsonBody),
                MediaType.parse("application/json; charset=utf-8")
        );
        
        Request request = new Request.Builder()
                .url(ollamaApiUrl)
                .post(body)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Ollama API请求失败: " + response.code());
            }
            
            String responseBody = response.body() != null ? response.body().string() : "";
            JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
            
            // 解析Ollama格式的响应
            if (jsonResponse.has("message")) {
                JsonObject messageObj = jsonResponse.getAsJsonObject("message");
                if (messageObj.has("content")) {
                    return messageObj.get("content").getAsString();
                }
            }
            
            return "无法解析Ollama响应";
        }
    }
    
    /**
     * 生成模拟响应（用于测试或没有API Key时）
     * @param message 用户消息
     * @return 模拟响应
     */
    private String generateMockResponse(String message) {
        if (message.contains("水平分析")) {
            return "【模拟响应】球员水平分析报告：\n\n" +
                   "1. 技术水平评估：该球员具备扎实的基本功，正反手技术较为均衡。\n" +
                   "2. 比赛经验推测：根据积分情况，推测有一定的比赛经验。\n" +
                   "3. 优势与不足：优势在于稳定性好，不足可能是进攻威力有待提升。\n" +
                   "4. 提升建议：建议加强发球抢攻训练，提高前三板的质量。";
        } else if (message.contains("打法建议")) {
            return "【模拟响应】打法建议：\n\n" +
                   "1. 适合的打法风格：建议采用弧圈结合快攻的打法。\n" +
                   "2. 技术训练重点：加强正手连续拉冲能力和反手变线能力。\n" +
                   "3. 战术运用建议：多使用长短球结合，调动对手。\n" +
                   "4. 装备选择建议：建议选择控制性较好的底板，搭配粘性胶皮。";
        } else {
            return "【模拟响应】感谢您的提问：" + message + "\n\n" +
                   "这是一个模拟的AI回答。要使用真实的AI服务，请配置AI_API_KEY环境变量。";
        }
    }
}
