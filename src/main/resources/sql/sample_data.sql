-- ============================================
-- WTT管理系统示例数据
-- ============================================

-- 清空现有数据（可选）
-- DELETE FROM user_action_log;
-- DELETE FROM equipment;
-- DELETE FROM player;
-- DELETE FROM admin;

-- ============================================
-- 1. 管理员表 (admin) 示例数据
-- ============================================
INSERT INTO admin (id, username, password) VALUES
(1, 'admin', 'admin123'),
(2, 'zhangsan', 'zs456789'),
(3, 'lisi', 'ls987654');

-- ============================================
-- 2. 球员表 (player) 示例数据
-- ============================================
INSERT INTO player (id, name, phone, username, password, points) VALUES
(1, '马龙', '13800138001', 'malong', 'ml2024', 3500),
(2, '樊振东', '13800138002', 'fanzhendong', 'fzd2024', 3400),
(3, '王楚钦', '13800138003', 'wangchuqin', 'wcq2024', 3300),
(4, '孙颖莎', '13800138004', 'sunyingsha', 'sys2024', 3450),
(5, '陈梦', '13800138005', 'chenmeng', 'cm2024', 3200),
(6, '王曼昱', '13800138006', 'wangmanyu', 'wmy2024', 3150),
(7, '梁靖崑', '13800138007', 'liangjingkun', 'ljk2024', 2900),
(8, '林高远', '13800138008', 'lingaoyuan', 'lgy2024', 2850),
(9, '张本智和', '13800138009', 'zhangbenzhihe', 'zbzh2024', 2950),
(10, '伊藤美诚', '13800138010', 'yitengmeicheng', 'ytmc2024', 2800);

-- ============================================
-- 3. 器材表 (equipment) 示例数据
-- ============================================
INSERT INTO equipment (player_name, blade, forehand_rubber, backhand_rubber) VALUES
('马龙', '红双喜狂飙龙5X', '红双喜狂飙3-蓝海绵', '蝴蝶Dignics 09c'),
('樊振东', '红双喜狂飙龙5', '红双喜狂飙3-Neo', '蝴蝶Tenergy 05'),
('王楚钦', '斯蒂卡CL-CR', '红双喜狂飙3', '蝴蝶Rozena'),
('孙颖莎', '红双喜狂飙龙5', '红双喜狂飙3-蓝海绵', '蝴蝶Dignics 09c'),
('陈梦', '红双喜狂飙龙5X', '红双喜狂飙3', '蝴蝶Tenergy 05'),
('王曼昱', '红双喜狂飙龙5', '红双喜狂飙3-Neo', '蝴蝶Rozena'),
('梁靖崑', '亚萨卡YE', '红双喜狂飙3', '多尼克F1'),
('林高远', '斯蒂卡CL-CR', '红双喜狂飙3', '蝴蝶Tenergy 64'),
('张本智和', '蝴蝶Viscaria', '蝴蝶Dignics 05', '蝴蝶Dignics 05'),
('伊藤美诚', '尼塔库 acoustic', '挺拔变革MX-P', '友谊729-08');

-- ============================================
-- 4. 用户操作日志表 (user_action_log) 示例数据
-- ============================================
INSERT INTO user_action_log (username, user_type, action_type, module, description, request_url, request_method, ip_address, user_agent, response_time, status_code, create_time) VALUES
('admin', 'admin', 'LOGIN', 'Admin', '管理员登录', '/api/adminlogin', 'POST', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', 120, 200, '2024-01-15 09:30:00'),
('admin', 'admin', 'QUERY', 'Player', '查询所有球员', '/player/allPlayers', 'GET', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', 85, 200, '2024-01-15 09:31:00'),
('malong', 'player', 'LOGIN', 'Player', '球员登录', '/api/playerlogin', 'POST', '192.168.1.101', 'Mozilla/5.0 (iPhone; CPU iPhone OS 14_0)', 95, 200, '2024-01-15 10:00:00'),
('malong', 'player', 'QUERY', 'Equipment', '查询器材信息', '/equipment/byPlayer/malong', 'GET', '192.168.1.101', 'Mozilla/5.0 (iPhone; CPU iPhone OS 14_0)', 65, 200, '2024-01-15 10:05:00'),
('admin', 'admin', 'INSERT', 'Player', '添加新球员', '/player/add', 'POST', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', 150, 200, '2024-01-15 10:30:00'),
('fanzhendong', 'player', 'LOGIN', 'Player', '球员登录', '/api/playerlogin', 'POST', '192.168.1.102', 'Mozilla/5.0 (Android 11; Mobile)', 88, 200, '2024-01-15 11:00:00'),
('admin', 'admin', 'UPDATE', 'Player', '更新球员信息', '/player/update', 'PUT', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', 135, 200, '2024-01-15 11:30:00'),
('sunyingsha', 'player', 'QUERY', 'Chat', 'AI问答-打法建议', '/api/chat/suggest/4', 'POST', '192.168.1.103', 'Mozilla/5.0 (iPad; CPU OS 14_0)', 2500, 200, '2024-01-15 14:00:00'),
('admin', 'admin', 'DELETE', 'Player', '删除球员', '/player/delete/99', 'DELETE', '192.168.1.100', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', 95, 200, '2024-01-15 15:00:00'),
('wangchuqin', 'player', 'QUERY', 'Chat', 'AI问答-水平分析', '/api/chat/analyze/3', 'GET', '192.168.1.104', 'Mozilla/5.0 (iPhone; CPU iPhone OS 15_0)', 3200, 200, '2024-01-15 16:00:00');

-- ============================================
-- 数据说明
-- ============================================
-- 管理员账号：
--   1. admin / admin123 (超级管理员)
--   2. zhangsan / zs456789 (普通管理员)
--   3. lisi / ls987654 (普通管理员)
--
-- 球员账号（用户名/密码）：
--   1. malong / ml2024 (马龙 - 积分3500)
--   2. fanzhendong / fzd2024 (樊振东 - 积分3400)
--   3. wangchuqin / wcq2024 (王楚钦 - 积分3300)
--   4. sunyingsha / sys2024 (孙颖莎 - 积分3450)
--   5. chenmeng / cm2024 (陈梦 - 积分3200)
--   6. wangmanyu / wmy2024 (王曼昱 - 积分3150)
--   7. liangjingkun / ljk2024 (梁靖崑 - 积分2900)
--   8. lingaoyuan / lgy2024 (林高远 - 积分2850)
--   9. zhangbenzhihe / zbzh2024 (张本智和 - 积分2950)
--   10. yitengmeicheng / ytmc2024 (伊藤美诚 - 积分2800)
--
-- 注意：
-- 1. 执行前请确保数据库已创建且表结构正确
-- 2. 如果表中已有数据，建议先清空或调整ID避免冲突
-- 3. 手机号为示例号码，非真实号码
-- 4. 密码为明文存储，实际项目中应加密存储
-- ============================================
