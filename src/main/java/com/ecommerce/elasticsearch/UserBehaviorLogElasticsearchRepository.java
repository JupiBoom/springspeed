package com.ecommerce.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.UserBehaviorLog;

@Repository
public interface UserBehaviorLogElasticsearchRepository extends ElasticsearchRepository<UserBehaviorLog, Long> {
    
    // 可以根据需要添加自定义查询方法
}
