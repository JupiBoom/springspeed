package com.yasinyuan.testspring.web;

import com.yasinyuan.testspring.model.MemberLevel;
import com.yasinyuan.testspring.service.MemberLevelService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会员等级控制器
 * @author yinyuan
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/member/level")
public class MemberLevelController extends AbstractController {
    @Resource
    private MemberLevelService memberLevelService;

    /**
     * 查询所有启用的会员等级
     */
    @GetMapping("/enabled")
    public R getEnabledLevels() {
        List<MemberLevel> levels = memberLevelService.findEnabledLevels();
        return R.ok().put("levels", levels);
    }

    /**
     * 根据成长值查询对应的会员等级
     */
    @GetMapping("/by-growth-value/{growthValue}")
    public R getLevelByGrowthValue(@PathVariable Integer growthValue) {
        MemberLevel level = memberLevelService.findByGrowthValue(growthValue);
        if (level == null) {
            return R.error("未找到对应的会员等级");
        }
        return R.ok().put("level", level);
    }

    /**
     * 初始化会员等级
     */
    @PostMapping("/init")
    public R initMemberLevels() {
        try {
            memberLevelService.initMemberLevels();
            return R.ok("会员等级初始化成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
}