# 电商商品管理与检索系统

## 项目简介

本项目基于Spring Boot框架构建的电商商品管理与检索系统，实现了商品管理、库存管理、商品搜索和数据分析等核心功能。

## 技术栈

- **Spring Boot 1.5.9.RELEASE** - 后端开发框架
- **MySQL** - 关系型数据库
- **Redis** - 缓存数据库
- **Elasticsearch** - 搜索引擎
- **MyBatis** - ORM框架
- **EasyExcel** - Excel文件处理
- **Swagger 2.8.0** - API文档生成

## 功能模块

### 1. 商品管理

#### SPU/SKU管理
- 商品基础信息增删改查
- 商品SKU多规格管理
- 商品图片和详情管理

#### 商品属性管理
- 商品属性分类管理
- 规格参数管理
- 销售属性管理

#### 商品状态管理
- 商品上架/下架
- 商品审核流程
- 新品推荐设置

### 2. 库存管理

#### 实时库存同步
- 多仓库库存管理
- 库存实时更新
- 缓存与数据库一致性保证

#### 库存预警设置
- 库存阈值配置
- 低库存商品查询
- 库存告警通知

#### 库存变更日志审计
- 库存变更全流程记录
- 操作人追踪
- 变更原因溯源

### 3. 商品搜索

#### Elasticsearch商品检索
- 全文搜索
- 关键词高亮显示
- 搜索结果排序

#### 多维度筛选
- 品牌筛选
- 分类筛选
- 价格区间筛选
- 属性筛选

### 4. 数据分析

#### 商品销售排行
- 按时间段统计商品销售TOP排行榜
- 支持自定义获取前N条数据
- 展示销售数量和销售金额
- 支持按品牌、分类筛选

#### 库存周转率分析
- 计算库存周转率（销售成本/平均库存）
- 支持按分类、品牌维度分析
- 展示平均库存、销售成本等详细数据

#### 商品上下架效率统计
- 统计上下架商品数量及占比
- 计算平均上架时长和平均审核时长
- 分析商品上架审核效率

#### 销售趋势分析
- 支持按日/周/月统计销售趋势
- 展示商品销量和销售额变化
- 可视化图表数据输出

## 项目结构

```
├── src/main/java/com/yasinyuan/testspring/
│   ├── Application.java                 # 项目启动类
│   ├── configurer/                      # 配置类
│   │   ├── RedisConfigurer.java         # Redis缓存配置
│   │   └── ...
│   ├── core/                            # 核心基础类
│   │   ├── AbstractService.java         # Service抽象类
│   │   ├── ResultGenerator.java         # 返回结果生成器
│   │   └── ...
│   ├── dao/                             # Mapper接口
│   │   ├── ProductMapper.java           # 商品Mapper
│   │   ├── ProductSkuMapper.java        # 商品SKU Mapper
│   │   └── ...
│   ├── elasticsearch/                   # ES相关代码
│   │   ├── ProductEs.java               # ES商品实体
│   │   └── ProductEsRepository.java     # ES Repository
│   ├── model/                           # 数据库实体
│   │   ├── Product.java                 # 商品SPU实体
│   │   ├── ProductSku.java              # 商品SKU实体
│   │   ├── Brand.java                   # 品牌实体
│   │   ├── ProductCategory.java         # 分类实体
│   │   ├── ProductAttribute.java        # 属性实体
│   │   └── StockChangeLog.java          # 库存日志实体
│   ├── service/                         # Service接口与实现
│   │   ├── ProductService.java          # 商品Service
│   │   ├── ProductSearchService.java    # 搜索Service
│   │   └── impl/
│   └── web/                             # Controller层
│       ├── ProductController.java       # 商品管理接口
│       └── ProductSearchController.java # 商品搜索接口
└── src/main/resources/
    ├── application-dev.properties        # 开发环境配置
    └── mapper/                           # MyBatis XML映射文件
```

## 接口文档

### 商品管理接口

- `POST /product` - 创建商品
- `PUT /product` - 更新商品
- `DELETE /product/{id}` - 删除商品
- `GET /product/{id}` - 获取商品详情
- `GET /product` - 分页查询商品列表
- `POST /product/publish/{id}` - 商品上架
- `POST /product/unpublish/{id}` - 商品下架
- `POST /product/verify/{id}` - 商品审核
- `GET /product/low-stock` - 查询低库存商品

### 商品搜索接口

- `GET /search/importAll` - 导入所有商品到ES
- `GET /search` - 高级搜索（多维度筛选）
- `GET /search/simple` - 简单关键词搜索

## 部署说明

### 环境要求

1.  JDK 1.8+
2.  MySQL 5.7+
3.  Redis 3.0+
4.  Elasticsearch 2.4+

### 配置修改

1.  修改`application-dev.properties`配置数据库、Redis和ES连接信息
2.  执行数据库初始化脚本（需要手动创建对应的数据表）
3.  启动Elasticsearch服务
4.  启动Redis服务
5.  启动项目

## 使用说明

1.  启动项目后访问Swagger文档：http://localhost/swagger-ui.html
2.  通过Swagger接口进行商品管理操作
3.  调用`/search/importAll`接口初始化商品搜索索引
4.  使用搜索接口进行商品检索

## 后续优化

1.  添加商品导出功能（基于EasyExcel）
2.  完善数据分析报表功能
3.  添加库存预警邮件通知
4.  实现商品搜索热点词统计
5.  添加商品评论系统
6.  实现商品秒杀功能
