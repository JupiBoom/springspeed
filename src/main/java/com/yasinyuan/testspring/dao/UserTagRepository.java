package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.model.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    List<UserTag> findByTagType(String tagType);
    List<UserTag> findByTagNameContaining(String tagName);
}
