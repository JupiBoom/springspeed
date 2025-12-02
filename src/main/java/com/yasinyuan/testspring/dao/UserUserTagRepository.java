package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.UserUserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserUserTagRepository extends JpaRepository<UserUserTag, Long> {
    List<UserUserTag> findByUserId(String userId);
    List<UserUserTag> findByTagId(Long tagId);
    void deleteByUserId(String userId);
    void deleteByTagId(Long tagId);
}
