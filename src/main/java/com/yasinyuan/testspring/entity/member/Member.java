package com.yasinyuan.testspring.entity.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "member")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday")
    private Date birthday;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "level_id")
    private MemberLevel level;

    @Column(name = "growth_value")
    private Integer growthValue = 0;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "join_date")
    private Date joinDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @Column(name = "status")
    private Integer status = 1; // 1:正常, 0:禁用

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "expire_date")
    private Date expireDate;

    @Column(name = "exclusive_customer_service_id")
    private Long exclusiveCustomerServiceId;

    // 构造函数
    public Member() {}

    // getter和setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }
    public MemberLevel getLevel() { return level; }
    public void setLevel(MemberLevel level) { this.level = level; }
    public Integer getGrowthValue() { return growthValue; }
    public void setGrowthValue(Integer growthValue) { this.growthValue = growthValue; }
    public Date getJoinDate() { return joinDate; }
    public void setJoinDate(Date joinDate) { this.joinDate = joinDate; }
    public Date getLastLoginDate() { return lastLoginDate; }
    public void setLastLoginDate(Date lastLoginDate) { this.lastLoginDate = lastLoginDate; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Date getExpireDate() { return expireDate; }
    public void setExpireDate(Date expireDate) { this.expireDate = expireDate; }
    public Long getExclusiveCustomerServiceId() { return exclusiveCustomerServiceId; }
    public void setExclusiveCustomerServiceId(Long exclusiveCustomerServiceId) { this.exclusiveCustomerServiceId = exclusiveCustomerServiceId; }
}
