package com.yasinyuan.testspring.controller.member;

import com.yasinyuan.testspring.entity.member.MemberLevel;
import com.yasinyuan.testspring.service.member.MemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member-levels")
public class MemberLevelController {

    @Autowired
    private MemberLevelService memberLevelService;

    // 创建会员等级
    @PostMapping("")
    public ResponseEntity<MemberLevel> createMemberLevel(@RequestBody MemberLevel level) {
        try {
            MemberLevel createdLevel = memberLevelService.createMemberLevel(level);
            return new ResponseEntity<>(createdLevel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 更新会员等级
    @PutMapping("/{id}")
    public ResponseEntity<MemberLevel> updateMemberLevel(@PathVariable Long id, @RequestBody MemberLevel level) {
        try {
            level.setId(id);
            MemberLevel updatedLevel = memberLevelService.updateMemberLevel(level);
            return new ResponseEntity<>(updatedLevel, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 根据ID查找会员等级
    @GetMapping("/{id}")
    public ResponseEntity<MemberLevel> findMemberLevelById(@PathVariable Long id) {
        MemberLevel level = memberLevelService.findMemberLevelById(id);
        if (level != null) {
            return new ResponseEntity<>(level, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // 根据等级值查找会员等级
    @GetMapping("/level/{level}")
    public ResponseEntity<MemberLevel> findMemberLevelByLevel(@PathVariable Integer level) {
        MemberLevel levelEntity = memberLevelService.findMemberLevelByLevel(level);
        if (levelEntity != null) {
            return new ResponseEntity<>(levelEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // 根据成长值查找对应的会员等级
    @GetMapping("/growth-value/{growthValue}")
    public ResponseEntity<MemberLevel> findLevelByGrowthValue(@PathVariable Integer growthValue) {
        MemberLevel level = memberLevelService.findLevelByGrowthValue(growthValue);
        if (level != null) {
            return new ResponseEntity<>(level, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // 获取所有会员等级
    @GetMapping("")
    public ResponseEntity<List<MemberLevel>> findAllMemberLevels() {
        List<MemberLevel> levels = memberLevelService.findAllMemberLevels();
        return new ResponseEntity<>(levels, HttpStatus.OK);
    }

    // 根据状态查找会员等级
    @GetMapping("/status/{status}")
    public ResponseEntity<List<MemberLevel>> findMemberLevelsByStatus(@PathVariable Integer status) {
        List<MemberLevel> levels = memberLevelService.findMemberLevelsByStatus(status);
        return new ResponseEntity<>(levels, HttpStatus.OK);
    }

    // 按等级值升序获取会员等级列表
    @GetMapping("/order/level")
    public ResponseEntity<List<MemberLevel>> findMemberLevelsOrderByLevel() {
        List<MemberLevel> levels = memberLevelService.findMemberLevelsOrderByLevel();
        return new ResponseEntity<>(levels, HttpStatus.OK);
    }

    // 按最小成长值升序获取会员等级列表
    @GetMapping("/order/growth-value")
    public ResponseEntity<List<MemberLevel>> findMemberLevelsOrderByMinGrowthValue() {
        List<MemberLevel> levels = memberLevelService.findMemberLevelsOrderByMinGrowthValue();
        return new ResponseEntity<>(levels, HttpStatus.OK);
    }

    // 启用会员等级
    @PutMapping("/{id}/enable")
    public ResponseEntity<MemberLevel> enableMemberLevel(@PathVariable Long id) {
        try {
            MemberLevel level = memberLevelService.enableMemberLevel(id);
            return new ResponseEntity<>(level, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 禁用会员等级
    @PutMapping("/{id}/disable")
    public ResponseEntity<MemberLevel> disableMemberLevel(@PathVariable Long id) {
        try {
            MemberLevel level = memberLevelService.disableMemberLevel(id);
            return new ResponseEntity<>(level, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 删除会员等级
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMemberLevel(@PathVariable Long id) {
        try {
            memberLevelService.deleteMemberLevel(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
