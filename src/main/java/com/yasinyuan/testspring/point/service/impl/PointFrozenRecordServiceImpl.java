package com.yasinyuan.testspring.point.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yasinyuan.testspring.point.entity.PointFrozenRecord;
import com.yasinyuan.testspring.point.mapper.PointFrozenRecordMapper;
import com.yasinyuan.testspring.point.service.PointFrozenRecordService;
import org.springframework.stereotype.Service;

/**
 * 积分冻结记录Service实现类
 */
@Service
public class PointFrozenRecordServiceImpl extends ServiceImpl<PointFrozenRecordMapper, PointFrozenRecord> implements PointFrozenRecordService {
}
