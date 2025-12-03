# 电商支付与结算系统

## 项目概述

这是一个基于Spring Boot的电商支付与结算系统，提供了支付管理、交易管理、结算管理和对账功能等核心业务模块。

## 技术栈

- **Spring Boot**: 项目框架
- **Spring StateMachine**: 支付状态管理
- **Redis**: 缓存和分布式锁
- **MySQL**: 数据持久化
- **Maven**: 项目构建和依赖管理
- **Quartz**: 定时任务

## 功能模块

### 1. 支付管理

- **多支付渠道对接**: 支持微信支付、支付宝和银联支付
- **统一支付网关**: 提供统一的支付接口，简化业务接入
- **支付状态机管理**: 实现支付流程的状态转换（待支付→支付中→支付成功/失败）

### 2. 交易管理

- **交易记录存储与查询**: 保存所有交易记录，并提供查询功能
- **交易流水号生成**: 生成全局唯一的交易流水号
- **支付超时处理**: 自动关闭超时未支付的订单

### 3. 结算管理

- **商户资金账户管理**: 管理商户的资金账户信息
- **T+N结算规则配置**: 支持T+N的结算规则
- **手续费计算**: 计算交易手续费

### 4. 对账功能

- **渠道对账单下载**: 定时下载渠道对账单
- **自动对账**: 自动比对渠道账单和系统账单
- **对账差异处理**: 处理对账过程中出现的差异

## 核心类结构

### 支付管理模块

- **PayStatus.java**: 支付状态枚举类
- **PayEvent.java**: 支付事件枚举类
- **PayStateMachineConfig.java**: 支付状态机配置类
- **PayService.java**: 支付服务接口
- **PayServiceImpl.java**: 支付服务实现类
- **PayChannel.java**: 支付渠道枚举类
- **PayRequest.java**: 支付请求DTO
- **PayResult.java**: 支付结果DTO
- **PayCallbackResult.java**: 支付回调结果DTO
- **PayController.java**: 支付控制器

### 交易管理模块

- **TransactionRecord.java**: 交易记录实体类
- **TransactionService.java**: 交易服务接口
- **TransactionServiceImpl.java**: 交易服务实现类

### 结算管理模块

- **MerchantAccount.java**: 商户资金账户实体类
- **SettlementService.java**: 结算服务接口
- **SettlementServiceImpl.java**: 结算服务实现类

### 对账功能模块

- **ReconciliationRecord.java**: 对账记录实体类
- **ReconciliationService.java**: 对账服务接口
- **ReconciliationServiceImpl.java**: 对账服务实现类
- **ChannelTransactionRecord.java**: 渠道交易记录DTO
- **SystemTransactionRecord.java**: 系统交易记录DTO
- **ReconciliationResult.java**: 对账结果DTO

### 定时任务模块

- **ReconciliationSchedule.java**: 对账定时任务

## 项目配置

### 数据源配置

- **MySQL**: 配置在application-dev.properties中
- **Redis**: 配置在application-dev.properties中

### 定时任务配置

- 每日自动对账任务: 每天凌晨2点执行
- 对账差异检查任务: 每分钟执行一次

## 部署方式

1. 克隆项目到本地
2. 配置MySQL和Redis的连接信息
3. 运行Maven命令编译项目: `mvn compile`
4. 运行项目: `mvn spring-boot:run`

## API接口

### 支付接口

- **统一支付**: POST /pay/unifiedPay
- **查询支付状态**: GET /pay/queryPayStatus
- **支付回调**: POST /pay/callback/{payChannel}
- **关闭超时订单**: POST /pay/closeTimeoutOrder

## 总结

这个电商支付与结算系统提供了完整的支付流程管理、交易记录管理、结算管理和对账功能，采用了Spring Boot、Spring StateMachine、Redis和MySQL等技术栈，具有良好的可扩展性和可维护性。
