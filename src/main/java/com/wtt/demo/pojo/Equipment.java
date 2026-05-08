package com.wtt.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {
    private String playerName;
    private String blade;
    private String forehandRubber;
    private String backhandRubber;
}
