package com.wtt.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户操作日志实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActionLog {
    private Long id;                    // 日志ID
    private String username;            // 用户名
    private String userType;            // 用户类型（admin/player）
    private String actionType;          // 操作类型（QUERY/INSERT/UPDATE/DELETE/LOGIN等）
    private String module;              // 模块名称（Player/Equipment/Admin/Chat等）
    private String description;         // 操作描述
    private String requestUrl;          // 请求URL
    private String requestMethod;       // 请求方法（GET/POST/PUT/DELETE）
    private String ipAddress;           // IP地址
    private String userAgent;           // 用户代理
    private Integer responseTime;       // 响应时间（毫秒）
    private Integer statusCode;         // HTTP状态码
    private LocalDateTime createTime;   // 创建时间
}
