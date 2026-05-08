-- 用户操作日志表
CREATE TABLE IF NOT EXISTS user_action_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    username VARCHAR(100) COMMENT '用户名',
    user_type VARCHAR(50) COMMENT '用户类型（admin/player/guest）',
    action_type VARCHAR(50) COMMENT '操作类型（QUERY/INSERT/UPDATE/DELETE/LOGIN等）',
    module VARCHAR(100) COMMENT '模块名称（Player/Equipment/Admin/Chat等）',
    description TEXT COMMENT '操作描述',
    request_url VARCHAR(500) COMMENT '请求URL',
    request_method VARCHAR(10) COMMENT '请求方法（GET/POST/PUT/DELETE）',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    user_agent VARCHAR(500) COMMENT '用户代理',
    response_time INT COMMENT '响应时间（毫秒）',
    status_code INT COMMENT 'HTTP状态码',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_username (username),
    INDEX idx_module (module),
    INDEX idx_action_type (action_type),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户操作日志表';
