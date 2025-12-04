package com.yasinyuan.testspring.analysis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yasinyuan.testspring.analysis.entity.MemberActivityHeatmap;
import com.yasinyuan.testspring.analysis.mapper.MemberActivityHeatmapMapper;
import com.yasinyuan.testspring.analysis.service.MemberActivityHeatmapService;
import org.springframework.stereotype.Service;

/**
 * 会员活跃度热力图Service实现类
 */
@Service
public class MemberActivityHeatmapServiceImpl extends ServiceImpl<MemberActivityHeatmapMapper, MemberActivityHeatmap> implements MemberActivityHeatmapService {
}
