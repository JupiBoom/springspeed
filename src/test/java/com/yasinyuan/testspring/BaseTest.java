package com.yasinyuan.testspring;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 单元测试基类
 * @author yinyuan
 * @since 2024-01-01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseTest {
    // 可以在这里添加单元测试的公共方法和属性
}