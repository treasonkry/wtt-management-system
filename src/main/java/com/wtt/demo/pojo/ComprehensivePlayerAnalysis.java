package com.wtt.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComprehensivePlayerAnalysis {
    private Player player;
    private Equipment equipment;
    private List<MatchRecord> matches;
    private MatchStats stats;
    private List<OpponentAnalysis> opponentAnalysis;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchRecord {
        private Integer id;
        private Integer opponentId;
        private String opponentName;
        private String largeScore;
        private String smallScores;
        private Boolean isWin;
        private String matchTime;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchStats {
        private Integer totalMatches;
        private Integer winCount;
        private Integer loseCount;
        private Double winRate;
        private Integer recentForm;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OpponentAnalysis {
        private Integer opponentId;
        private String opponentName;
        private Integer timesPlayed;
        private Integer wins;
        private Integer losses;
        private String weaknessDescription;
    }
}
