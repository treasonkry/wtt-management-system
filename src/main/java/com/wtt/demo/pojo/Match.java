package com.wtt.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    private Integer id;
    private Integer player1Id;
    private Integer player2Id;
    private String player1Name;
    private String player2Name;
    private String largeScore;
    private String smallScores;
    private Integer winnerId;
    private String winnerName;
    private LocalDateTime createdAt;
}
