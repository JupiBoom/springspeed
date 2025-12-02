package com.springspeed.userbehavior.service.impl;

import com.springspeed.userbehavior.entity.UserBehavior;
import com.springspeed.userbehavior.repository.UserBehaviorRepository;
import com.springspeed.userbehavior.service.UserBehaviorService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.aggregations.metrics.ValueCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户行为服务实现类
 * 实现用户行为相关的业务逻辑
 */
@Service
@Transactional(readOnly = true)
public class UserBehaviorServiceImpl implements UserBehaviorService {

    @Autowired
    private UserBehaviorRepository userBehaviorRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 记录用户行为
     * @param userBehavior 用户行为实体
     * @return 记录的用户行为
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserBehavior recordUserBehavior(UserBehavior userBehavior) {
        if (userBehavior == null) {
            throw new IllegalArgumentException("用户行为实体不能为空");
        }

        // 设置默认值
        if (userBehavior.getBehaviorTime() == null) {
            userBehavior.setBehaviorTime(new Date());
        }

        if (userBehavior.getDeviceType() == null) {
            userBehavior.setDeviceType("unknown");
        }

        if (userBehavior.getSourceChannel() == null) {
            userBehavior.setSourceChannel("unknown");
        }

        // 保存用户行为
        return userBehaviorRepository.save(userBehavior);
    }

    /**
     * 批量记录用户行为
     * @param userBehaviors 用户行为实体列表
     * @return 记录的用户行为列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<UserBehavior> batchRecordUserBehaviors(List<UserBehavior> userBehaviors) {
        if (userBehaviors == null || userBehaviors.isEmpty()) {
            throw new IllegalArgumentException("用户行为实体列表不能为空");
        }

        // 设置默认值
        Date now = new Date();
        for (UserBehavior userBehavior : userBehaviors) {
            if (userBehavior.getBehaviorTime() == null) {
                userBehavior.setBehaviorTime(now);
            }

            if (userBehavior.getDeviceType() == null) {
                userBehavior.setDeviceType("unknown");
            }

            if (userBehavior.getSourceChannel() == null) {
                userBehavior.setSourceChannel("unknown");
            }
        }

        // 批量保存用户行为
        return (List<UserBehavior>) userBehaviorRepository.saveAll(userBehaviors);
    }

    /**
     * 根据行为ID查询用户行为
     * @param behaviorId 行为ID
     * @return 用户行为
     */
    @Override
    public UserBehavior getUserBehaviorById(String behaviorId) {
        if (behaviorId == null || behaviorId.isEmpty()) {
            throw new IllegalArgumentException("行为ID不能为空");
        }

        return userBehaviorRepository.findById(behaviorId).orElse(null);
    }

    /**
     * 根据用户ID和行为类型查询用户行为
     * @param userId 用户ID
     * @param behaviorType 行为类型
     * @return 用户行为列表
     */
    @Override
    public List<UserBehavior> getUserBehaviorsByUserIdAndType(String userId, String behaviorType) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        if (behaviorType == null || behaviorType.isEmpty()) {
            throw new IllegalArgumentException("行为类型不能为空");
        }

        return userBehaviorRepository.findByUserIdAndBehaviorType(userId, behaviorType);
    }

    /**
     * 根据会话ID查询用户行为
     * @param sessionId 会话ID
     * @return 用户行为列表
     */
    @Override
    public List<UserBehavior> getUserBehaviorsBySessionId(String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            throw new IllegalArgumentException("会话ID不能为空");
        }

        return userBehaviorRepository.findBySessionId(sessionId);
    }

    /**
     * 根据会话ID查询用户行为（分页）
     * @param sessionId 会话ID
     * @param pageable 分页参数
     * @return 用户行为分页列表
     */
    @Override
    public Page<UserBehavior> getUserBehaviorsBySessionId(String sessionId, Pageable pageable) {
        if (sessionId == null || sessionId.isEmpty()) {
            throw new IllegalArgumentException("会话ID不能为空");
        }

        return userBehaviorRepository.findBySessionId(sessionId, pageable);
    }

    /**
     * 根据时间范围查询用户行为
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param pageable 分页参数
     * @return 用户行为分页列表
     */
    @Override
    public Page<UserBehavior> getUserBehaviorsByTimeRange(Date startDate, Date endDate, Pageable pageable) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("开始时间和结束时间不能为空");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        return userBehaviorRepository.findByBehaviorTimeBetween(startDate, endDate, pageable);
    }

    /**
     * 查询行为类型统计数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 行为类型统计数据
     */
    @Override
    public List<Map<String, Object>> getBehaviorTypeStatistics(Date startDate, Date endDate) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        if (startDate != null && endDate != null) {
            queryBuilder.must(QueryBuilders.rangeQuery("behaviorTime").gte(startDate).lte(endDate));
        }

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .addAggregation(AggregationBuilders.terms("behaviorType").field("behaviorType").size(100));

        SearchHits<UserBehavior> searchHits = elasticsearchRestTemplate.search(searchQueryBuilder.build(), UserBehavior.class);
        Terms behaviorTypeAgg = ((Aggregations) searchHits.getAggregations().aggregations()).get("behaviorType");

        List<Map<String, Object>> typeStatistics = new ArrayList<>();
        if (behaviorTypeAgg != null) {
            for (Terms.Bucket bucket : behaviorTypeAgg.getBuckets()) {
                Map<String, Object> typeData = new HashMap<>();
                typeData.put("behaviorType", bucket.getKeyAsString());
                typeData.put("count", bucket.getDocCount());
                typeStatistics.add(typeData);
            }
        }

        return typeStatistics;
    }

    /**
     * 根据用户ID查询用户行为（分页）
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 用户行为分页列表
     */
    @Override
    public Page<UserBehavior> getUserBehaviorsByUserId(String userId, Pageable pageable) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        return userBehaviorRepository.findByUserId(userId, pageable);
    }

    /**
     * 根据行为类型查询用户行为（分页）
     * @param behaviorType 行为类型
     * @param pageable 分页参数
     * @return 用户行为分页列表
     */
    @Override
    public Page<UserBehavior> getUserBehaviorsByBehaviorType(String behaviorType, Pageable pageable) {
        if (behaviorType == null || behaviorType.isEmpty()) {
            throw new IllegalArgumentException("行为类型不能为空");
        }

        return userBehaviorRepository.findByBehaviorType(behaviorType, pageable);
    }

    /**
     * 根据行为类型和时间范围查询用户行为
     * @param behaviorType 行为类型
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param pageable 分页参数
     * @return 用户行为分页列表
     */
    @Override
    public Page<UserBehavior> getUserBehaviorsByTypeAndTimeRange(String behaviorType, Date startDate, Date endDate, Pageable pageable) {
        if (behaviorType == null || behaviorType.isEmpty()) {
            throw new IllegalArgumentException("行为类型不能为空");
        }

        if (startDate == null) {
            throw new IllegalArgumentException("开始时间不能为空");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("结束时间不能为空");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        return userBehaviorRepository.findByBehaviorTypeAndBehaviorTimeBetween(behaviorType, startDate, endDate, pageable);
    }

    /**
     * 查询用户行为统计数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 统计数据Map
     */
    @Override
    public Map<String, Object> getUserBehaviorStatistics(Date startDate, Date endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("开始时间不能为空");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("结束时间不能为空");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        // 构建查询条件
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.filter(QueryBuilders.rangeQuery("behaviorTime").gte(startDate).lte(endDate));

        // 构建聚合查询
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .addAggregation(AggregationBuilders.count("totalBehaviorCount").field("behaviorId"))
                .addAggregation(AggregationBuilders.cardinality("uniqueUserIdCount").field("userId"))
                .addAggregation(AggregationBuilders.cardinality("uniqueSessionIdCount").field("sessionId"))
                .addAggregation(AggregationBuilders.terms("behaviorTypeCount").field("behaviorType").size(10));

        // 执行查询
        SearchHits<UserBehavior> searchHits = elasticsearchRestTemplate.search(searchQueryBuilder.build(), UserBehavior.class);

        // 处理查询结果
        Map<String, Object> statistics = new HashMap<>();

        // 总行为次数
        long totalBehaviorCount = searchHits.getTotalHits();
        statistics.put("totalBehaviorCount", totalBehaviorCount);

        // 独立用户数
        Cardinality uniqueUserIdCountAgg = ((Aggregations) searchHits.getAggregations().aggregations()).get("uniqueUserIdCount");
        long uniqueUserIdCount = uniqueUserIdCountAgg == null ? 0 : uniqueUserIdCountAgg.getValue();
        statistics.put("uniqueUserIdCount", uniqueUserIdCount);

        // 独立会话数
        Cardinality uniqueSessionIdCountAgg = ((Aggregations) searchHits.getAggregations().aggregations()).get("uniqueSessionIdCount");
        long uniqueSessionIdCount = uniqueSessionIdCountAgg == null ? 0 : uniqueSessionIdCountAgg.getValue();
        statistics.put("uniqueSessionIdCount", uniqueSessionIdCount);

        // 平均行为次数/用户
        double avgBehaviorCountPerUser = uniqueUserIdCount > 0 ? (double) totalBehaviorCount / uniqueUserIdCount : 0;
        statistics.put("avgBehaviorCountPerUser", avgBehaviorCountPerUser);

        // 平均行为次数/会话
        double avgBehaviorCountPerSession = uniqueSessionIdCount > 0 ? (double) totalBehaviorCount / uniqueSessionIdCount : 0;
        statistics.put("avgBehaviorCountPerSession", avgBehaviorCountPerSession);

        // 各行为类型次数
        Terms behaviorTypeCountAgg = ((Aggregations) searchHits.getAggregations().aggregations()).get("behaviorTypeCount");
        List<Map<String, Object>> behaviorTypeCountList = new ArrayList<>();
        if (behaviorTypeCountAgg != null) {
            for (Terms.Bucket bucket : behaviorTypeCountAgg.getBuckets()) {
                Map<String, Object> behaviorTypeCount = new HashMap<>();
                behaviorTypeCount.put("behaviorType", bucket.getKeyAsString());
                behaviorTypeCount.put("count", bucket.getDocCount());
                behaviorTypeCount.put("percentage", (double) bucket.getDocCount() / totalBehaviorCount * 100);
                behaviorTypeCountList.add(behaviorTypeCount);
            }
        }
        statistics.put("behaviorTypeCountList", behaviorTypeCountList);

        return statistics;
    }

    /**
     * 查询用户路径分析数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param minCount 最小访问次数
     * @return 用户路径分析数据
     */
    @Override
    public List<Map<String, Object>> getUserPathAnalysis(Date startDate, Date endDate, Integer minCount) {
        if (startDate == null) {
            throw new IllegalArgumentException("开始时间不能为空");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("结束时间不能为空");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        if (minCount == null || minCount < 1) {
            minCount = 1;
        }

        // 构建查询条件
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.filter(QueryBuilders.rangeQuery("behaviorTime").gte(startDate).lte(endDate));

        // 构建聚合查询
        // 构建聚合查询
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(queryBuilder);
        
        // 构建sessionId聚合
        TermsAggregationBuilder sessionIdAgg = AggregationBuilders.terms("sessionId")
                .field("sessionId")
                .size(10000);
        
        // 构建page聚合
        TermsAggregationBuilder pageAgg = AggregationBuilders.terms("page")
                .field("page")
                .size(100);
        
        // 构建max_behavior_time聚合
        MaxAggregationBuilder maxBehaviorTimeAgg = AggregationBuilders.max("max_behavior_time")
                .field("behaviorTime");
        
        // 添加聚合
        pageAgg.subAggregation(maxBehaviorTimeAgg);
        pageAgg.order(Terms.Order.aggregation("max_behavior_time", false));
        sessionIdAgg.subAggregation(pageAgg);
        searchQueryBuilder.addAggregation(sessionIdAgg);

        // 执行查询
        SearchHits<UserBehavior> searchHits = elasticsearchRestTemplate.search(searchQueryBuilder.build(), UserBehavior.class);

        // 处理查询结果
        Map<String, List<String>> sessionPageMap = new HashMap<>();

        Terms sessionIdAgg = ((Aggregations) searchHits.getAggregations().aggregations()).get("sessionId");
        if (sessionIdAgg != null) {
            for (Terms.Bucket sessionBucket : sessionIdAgg.getBuckets()) {
                String sessionId = sessionBucket.getKeyAsString();

                Terms pageAgg = sessionBucket.getAggregations().get("page");
                if (pageAgg != null) {
                    List<String> pages = new ArrayList<>();
                    for (Terms.Bucket pageBucket : pageAgg.getBuckets()) {
                        pages.add(pageBucket.getKeyAsString());
                    }
                    sessionPageMap.put(sessionId, pages);
                }
            }
        }

        // 统计页面跳转次数
        Map<String, Map<String, Integer>> pageTransitionCountMap = new HashMap<>();

        for (List<String> pages : sessionPageMap.values()) {
            if (pages.size() < 2) {
                continue;
            }

            for (int i = 0; i < pages.size() - 1; i++) {
                String fromPage = pages.get(i);
                String toPage = pages.get(i + 1);

                pageTransitionCountMap.computeIfAbsent(fromPage, k -> new HashMap<>());
                pageTransitionCountMap.get(fromPage).compute(toPage, (k, v) -> v == null ? 1 : v + 1);
            }
        }

        // 转换为结果列表
        List<Map<String, Object>> transitionList = new ArrayList<>();

        for (Map.Entry<String, Map<String, Integer>> fromPageEntry : pageTransitionCountMap.entrySet()) {
            String fromPage = fromPageEntry.getKey();

            for (Map.Entry<String, Integer> toPageEntry : fromPageEntry.getValue().entrySet()) {
                String toPage = toPageEntry.getKey();
                int count = toPageEntry.getValue();

                if (count >= minCount) {
                    Map<String, Object> transition = new HashMap<>();
                    transition.put("fromPage", fromPage);
                    transition.put("toPage", toPage);
                    transition.put("count", count);
                    transitionList.add(transition);
                }
            }
        }

        // 按跳转次数降序排序
        transitionList.sort((a, b) -> Integer.compare((int) b.get("count"), (int) a.get("count")));

        return transitionList;
    }

    /**
     * 查询页面访问统计数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param topN 前N条数据
     * @return 页面访问统计数据
     */
    @Override
    public List<Map<String, Object>> getPageVisitStatistics(Date startDate, Date endDate, Integer topN) {
        if (startDate == null) {
            throw new IllegalArgumentException("开始时间不能为空");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("结束时间不能为空");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        if (topN == null || topN < 1) {
            topN = 10;
        }

        // 构建查询条件
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.filter(QueryBuilders.rangeQuery("behaviorTime").gte(startDate).lte(endDate));

        // 构建聚合查询
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .addAggregation(AggregationBuilders.terms("page").field("page").size(topN)
                        .subAggregation(AggregationBuilders.count("visitCount").field("behaviorId"))
                        .subAggregation(AggregationBuilders.cardinality("uniqueUserIdCount").field("userId"))
                        .subAggregation(AggregationBuilders.cardinality("uniqueSessionIdCount").field("sessionId")));

        // 执行查询
        SearchHits<UserBehavior> searchHits = elasticsearchRestTemplate.search(searchQueryBuilder.build(), UserBehavior.class);

        // 处理查询结果
        List<Map<String, Object>> pageVisitStatistics = new ArrayList<>();

        Terms pageAgg = ((Aggregations) searchHits.getAggregations().aggregations()).get("page");
        if (pageAgg != null) {
            for (Terms.Bucket pageBucket : pageAgg.getBuckets()) {
                String page = pageBucket.getKeyAsString();

                // 访问次数
                ValueCount visitCountAgg = (ValueCount) pageBucket.getAggregations().get("visitCount");
                long visitCount = visitCountAgg == null ? 0 : visitCountAgg.getValue();

                // 独立用户数
                Cardinality uniqueUserIdCountAgg = (Cardinality) pageBucket.getAggregations().get("uniqueUserIdCount");
                long uniqueUserIdCount = uniqueUserIdCountAgg == null ? 0 : uniqueUserIdCountAgg.getValue();

                // 独立会话数
                Cardinality uniqueSessionIdCountAgg = (Cardinality) pageBucket.getAggregations().get("uniqueSessionIdCount");
                long uniqueSessionIdCount = uniqueSessionIdCountAgg == null ? 0 : uniqueSessionIdCountAgg.getValue();

                // 平均访问次数/用户
                double avgVisitCountPerUser = uniqueUserIdCount > 0 ? (double) visitCount / uniqueUserIdCount : 0;

                // 平均访问次数/会话
                double avgVisitCountPerSession = uniqueSessionIdCount > 0 ? (double) visitCount / uniqueSessionIdCount : 0;

                Map<String, Object> pageStatistics = new HashMap<>();
                pageStatistics.put("page", page);
                pageStatistics.put("visitCount", visitCount);
                pageStatistics.put("uniqueUserIdCount", uniqueUserIdCount);
                pageStatistics.put("uniqueSessionIdCount", uniqueSessionIdCount);
                pageStatistics.put("avgVisitCountPerUser", avgVisitCountPerUser);
                pageStatistics.put("avgVisitCountPerSession", avgVisitCountPerSession);

                pageVisitStatistics.add(pageStatistics);
            }
        }

        return pageVisitStatistics;
    }

    /**
     * 查询商品行为统计数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param topN 前N条数据
     * @return 商品行为统计数据
     */
    @Override
    public List<Map<String, Object>> getProductBehaviorStatistics(Date startDate, Date endDate, Integer topN) {
        if (startDate == null) {
            throw new IllegalArgumentException("开始时间不能为空");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("结束时间不能为空");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        if (topN == null || topN < 1) {
            topN = 10;
        }

        // 构建查询条件
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.filter(QueryBuilders.rangeQuery("behaviorTime").gte(startDate).lte(endDate));
        queryBuilder.filter(QueryBuilders.existsQuery("productId"));

        // 构建聚合查询
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .addAggregation(AggregationBuilders.terms("productId").field("productId").size(topN)
                        .subAggregation(AggregationBuilders.count("totalBehaviorCount").field("behaviorId"))
                        .subAggregation(AggregationBuilders.terms("behaviorTypeCount").field("behaviorType").size(10)));

        // 执行查询
        SearchHits<UserBehavior> searchHits = elasticsearchRestTemplate.search(searchQueryBuilder.build(), UserBehavior.class);

        // 处理查询结果
        List<Map<String, Object>> productBehaviorStatistics = new ArrayList<>();

        Terms productIdAgg = ((Aggregations) searchHits.getAggregations().aggregations()).get("productId");
        if (productIdAgg != null) {
            for (Terms.Bucket productIdBucket : productIdAgg.getBuckets()) {
                String productId = productIdBucket.getKeyAsString();

                // 总行为次数
                long totalBehaviorCount = productIdBucket.getDocCount();

                // 各行为类型次数
                Terms behaviorTypeCountAgg = (Terms) productIdBucket.getAggregations().get("behaviorTypeCount");
                Map<String, Long> behaviorTypeCountMap = new HashMap<>();
                if (behaviorTypeCountAgg != null) {
                    for (Terms.Bucket behaviorTypeBucket : behaviorTypeCountAgg.getBuckets()) {
                        behaviorTypeCountMap.put(behaviorTypeBucket.getKeyAsString(), behaviorTypeBucket.getDocCount());
                    }
                }

                Map<String, Object> productStatistics = new HashMap<>();
                productStatistics.put("productId", productId);
                productStatistics.put("totalBehaviorCount", totalBehaviorCount);
                productStatistics.put("behaviorTypeCountMap", behaviorTypeCountMap);

                // 计算各行为类型占比
                Map<String, Double> behaviorTypePercentageMap = new HashMap<>();
                for (Map.Entry<String, Long> entry : behaviorTypeCountMap.entrySet()) {
                    behaviorTypePercentageMap.put(entry.getKey(), (double) entry.getValue() / totalBehaviorCount * 100);
                }
                productStatistics.put("behaviorTypePercentageMap", behaviorTypePercentageMap);

                productBehaviorStatistics.add(productStatistics);
            }
        }

        return productBehaviorStatistics;
    }

    /**
     * 查询用户行为漏斗数据
     * @param funnelSteps 漏斗步骤
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 漏斗数据
     */
    @Override
    public List<Map<String, Object>> getUserBehaviorFunnel(List<String> funnelSteps, Date startDate, Date endDate) {
        if (funnelSteps == null || funnelSteps.isEmpty()) {
            throw new IllegalArgumentException("漏斗步骤不能为空");
        }

        if (startDate == null) {
            throw new IllegalArgumentException("开始时间不能为空");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("结束时间不能为空");
        }

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("开始时间不能晚于结束时间");
        }

        // 处理漏斗步骤，支持页面或行为类型
        List<Map<String, Object>> funnelData = new ArrayList<>();
        Set<String> previousUserIds = new HashSet<>();

        for (int i = 0; i < funnelSteps.size(); i++) {
            String step = funnelSteps.get(i);

            // 构建查询条件
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            queryBuilder.filter(QueryBuilders.rangeQuery("behaviorTime").gte(startDate).lte(endDate));

            // 检查步骤是否为页面或行为类型
            if (step.startsWith("page:")) {
                String page = step.substring(5);
                queryBuilder.filter(QueryBuilders.termQuery("page", page));
            } else if (step.startsWith("behavior:")) {
                String behaviorType = step.substring(9);
                queryBuilder.filter(QueryBuilders.termQuery("behaviorType", behaviorType));
            } else {
                // 默认按页面查询
                queryBuilder.filter(QueryBuilders.termQuery("page", step));
            }

            // 如果不是第一步，只查询之前步骤中出现过的用户
            if (i > 0 && !previousUserIds.isEmpty()) {
                queryBuilder.filter(QueryBuilders.termsQuery("userId", previousUserIds));
            }

            // 构建聚合查询
            NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
                    .withQuery(queryBuilder)
                    .addAggregation(AggregationBuilders.cardinality("uniqueUserIdCount").field("userId"));

            // 执行查询
            SearchHits<UserBehavior> searchHits = elasticsearchRestTemplate.search(searchQueryBuilder.build(), UserBehavior.class);

            // 处理查询结果
            Cardinality uniqueUserIdCountAgg = ((Aggregations) searchHits.getAggregations().aggregations()).get("uniqueUserIdCount");
            long uniqueUserIdCount = uniqueUserIdCountAgg == null ? 0 : uniqueUserIdCountAgg.getValue();

            // 更新之前步骤的用户ID集合
            if (i == 0) {
                // 第一步查询所有用户ID
                NativeSearchQueryBuilder userIdQueryBuilder = new NativeSearchQueryBuilder()
                        .withQuery(queryBuilder)
                        .addAggregation(AggregationBuilders.terms("userId").field("userId").size(10000));

                SearchHits<UserBehavior> userIdSearchHits = elasticsearchRestTemplate.search(userIdQueryBuilder.build(), UserBehavior.class);
                Terms userIdAgg = ((Aggregations) userIdSearchHits.getAggregations().aggregations()).get("userId");
                if (userIdAgg != null) {
                    for (Terms.Bucket userIdBucket : userIdAgg.getBuckets()) {
                        previousUserIds.add(userIdBucket.getKeyAsString());
                    }
                }
            } else {
                // 后续步骤只保留当前步骤中出现过的用户ID
                Set<String> currentUserIds = new HashSet<>();

                NativeSearchQueryBuilder userIdQueryBuilder = new NativeSearchQueryBuilder()
                        .withQuery(queryBuilder)
                        .addAggregation(AggregationBuilders.terms("userId").field("userId").size(10000));

                SearchHits<UserBehavior> userIdSearchHits = elasticsearchRestTemplate.search(userIdQueryBuilder.build(), UserBehavior.class);
                Terms userIdAgg = ((Aggregations) userIdSearchHits.getAggregations().aggregations()).get("userId");
                if (userIdAgg != null) {
                    for (Terms.Bucket userIdBucket : userIdAgg.getBuckets()) {
                        currentUserIds.add(userIdBucket.getKeyAsString());
                    }
                }

                previousUserIds = currentUserIds;
            }

            // 计算转化率
            double conversionRate = 0;
            if (i > 0 && !funnelData.isEmpty()) {
                long previousStepCount = (long) funnelData.get(i - 1).get("uniqueUserIdCount");
                if (previousStepCount > 0) {
                    conversionRate = (double) uniqueUserIdCount / previousStepCount * 100;
                }
            }

            // 计算累计转化率
            double cumulativeConversionRate = 0;
            if (!funnelData.isEmpty()) {
                long firstStepCount = (long) funnelData.get(0).get("uniqueUserIdCount");
                if (firstStepCount > 0) {
                    cumulativeConversionRate = (double) uniqueUserIdCount / firstStepCount * 100;
                }
            }

            // 添加漏斗数据
            Map<String, Object> stepData = new HashMap<>();
            stepData.put("step", i + 1);
            stepData.put("stepName", step);
            stepData.put("uniqueUserIdCount", uniqueUserIdCount);
            stepData.put("conversionRate", conversionRate);
            stepData.put("cumulativeConversionRate", cumulativeConversionRate);

            funnelData.add(stepData);
        }

        return funnelData;
    }

    /**
     * 分析用户行为漏斗
     * @param funnelSteps 漏斗步骤
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 漏斗分析数据
     */
    @Override
    public Map<String, Object> analyzeFunnel(List<String> funnelSteps, Date startDate, Date endDate) {
        // 调用getUserBehaviorFunnel方法获取漏斗数据
        List<Map<String, Object>> funnelData = getUserBehaviorFunnel(funnelSteps, startDate, endDate);

        // 计算漏斗的整体转化率
        double overallConversionRate = 0;
        if (funnelData.size() >= 2) {
            long firstStepCount = (long) funnelData.get(0).get("uniqueUserIdCount");
            long lastStepCount = (long) funnelData.get(funnelData.size() - 1).get("uniqueUserIdCount");
            if (firstStepCount > 0) {
                overallConversionRate = (double) lastStepCount / firstStepCount * 100;
            }
        }

        // 分析漏斗的瓶颈
        Map<String, Object> bottleneckAnalysis = new HashMap<>();
        if (funnelData.size() >= 2) {
            double minConversionRate = Double.MAX_VALUE;
            int bottleneckStep = 0;

            for (int i = 1; i < funnelData.size(); i++) {
                double conversionRate = (double) funnelData.get(i).get("conversionRate");
                if (conversionRate < minConversionRate) {
                    minConversionRate = conversionRate;
                    bottleneckStep = i;
                }
            }

            bottleneckAnalysis.put("bottleneckStep", bottleneckStep);
            bottleneckAnalysis.put("bottleneckStepName", funnelData.get(bottleneckStep).get("stepName"));
            bottleneckAnalysis.put("bottleneckConversionRate", minConversionRate);
        }

        // 构建漏斗分析结果
        Map<String, Object> funnelAnalysis = new HashMap<>();
        funnelAnalysis.put("funnelData", funnelData);
        funnelAnalysis.put("overallConversionRate", overallConversionRate);
        funnelAnalysis.put("bottleneckAnalysis", bottleneckAnalysis);

        return funnelAnalysis;
    }

    /**
     * 获取用户行为日报
     * @param reportDate 报告日期
     * @return 日报数据
     */
    @Override
    public Map<String, Object> getUserBehaviorDailyReport(Date reportDate) {
        // 设置报告日期的时间范围（当天的00:00:00到23:59:59）
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reportDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endDate = calendar.getTime();

        // 获取用户行为统计数据
        Map<String, Object> statistics = getUserBehaviorStatistics(startDate, endDate);

        // 可以在这里添加更多的日报逻辑
        // 例如：计算用户活跃度、分析用户行为趋势等

        // 添加报告日期
        statistics.put("reportDate", reportDate);

        return statistics;
    }

    /**
     * 获取用户行为周报
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 周报数据
     */
    @Override
    public Map<String, Object> getUserBehaviorWeeklyReport(Date startDate, Date endDate) {
        // 获取用户行为统计数据
        Map<String, Object> statistics = getUserBehaviorStatistics(startDate, endDate);

        // 可以在这里添加更多的周报逻辑
        // 例如：计算用户留存率、分析用户行为模式等

        // 添加报告时间范围
        statistics.put("startDate", startDate);
        statistics.put("endDate", endDate);

        return statistics;
    }

    /**
     * 获取用户行为月报
     * @param reportMonth 报告月份（格式：yyyy-MM）
     * @return 月报数据
     */
    @Override
    public Map<String, Object> getUserBehaviorMonthlyReport(String reportMonth) {
        // 设置报告月份的时间范围（当月的第一天的00:00:00到最后一天的23:59:59）
        Calendar calendar = Calendar.getInstance();
        String[] monthArray = reportMonth.split("-");
        int year = Integer.parseInt(monthArray[0]);
        int month = Integer.parseInt(monthArray[1]) - 1; // 月份从0开始
        calendar.set(year, month, 1, 0, 0, 0);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endDate = calendar.getTime();

        // 获取用户行为统计数据
        Map<String, Object> statistics = getUserBehaviorStatistics(startDate, endDate);

        // 可以在这里添加更多的月报逻辑
        // 例如：计算用户增长趋势、分析用户行为季节变化等

        // 添加报告月份
        statistics.put("reportMonth", reportMonth);

        return statistics;
    }
}