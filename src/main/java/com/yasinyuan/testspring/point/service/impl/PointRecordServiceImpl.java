package com.yasinyuan.testspring.point.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yasinyuan.testspring.point.entity.PointRecord;
import com.yasinyuan.testspring.point.mapper.PointRecordMapper;
import com.yasinyuan.testspring.point.service.PointRecordService;
import org.springframework.stereotype.Service;

/**
 * 积分变动记录Service实现类
 */
@Service
public class PointRecordServiceImpl extends ServiceImpl<PointRecordMapper, PointRecord> implements PointRecordService {
}
