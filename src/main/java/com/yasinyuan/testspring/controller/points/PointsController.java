package com.yasinyuan.testspring.controller.points;

import com.yasinyuan.testspring.entity.points.PointsAccount;
import com.yasinyuan.testspring.entity.points.PointsRecord;
import com.yasinyuan.testspring.service.points.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/points")
public class PointsController {

    @Autowired
    private PointsService pointsService;

    // 初始化积分账户
    @PostMapping("/account/{memberId}")
    public ResponseEntity<PointsAccount> initializePointsAccount(@PathVariable Long memberId) {
        try {
            PointsAccount account = pointsService.initializePointsAccount(memberId);
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 会员获取积分
    @PostMapping("/earn/{memberId}")
    public ResponseEntity<PointsAccount> earnPoints(@PathVariable Long memberId,
                                                        @RequestParam Integer points,
                                                        @RequestParam Integer pointsType,
                                                        @RequestParam Integer pointsScene,
                                                        @RequestParam(required = false) String relatedBusinessId) {
        try {
            PointsAccount account = pointsService.earnPoints(memberId, points, pointsType, pointsScene, relatedBusinessId);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 会员消耗积分
    @PostMapping("/spend/{memberId}")
    public ResponseEntity<PointsAccount> spendPoints(@PathVariable Long memberId,
                                                        @RequestParam Integer points,
                                                        @RequestParam Integer pointsType,
                                                        @RequestParam Integer pointsScene,
                                                        @RequestParam(required = false) String relatedBusinessId) {
        try {
            PointsAccount account = pointsService.spendPoints(memberId, points, pointsType, pointsScene, relatedBusinessId);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 冻结积分
    @PostMapping("/freeze/{memberId}")
    public ResponseEntity<PointsAccount> freezePoints(@PathVariable Long memberId,
                                                         @RequestParam Integer points,
                                                         @RequestParam(required = false) String relatedBusinessId) {
        try {
            PointsAccount account = pointsService.freezePoints(memberId, points, relatedBusinessId);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 解冻积分
    @PostMapping("/unfreeze/{memberId}")
    public ResponseEntity<PointsAccount> unfreezePoints(@PathVariable Long memberId,
                                                           @RequestParam Integer points,
                                                           @RequestParam(required = false) String relatedBusinessId) {
        try {
            PointsAccount account = pointsService.unfreezePoints(memberId, points, relatedBusinessId);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 处理积分过期
    @PostMapping("/process-expiration")
    public ResponseEntity<HttpStatus> processPointsExpiration() {
        try {
            pointsService.processPointsExpiration();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 根据会员ID查找积分账户
    @GetMapping("/account/{memberId}")
    public ResponseEntity<PointsAccount> findPointsAccountByMemberId(@PathVariable Long memberId) {
        PointsAccount account = pointsService.findPointsAccountByMemberId(memberId);
        if (account != null) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // 根据会员ID查找积分记录
    @GetMapping("/records/{memberId}")
    public ResponseEntity<List<PointsRecord>> findPointsRecordsByMemberId(@PathVariable Long memberId) {
        List<PointsRecord> records = pointsService.findPointsRecordsByMemberId(memberId);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    // 根据会员ID和积分类型查找积分记录
    @GetMapping("/records/{memberId}/type/{pointsType}")
    public ResponseEntity<List<PointsRecord>> findPointsRecordsByMemberIdAndType(@PathVariable Long memberId, @PathVariable Integer pointsType) {
        List<PointsRecord> records = pointsService.findPointsRecordsByMemberIdAndType(memberId, pointsType);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    // 根据会员ID和积分场景查找积分记录
    @GetMapping("/records/{memberId}/scene/{pointsScene}")
    public ResponseEntity<List<PointsRecord>> findPointsRecordsByMemberIdAndScene(@PathVariable Long memberId, @PathVariable Integer pointsScene) {
        List<PointsRecord> records = pointsService.findPointsRecordsByMemberIdAndScene(memberId, pointsScene);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    // 根据会员ID和日期范围查找积分记录
    @GetMapping("/records/{memberId}/date-range")
    public ResponseEntity<List<PointsRecord>> findPointsRecordsByMemberIdAndDateRange(@PathVariable Long memberId,
                                                                                              @RequestParam Date startDate,
                                                                                              @RequestParam Date endDate) {
        List<PointsRecord> records = pointsService.findPointsRecordsByMemberIdAndDateRange(memberId, startDate, endDate);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    // 获取会员总获取积分
    @GetMapping("/total-earned/{memberId}")
    public ResponseEntity<Integer> getTotalEarnedPointsByMemberId(@PathVariable Long memberId) {
        Integer total = pointsService.getTotalEarnedPointsByMemberId(memberId);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    // 获取会员总消耗积分
    @GetMapping("/total-spent/{memberId}")
    public ResponseEntity<Integer> getTotalSpentPointsByMemberId(@PathVariable Long memberId) {
        Integer total = pointsService.getTotalSpentPointsByMemberId(memberId);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    // 获取会员可用积分
    @GetMapping("/available/{memberId}")
    public ResponseEntity<Integer> getAvailablePointsByMemberId(@PathVariable Long memberId) {
        Integer available = pointsService.getAvailablePointsByMemberId(memberId);
        return new ResponseEntity<>(available, HttpStatus.OK);
    }

    // 获取会员冻结积分
    @GetMapping("/frozen/{memberId}")
    public ResponseEntity<Integer> getFrozenPointsByMemberId(@PathVariable Long memberId) {
        Integer frozen = pointsService.getFrozenPointsByMemberId(memberId);
        return new ResponseEntity<>(frozen, HttpStatus.OK);
    }

    // 统计系统中所有有积分的会员数
    @GetMapping("/statistics/members-with-points")
    public ResponseEntity<Integer> countTotalMembersWithPoints() {
        Integer count = pointsService.countTotalMembersWithPoints();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // 统计指定时间段内有积分活动的活跃会员数
    @GetMapping("/statistics/active-members")
    public ResponseEntity<Integer> countActiveMembersWithPoints(@RequestParam Date startDate, @RequestParam Date endDate) {
        Integer count = pointsService.countActiveMembersWithPoints(startDate, endDate);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // 统计系统中所有积分的总和
    @GetMapping("/statistics/total-points")
    public ResponseEntity<Integer> getTotalPointsInSystem() {
        Integer total = pointsService.getTotalPointsInSystem();
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    // 统计系统中会员的平均积分
    @GetMapping("/statistics/average-points")
    public ResponseEntity<Integer> getAveragePointsPerMember() {
        Integer average = pointsService.getAveragePointsPerMember();
        return new ResponseEntity<>(average, HttpStatus.OK);
    }
}
