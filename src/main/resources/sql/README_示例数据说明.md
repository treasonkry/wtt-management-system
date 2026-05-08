# WTT管理系统 - 示例数据说明

## 📁 文件位置
示例数据SQL文件位于：`src/main/resources/sql/sample_data.sql`

## 📊 数据概览

### 1. 管理员表 (admin) - 3条记录

| ID | 用户名 | 密码 | 说明 |
|----|--------|------|------|
| 1 | admin | admin123 | 超级管理员 |
| 2 | zhangsan | zs456789 | 普通管理员 |
| 3 | lisi | ls987654 | 普通管理员 |

---

### 2. 球员表 (player) - 10条记录

包含国内外知名乒乓球运动员：

#### 中国国家队球员
| ID | 姓名 | 用户名 | 密码 | 积分 | 手机号 |
|----|------|--------|------|------|--------|
| 1 | 马龙 | malong | ml2024 | 3500 | 13800138001 |
| 2 | 樊振东 | fanzhendong | fzd2024 | 3400 | 13800138002 |
| 3 | 王楚钦 | wangchuqin | wcq2024 | 3300 | 13800138003 |
| 4 | 孙颖莎 | sunyingsha | sys2024 | 3450 | 13800138004 |
| 5 | 陈梦 | chenmeng | cm2024 | 3200 | 13800138005 |
| 6 | 王曼昱 | wangmanyu | wmy2024 | 3150 | 13800138006 |
| 7 | 梁靖崑 | liangjingkun | ljk2024 | 2900 | 13800138007 |
| 8 | 林高远 | lingaoyuan | lgy2024 | 2850 | 13800138008 |

#### 国外球员
| ID | 姓名 | 用户名 | 密码 | 积分 | 国籍 |
|----|------|--------|------|------|------|
| 9 | 张本智和 | zhangbenzhihe | zbzh2024 | 2950 | 日本 |
| 10 | 伊藤美诚 | yitengmeicheng | ytmc2024 | 2800 | 日本 |

---

### 3. 器材表 (equipment) - 10条记录

每位球员都配置了真实的球拍装备：

**示例：**
- **马龙**: 红双喜狂飙龙5X + 狂飙3蓝海绵(正手) + Dignics 09c(反手)
- **樊振东**: 红双喜狂飙龙5 + 狂飙3-Neo(正手) + Tenergy 05(反手)
- **张本智和**: 蝴蝶Viscaria + Dignics 05(双面)
- **伊藤美诚**: 尼塔库acoustic + 挺拔变革MX-P(正手) + 729-08(反手)

---

### 4. 用户操作日志表 (user_action_log) - 10条记录

包含各种类型的操作记录：
- 登录操作 (LOGIN)
- 查询操作 (QUERY)
- 添加操作 (INSERT)
- 更新操作 (UPDATE)
- 删除操作 (DELETE)
- AI问答操作

---

## 🚀 如何使用

### 方法一：通过MySQL命令行

```bash
# 1. 登录MySQL
mysql -u root -p

# 2. 选择数据库
use wtt;

# 3. 执行SQL文件
source D:/java_project/wtt-management-system/src/main/resources/sql/sample_data.sql;
```

### 方法二：通过MySQL Workbench

1. 打开MySQL Workbench
2. 连接到数据库
3. 选择 `wtt` 数据库
4. 点击 `File` -> `Open SQL Script`
5. 选择 `sample_data.sql` 文件
6. 点击执行按钮 (⚡)

### 方法三：通过Navicat等工具

1. 连接到数据库
2. 打开查询窗口
3. 复制粘贴SQL内容
4. 执行查询

---

## ⚠️ 注意事项

### 1. 执行前检查
```sql
-- 查看当前数据量
SELECT COUNT(*) FROM admin;
SELECT COUNT(*) FROM player;
SELECT COUNT(*) FROM equipment;
SELECT COUNT(*) FROM user_action_log;
```

### 2. 如果已有数据

**选项A：清空后重新导入**
```sql
DELETE FROM user_action_log;
DELETE FROM equipment;
DELETE FROM player;
DELETE FROM admin;

-- 然后执行 sample_data.sql
```

**选项B：调整ID避免冲突**
- 修改SQL文件中的ID值
- 确保不与现有数据冲突

### 3. 自增ID重置（可选）
```sql
ALTER TABLE admin AUTO_INCREMENT = 4;
ALTER TABLE player AUTO_INCREMENT = 11;
```

---

## 🧪 测试示例

### 测试管理员登录
```bash
POST http://localhost:8080/api/adminlogin
Content-Type: application/x-www-form-urlencoded

username=admin&password=admin123
```

### 测试球员登录
```bash
POST http://localhost:8080/api/playerlogin
Content-Type: application/x-www-form-urlencoded

username=malong&password=ml2024
```

### 查询所有球员
```bash
GET http://localhost:8080/player/allPlayers
```

### 查询球员器材
```bash
GET http://localhost:8080/equipment/byPlayer/马龙
```

### AI问答测试
```bash
# 水平分析
GET http://localhost:8080/api/chat/analyze/1

# 打法建议
POST http://localhost:8080/api/chat/suggest/1?style=弧圈结合快攻
```

### 查询操作日志
```bash
# 所有日志
GET http://localhost:8080/api/log/all

# 按用户查询
GET http://localhost:8080/api/log/username/admin

# 按模块查询
GET http://localhost:8080/api/log/module/Player

# 统计总数
GET http://localhost:8080/api/log/count
```

---

## 📝 数据特点

### 真实性
- 使用真实球员姓名
- 使用真实球拍配置
- 合理的积分分布（2800-3500分）

### 多样性
- 不同水平的球员（高、中、低积分）
- 不同类型的装备配置
- 多种操作类型的日志

### 实用性
- 可用于功能测试
- 可用于性能测试
- 可用于演示展示

---

## 🔐 安全提示

⚠️ **重要提醒：**

1. **密码明文存储**：示例数据中密码为明文，实际项目必须加密（如BCrypt）
2. **手机号虚构**：所有手机号均为示例号码（13800138xxx段）
3. **生产环境勿用**：此数据仅用于开发测试，不可用于生产环境
4. **及时修改密码**：部署后应立即修改默认密码

---

## 📊 数据统计

执行示例数据后，各表数据量：
- admin: 3条
- player: 10条
- equipment: 10条
- user_action_log: 10条
- **总计: 33条记录**

---

## 🎯 下一步建议

1. **验证数据导入**
   ```sql
   SELECT * FROM player LIMIT 5;
   SELECT * FROM equipment LIMIT 5;
   ```

2. **测试API接口**
   - 使用Postman或浏览器测试各个接口
   - 验证数据是否正确返回

3. **体验AI功能**
   - 尝试对不同球员进行水平分析
   - 获取打法建议

4. **查看操作日志**
   - 执行一些操作后查看日志记录
   - 验证AOP切面是否正常工作

---

## 📞 问题反馈

如果在使用过程中遇到问题，请检查：
1. 数据库连接是否正常
2. 表结构是否正确创建
3. SQL语法是否有误
4. 是否有数据冲突

祝使用愉快！🎉
