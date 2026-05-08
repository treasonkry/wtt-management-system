package com.wtt.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private Integer id;
    private String name;
    private String phone;
    private String username;
    private String password;
    private Integer points;

}
