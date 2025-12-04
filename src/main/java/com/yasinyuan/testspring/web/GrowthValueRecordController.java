package com.yasinyuan.testspring.web;

import com.yasinyuan.testspring.model.GrowthValueRecord;
import com.yasinyuan.testspring.model.Member;
import com.yasinyuan.testspring.service.GrowthValueRecordService;
import com.yasinyuan.testspring.service.MemberService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 成长值记录控制器
 * @author yinyuan
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/member/growth-value")
public class GrowthValueRecordController extends AbstractController {
    @Resource
    private GrowthValueRecordService growthValueRecordService;
    
    @Resource
    private MemberService memberService;

    /**
     * 根据会员ID查询成长值记录
     */
    @GetMapping("/records/{memberId}")
    public R getGrowthValueRecords(@PathVariable Long memberId) {
        List<GrowthValueRecord> records = growthValueRecordService.findByMemberId(memberId);
        return R.ok().put("records", records);
    }

    /**
     * 根据会员ID和业务类型查询成长值记录
     */
    @GetMapping("/records/{memberId}/{businessType}")
    public R getGrowthValueRecordsByType(@PathVariable Long memberId, @PathVariable Integer businessType) {
        List<GrowthValueRecord> records = growthValueRecordService.findByMemberIdAndBusinessType(memberId, businessType);
        return R.ok().put("records", records);
    }

    /**
     * 增加成长值记录
     */
    @PostMapping("/add")
    public R addGrowthValueRecord(@RequestParam Long memberId, @RequestParam Integer growthValue, 
                                 @RequestParam Integer businessType, @RequestParam(required = false) Long businessId, 
                                 @RequestParam(required = false) String remark) {
        try {
            // 获取会员当前成长值
            Member member = memberService.selectByPrimaryKey(memberId);
            if (member == null) {
                return R.error("会员不存在");
            }
            
            Integer beforeValue = member.getCurrentGrowthValue();
            Integer afterValue = beforeValue + growthValue;
            
            // 记录成长值变化
            growthValueRecordService.addGrowthValueRecord(memberId, growthValue > 0 ? 1 : 2, 
                Math.abs(growthValue), beforeValue, afterValue, businessType, businessId, remark);
            
            // 更新会员成长值
            member.setCurrentGrowthValue(afterValue);
            member.setTotalGrowthValue(member.getTotalGrowthValue() + Math.abs(growthValue));
            member.setUpdateTime(new Date());
            memberService.updateByPrimaryKeySelective(member);
            
            return R.ok("成长值记录添加成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }
}