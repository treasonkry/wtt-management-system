# WTT乒乓球赛事管理系统

全栈式乒乓球俱乐部数字化管理平台，支持球员管理、比赛记录、AI智能分析、操作日志审计等功能。

## 技术栈

### 后端
- Java 17 + Spring Boot 3.3.0
- MyBatis 3.0.4
- MySQL 8.0
- Redis

### 前端
- Vue 3.5 + Vite 8.0
- Vue Router 4.6
- Pinia 3.0
- Axios 1.16

## 项目结构

```
wtt-management-system/
├── src/                        # Java后端源码
│   └── main/
│       ├── java/com/wtt/demo/
│       │   ├── controller/      # REST API控制器
│       │   ├── service/         # 业务逻辑层
│       │   ├── mapper/          # MyBatis Mapper接口
│       │   ├── pojo/           # 数据实体类
│       │   ├── config/         # 配置类
│       │   ├── aspect/         # AOP切面
│       │   └── annotation/     # 自定义注解
│       └── resources/
│           ├── mapper/          # MyBatis XML映射文件
│           └── sql/            # 数据库脚本
├── wtt-frontend/               # Vue前端项目
│   └── src/
│       ├── views/             # 页面组件
│       ├── api/               # API封装
│       ├── router/            # 路由配置
│       └── stores/            # 状态管理
└── target/classes/sql/       # 编译后的SQL脚本
```

## 快速开始

### 环境要求

- JDK 17+
- Node.js 18+
- MySQL 8.0+

### 后端启动

```bash
# 1. 创建数据库
mysql -u root -p < src/main/resources/sql/match.sql

# 2. 修改数据库配置
# 编辑 src/main/resources/application.yaml

# 3. 启动后端
mvn spring-boot:run
```

### 前端启动

```bash
cd wtt-frontend
npm install
npm run dev
```

### 访问

- 前端：http://localhost:5173
- 后端API：http://localhost:8080

## 功能模块

| 模块 | 说明 |
|------|------|
| 用户管理 | 管理员/球员双角色登录体系 |
| 球员管理 | 增删改查、器材配置 |
| 比赛管理 | 比赛记录、大比分/小比分录入 |
| AI分析 | 水平分析、打法建议、自由问答 |
| 操作日志 | AOP自动记录、多维度查询 |

## License

MIT