package com.yasinyuan.testspring.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_user_tag", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "tag_id"})
})
public class UserUserTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    @Column(name = "assign_time", nullable = false)
    private Date assignTime;

    @Column(name = "assign_by", nullable = false, length = 50)
    private String assignBy;

    public UserUserTag() {
        this.assignTime = new Date();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public String getAssignBy() {
        return assignBy;
    }

    public void setAssignBy(String assignBy) {
        this.assignBy = assignBy;
    }
}
