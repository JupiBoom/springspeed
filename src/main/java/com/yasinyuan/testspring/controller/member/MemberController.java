package com.yasinyuan.testspring.controller.member;

import com.yasinyuan.testspring.entity.member.Member;
import com.yasinyuan.testspring.entity.member.MemberLevel;
import com.yasinyuan.testspring.service.member.MemberService;
import com.yasinyuan.testspring.service.member.MemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberLevelService memberLevelService;

    // 会员注册
    @PostMapping("/register")
    public ResponseEntity<Member> registerMember(@RequestBody Member member) {
        try {
            Member registeredMember = memberService.registerMember(member);
            return new ResponseEntity<>(registeredMember, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 会员登录
    @PostMapping("/login")
    public ResponseEntity<Member> loginMember(@RequestParam String username, @RequestParam String password) {
        try {
            Member member = memberService.loginMember(username, password);
            return new ResponseEntity<>(member, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    // 根据ID查找会员
    @GetMapping("/{id}")
    public ResponseEntity<Member> findMemberById(@PathVariable Long id) {
        Member member = memberService.findMemberById(id);
        if (member != null) {
            return new ResponseEntity<>(member, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // 根据用户名查找会员
    @GetMapping("/username/{username}")
    public ResponseEntity<Member> findMemberByUsername(@PathVariable String username) {
        Member member = memberService.findMemberByUsername(username);
        if (member != null) {
            return new ResponseEntity<>(member, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // 更新会员信息
    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        try {
            member.setId(id);
            Member updatedMember = memberService.updateMember(member);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 更新会员成长值
    @PutMapping("/{id}/growth-value")
    public ResponseEntity<Member> updateMemberGrowthValue(@PathVariable Long id, @RequestParam Integer growthValueChange) {
        try {
            Member updatedMember = memberService.updateMemberGrowthValue(id, growthValueChange);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 检查会员等级变更
    @GetMapping("/{id}/check-level-change")
    public ResponseEntity<MemberLevel> checkMemberLevelChange(@PathVariable Long id) {
        try {
            MemberLevel newLevel = memberService.checkMemberLevelChange(id);
            return new ResponseEntity<>(newLevel, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 变更会员等级
    @PutMapping("/{id}/change-level")
    public ResponseEntity<Member> changeMemberLevel(@PathVariable Long id, @RequestParam Long newLevelId) {
        try {
            Member updatedMember = memberService.changeMemberLevel(id, newLevelId);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 切换会员状态
    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<Member> toggleMemberStatus(@PathVariable Long id) {
        try {
            Member updatedMember = memberService.toggleMemberStatus(id);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 延长会员有效期
    @PutMapping("/{id}/extend-expire-date")
    public ResponseEntity<Member> extendMemberExpireDate(@PathVariable Long id, @RequestParam Integer days) {
        try {
            Member updatedMember = memberService.extendMemberExpireDate(id, days);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 获取所有会员
    @GetMapping("")
    public ResponseEntity<List<Member>> findAllMembers() {
        List<Member> members = memberService.findAllMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    // 根据等级ID查找会员
    @GetMapping("/level/{levelId}")
    public ResponseEntity<List<Member>> findMembersByLevelId(@PathVariable Long levelId) {
        List<Member> members = memberService.findMembersByLevelId(levelId);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    // 根据状态查找会员
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Member>> findMembersByStatus(@PathVariable Integer status) {
        List<Member> members = memberService.findMembersByStatus(status);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    // 查找即将过期的会员
    @GetMapping("/expiring")
    public ResponseEntity<List<Member>> findExpiringMembers(@RequestParam Integer days) {
        List<Member> members = memberService.findExpiringMembers(days);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    // 统计不同等级的会员数量
    @GetMapping("/count-by-level")
    public ResponseEntity<List<Object[]>> countMembersByLevel() {
        List<Object[]> counts = memberService.countMembersByLevel();
        return new ResponseEntity<>(counts, HttpStatus.OK);
    }

    // 统计指定时间段内的新增会员数量
    @GetMapping("/count-new")
    public ResponseEntity<Integer> countNewMembersByTimeRange(@RequestParam Date startDate, @RequestParam Date endDate) {
        Integer count = memberService.countNewMembersByTimeRange(startDate, endDate);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
