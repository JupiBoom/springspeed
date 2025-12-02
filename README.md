# 电商用户行为分析与用户分群系统

## 项目概述

这是一个基于Spring Boot的电商用户行为分析与用户分群系统，旨在帮助电商平台深入了解用户行为，优化用户体验，提高转化率。

## 功能模块

### 1. 行为追踪
- 关键行为埋点（浏览/点击/加购/下单）
- 用户路径记录（页面流分析）
- 会话管理（30分钟超时）

### 2. 漏斗分析
- 自定义转化漏斗（如首页→详情页→加购→下单）
- 转化率实时计算
- 流失用户定位

### 3. 用户分群
- RFM模型分群（最近购买/购买频率/购买金额）
- 用户生命周期划分（新客/活跃/沉默/流失）
- 自定义标签分群

### 4. 报表展示
- 用户行为日报
- 漏斗分析报表
- 用户分群明细导出

## 技术栈

- **后端框架**: Spring Boot 2.7.12
- **数据访问**: Spring Data JPA
- **缓存**: Redis（会话存储）
- **搜索引擎**: Elasticsearch 7.17.12（行为日志存储）
- **数据库**: MySQL
- **定时任务**: Quartz
- **报表导出**: Apache POI

## 项目结构

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── ecommerce
│   │   │           ├── config          # 配置类
│   │   │           ├── controller       # 控制器
│   │   │           ├── entity           # 实体类
│   │   │           ├── job              # 定时任务
│   │   │           ├── repository       # 数据访问层
│   │   │           ├── service          # 服务层
│   │   │           │   └── impl        # 服务实现类
│   │   │           └── UserBehaviorAnalysisApplication.java  # 主应用类
│   │   └── resources
│   │       ├── application.yml          # 应用配置文件
│   │       └── static                   # 静态资源
│   └── test
│       └── java
│           └── com
│               └── ecommerce             # 测试类
├── pom.xml                                 # Maven依赖配置
└── README.md                               # 项目说明文档
```

## 配置说明

### 1. 数据库配置

在`application.yml`文件中配置MySQL数据库连接：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ecommerce_analysis?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 2. Redis配置

在`application.yml`文件中配置Redis连接：

```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: 
    database: 0
    timeout: 10000ms
```

### 3. Elasticsearch配置

在`application.yml`文件中配置Elasticsearch连接：

```yaml
spring:
  elasticsearch:
    rest:
      uris: http://localhost:9200
      username: 
      password: 
```

### 4. Quartz配置

在`application.yml`文件中配置Quartz定时任务：

```yaml
spring:
  quartz:
    job-store-type: jdbc
    jdbc:
      initialize-schema: never
    properties:
      org:
        quartz:
          scheduler:
            instanceName: EcommerceScheduler
            instanceId: AUTO
          threadPool:
            class: org.quartz.simpl.SimpleThreadPool
            threadCount: 5
            threadPriority: 5
          jobStore:
            class: org.quartz.impl.jdbcjobstore.JobStoreTX
            driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate
            tablePrefix: QRTZ_
            isClustered: false
            clusterCheckinInterval: 10000
            useProperties: false
```

## 使用方法

### 1. 启动项目

运行`UserBehaviorAnalysisApplication.java`类的`main`方法启动项目。

### 2. 访问API

项目启动后，可通过以下URL访问API：

- 行为追踪API：`http://localhost:8080/api/behavior-tracking`
- 漏斗分析API：`http://localhost:8080/api/funnel-analysis`
- 用户分群API：`http://localhost:8080/api/user-segmentation`
- 报表API：`http://localhost:8080/api/reports`

### 3. 定时任务

项目配置了一个每天凌晨1点执行的定时任务，用于自动生成用户行为日报。

## API文档

### 1. 行为追踪API

#### 记录用户行为

```http
POST /api/behavior-tracking/track
Content-Type: application/json

{
  "userId": 1,
  "sessionId": "abc123",
  "behaviorType": "browse",
  "pageUrl": "/product/123",
  "referrerUrl": "/category/electronics",
  "ipAddress": "192.168.1.100",
  "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
}
```

#### 查询用户行为日志

```http
GET /api/behavior-tracking/user/{userId}?startDate=2023-01-01 00:00:00&endDate=2023-01-31 23:59:59
```

#### 查询用户行为路径

```http
GET /api/behavior-tracking/session/{sessionId}
```

### 2. 漏斗分析API

#### 创建漏斗

```http
POST /api/funnel-analysis/funnels
Content-Type: application/json

{
  "funnelName": "首页→详情页→加购→下单",
  "description": "从首页浏览到下单的转化漏斗",
  "funnelSteps": [
    {"stepName": "首页", "pageUrl": "/"},
    {"stepName": "详情页", "pageUrl": "/product/"},
    {"stepName": "加购", "behaviorType": "add_to_cart"},
    {"stepName": "下单", "behaviorType": "place_order"}
  ],
  "isActive": true
}
```

#### 分析漏斗转化情况

```http
POST /api/funnel-analysis/analyze/{funnelId}?startDate=2023-01-01 00:00:00&endDate=2023-01-31 23:59:59
```

#### 定位流失用户

```http
GET /api/funnel-analysis/churned-users/{funnelId}?fromStep=1&toStep=2&startDate=2023-01-01 00:00:00&endDate=2023-01-31 23:59:59
```

### 3. 用户分群API

#### 基于RFM模型分群

```http
POST /api/user-segmentation/rfm-segmentation?recencyThreshold=30&frequencyThreshold=5&monetaryThreshold=1000
```

#### 基于用户生命周期分群

```http
POST /api/user-segmentation/lifecycle-segmentation?newUserDays=7&activeUserDays=30&silentUserDays=90
```

#### 创建自定义用户分群

```http
POST /api/user-segmentation/custom-segmentation?segmentName=高价值用户&userIds=[1,2,3,4,5]&createdBy=admin
```

### 4. 报表API

#### 生成用户行为日报

```http
POST /api/reports/daily?reportDate=2023-01-31
```

#### 导出用户行为日报到Excel

```http
GET /api/reports/export/daily?startDate=2023-01-01&endDate=2023-01-31
```

#### 导出漏斗分析报表到Excel

```http
GET /api/reports/export/funnel/{funnelId}?startDate=2023-01-01&endDate=2023-01-31
```

## 开发说明

### 1. 数据库初始化

项目启动前，需要创建MySQL数据库`ecommerce_analysis`，并执行数据库初始化脚本。

### 2. Elasticsearch索引

项目启动后，会自动创建Elasticsearch索引`user_behavior_log`，用于存储用户行为日志。

### 3. 定时任务启动

项目启动后，定时任务会自动启动，每天凌晨1点执行用户行为日报生成任务。

## 部署说明

### 1. 环境要求

- Java 8或以上
- MySQL 5.7或以上
- Redis 5.0或以上
- Elasticsearch 7.17或以上

### 2. 部署步骤

1. 克隆项目到本地
2. 配置`application.yml`文件中的数据库、Redis和Elasticsearch连接信息
3. 编译项目：`mvn clean compile`
4. 打包项目：`mvn clean package`
5. 运行项目：`java -jar user-behavior-analysis-1.0.0.jar`

## 许可证

MIT License
