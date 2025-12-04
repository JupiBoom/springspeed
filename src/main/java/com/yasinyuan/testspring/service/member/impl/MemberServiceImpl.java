package com.yasinyuan.testspring.service.member.impl;

import com.yasinyuan.testspring.dao.member.MemberRepository;
import com.yasinyuan.testspring.dao.member.MemberLevelRepository;
import com.yasinyuan.testspring.entity.member.Member;
import com.yasinyuan.testspring.entity.member.MemberLevel;
import com.yasinyuan.testspring.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberLevelRepository memberLevelRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Member registerMember(Member member) {
        // 检查用户名、邮箱、手机号是否已存在
        if (memberRepository.findByUsername(member.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        if (memberRepository.findByEmail(member.getEmail()) != null) {
            throw new RuntimeException("邮箱已存在");
        }
        if (memberRepository.findByPhone(member.getPhone()) != null) {
            throw new RuntimeException("手机号已存在");
        }

        // 加密密码
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        // 设置默认会员等级（普通会员）
        MemberLevel defaultLevel = memberLevelRepository.findByLevel(1);
        if (defaultLevel == null) {
            throw new RuntimeException("默认会员等级不存在");
        }
        member.setLevel(defaultLevel);

        // 设置默认成长值为0
        member.setGrowthValue(0);

        // 设置注册时间和状态
        member.setJoinDate(new Date());
        member.setStatus(1); // 正常状态

        // 保存会员
        return memberRepository.save(member);
    }

    @Override
    public Member loginMember(String username, String password) {
        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            throw new RuntimeException("用户名不存在");
        }
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        if (member.getStatus() != 1) {
            throw new RuntimeException("会员已禁用");
        }

        // 更新最后登录时间
        member.setLastLoginDate(new Date());
        memberRepository.save(member);

        return member;
    }

    @Override
    public Member findMemberById(Long id) {
        return memberRepository.findOne(id);
    }

    @Override
    public Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Member findMemberByPhone(String phone) {
        return memberRepository.findByPhone(phone);
    }

    @Override
    public Member updateMember(Member member) {
        Member existingMember = memberRepository.findOne(member.getId());
        if (existingMember == null) {
            throw new RuntimeException("会员不存在");
        }

        // 更新会员信息
        existingMember.setEmail(member.getEmail());
        existingMember.setPhone(member.getPhone());
        existingMember.setBirthday(member.getBirthday());

        return memberRepository.save(existingMember);
    }

    @Override
    public Member updateMemberGrowthValue(Long memberId, Integer growthValueChange) {
        Member member = memberRepository.findOne(memberId);
        if (member == null) {
            throw new RuntimeException("会员不存在");
        }

        // 更新成长值
        int newGrowthValue = member.getGrowthValue() + growthValueChange;
        member.setGrowthValue(Math.max(newGrowthValue, 0)); // 成长值不能为负数

        // 检查是否需要变更会员等级
        MemberLevel newLevel = checkMemberLevelChange(memberId);
        if (newLevel != null && !newLevel.getId().equals(member.getLevel().getId())) {
            member.setLevel(newLevel);
        }

        return memberRepository.save(member);
    }

    @Override
    public MemberLevel checkMemberLevelChange(Long memberId) {
        Member member = memberRepository.findOne(memberId);
        if (member == null) {
            throw new RuntimeException("会员不存在");
        }

        // 根据当前成长值查找对应的会员等级
        MemberLevel currentLevel = memberLevelRepository.findLevelByGrowthValue(member.getGrowthValue());
        if (currentLevel == null) {
            // 如果没有找到对应的等级，返回默认等级（普通会员）
            return memberLevelRepository.findByLevel(1);
        }

        return currentLevel;
    }

    @Override
    public Member changeMemberLevel(Long memberId, Long newLevelId) {
        Member member = memberRepository.findOne(memberId);
        if (member == null) {
            throw new RuntimeException("会员不存在");
        }

        MemberLevel newLevel = memberLevelRepository.findOne(newLevelId);
        if (newLevel == null) {
            throw new RuntimeException("会员等级不存在");
        }
        if (newLevel.getStatus() != 1) {
            throw new RuntimeException("会员等级已禁用");
        }

        // 更新会员等级
        member.setLevel(newLevel);

        return memberRepository.save(member);
    }

    @Override
    public Member toggleMemberStatus(Long memberId) {
        Member member = memberRepository.findOne(memberId);
        if (member == null) {
            throw new RuntimeException("会员不存在");
        }

        // 切换会员状态
        member.setStatus(member.getStatus() == 1 ? 0 : 1);

        return memberRepository.save(member);
    }

    @Override
    public Member extendMemberExpireDate(Long memberId, Integer days) {
        Member member = memberRepository.findOne(memberId);
        if (member == null) {
            throw new RuntimeException("会员不存在");
        }

        // 延长会员有效期
        Date currentExpireDate = member.getExpireDate();
        Date newExpireDate;
        if (currentExpireDate == null || currentExpireDate.before(new Date())) {
            // 如果有效期为空或已过期，从当前时间开始延长
            newExpireDate = new Date(System.currentTimeMillis() + (long) days * 24 * 60 * 60 * 1000);
        } else {
            // 如果有效期未过期，从现有有效期开始延长
            newExpireDate = new Date(currentExpireDate.getTime() + (long) days * 24 * 60 * 60 * 1000);
        }

        member.setExpireDate(newExpireDate);

        return memberRepository.save(member);
    }

    @Override
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public List<Member> findMembersByLevelId(Long levelId) {
        return memberRepository.findByLevelId(levelId);
    }

    @Override
    public List<Member> findMembersByStatus(Integer status) {
        return memberRepository.findByStatus(status);
    }

    @Override
    public List<Member> findExpiringMembers(Integer days) {
        return memberRepository.findExpiringMembers(days);
    }

    @Override
    public List<Object[]> countMembersByLevel() {
        return memberRepository.countMembersByLevel();
    }

    @Override
    public Integer countNewMembersByTimeRange(Date startDate, Date endDate) {
        return memberRepository.countNewMembersByTimeRange(startDate, endDate);
    }
}
