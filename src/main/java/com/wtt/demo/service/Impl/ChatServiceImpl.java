package com.wtt.demo.service.Impl;

import com.google.gson.Gson;
import com.wtt.demo.pojo.*;
import com.wtt.demo.service.ChatCacheService;
import com.wtt.demo.service.ChatService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class ChatServiceImpl implements ChatService {

    @Value("${ai.service.url:http://localhost:8000}")
    private String aiServiceUrl;

    @Value("${ai.default-model:deepseek}")
    private String defaultModel;

    private final OkHttpClient httpClient;
    private final Gson gson;
    
    @Autowired(required = false)
    private ChatCacheService chatCacheService;

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
            return callAiService("/api/chat/query", content, model);
        } catch (Exception e) {
            e.printStackTrace();
            return "AI服务调用失败: " + e.getMessage();
        }
    }

    @Override
    public String analyzePlayerLevel(Player player, String model) {
        if (player == null) {
            return "球员信息不能为空";
        }

        String effectiveModel = (model != null && !model.trim().isEmpty()) ? model : defaultModel;

        if (chatCacheService != null && player.getId() != null) {
            String cachedResult = chatCacheService.getPlayerAnalysis(player.getId());
            if (cachedResult != null) {
                return cachedResult;
            }
        }

        try {
            StringBuilder prompt = new StringBuilder();
            prompt.append("请根据以下乒乓球运动员的信息进行水平分析：\n");
            prompt.append("姓名：").append(player.getName()).append("\n");
            prompt.append("积分：").append(player.getPoints() != null ? player.getPoints() : "未知").append("\n");
            prompt.append("\n请从以下几个方面进行分析：\n");
            prompt.append("1. 技术水平评估\n");
            prompt.append("2. 比赛经验推测\n");
            prompt.append("3. 优势与不足\n");
            prompt.append("4. 提升建议\n");

            String result = callAiService("/api/chat/analyze", prompt.toString(), effectiveModel);
            
            if (chatCacheService != null && player.getId() != null && result != null && !result.startsWith("球员水平分析失败")) {
                chatCacheService.setPlayerAnalysis(player.getId(), result);
            }
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "球员水平分析失败: " + e.getMessage();
        }
    }

    @Override
    public String suggestPlayingStyle(Player player, String style, String model) {
        if (player == null) {
            return "球员信息不能为空";
        }

        String effectiveModel = (model != null && !model.trim().isEmpty()) ? model : defaultModel;

        if (chatCacheService != null && player.getId() != null) {
            String cachedResult = chatCacheService.getPlayingStyleSuggestion(player.getId(), style);
            if (cachedResult != null) {
                return cachedResult;
            }
        }

        try {
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

            String result = callAiService("/api/chat/suggest", prompt.toString(), effectiveModel);
            
            if (chatCacheService != null && player.getId() != null && result != null && !result.startsWith("打法建议生成失败")) {
                chatCacheService.setPlayingStyleSuggestion(player.getId(), style, result);
            }
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "打法建议生成失败: " + e.getMessage();
        }
    }

    @Override
    public String analyzeWithComprehensiveData(Player player, Equipment equipment, ComprehensivePlayerAnalysis analysis, String focus, String model) {
        if (player == null) {
            return "球员信息不能为空";
        }

        String effectiveModel = (model != null && !model.trim().isEmpty()) ? model : defaultModel;

        try {
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

            return callAiService("/api/chat/analyze", prompt.toString(), effectiveModel);
        } catch (Exception e) {
            e.printStackTrace();
            return "综合分析失败: " + e.getMessage();
        }
    }

    /**
     * 调用Python AI微服务
     * @param endpoint API端点
     * @param content 内容
     * @param model 模型标识
     * @return AI回复
     */
    private String callAiService(String endpoint, String content, String model) throws IOException {
        String url = aiServiceUrl + endpoint;

        com.google.gson.JsonObject jsonBody = new com.google.gson.JsonObject();
        jsonBody.addProperty("content", content);
        if (model != null) {
            jsonBody.addProperty("model", model);
        }

        RequestBody body = RequestBody.create(
                gson.toJson(jsonBody),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("AI Service请求失败: " + response.code());
            }

            String responseBody = response.body() != null ? response.body().string() : "";
            com.google.gson.JsonObject jsonResponse = gson.fromJson(responseBody, com.google.gson.JsonObject.class);

            if (jsonResponse.has("data")) {
                return jsonResponse.get("data").getAsString();
            }

            return "无法解析AI服务响应";
        } catch (IOException e) {
            throw new IOException("连接AI服务失败: " + e.getMessage(), e);
        }
    }
}