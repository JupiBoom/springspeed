package com.yasinyuan.testspring.service.product.impl;

import com.yasinyuan.testspring.dao.product.SpuMapper;
import com.yasinyuan.testspring.model.product.Spu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpuServiceImplTest {
    
    @Mock
    private SpuMapper spuMapper;
    
    @InjectMocks
    private SpuServiceImpl spuServiceImpl;
    
    @Test
    public void testFindByCondition() {
        // 模拟数据
        Spu spu = new Spu();
        spu.setId(1L);
        spu.setName("测试商品");
        
        List<Spu> spuList = Arrays.asList(spu);
        
        // 模拟Mapper方法调用
        when(spuMapper.selectByCondition(any())).thenReturn(spuList);
        
        // 执行测试
        List<Spu> result = spuServiceImpl.findByCondition(spu);
        
        // 验证结果
        assertEquals(1, result.size());
        assertEquals("测试商品", result.get(0).getName());
        
        // 验证Mapper方法是否被调用
        verify(spuMapper, times(1)).selectByCondition(any());
    }
    
    @Test
    public void testFindById() {
        // 模拟数据
        Spu spu = new Spu();
        spu.setId(1L);
        spu.setName("测试商品");
        
        // 模拟Mapper方法调用
        when(spuMapper.selectById(1L)).thenReturn(spu);
        
        // 执行测试
        Spu result = spuServiceImpl.findById(1L);
        
        // 验证结果
        assertEquals("测试商品", result.getName());
        
        // 验证Mapper方法是否被调用
        verify(spuMapper, times(1)).selectById(1L);
    }
    
    @Test
    public void testSave() {
        // 模拟数据
        Spu spu = new Spu();
        spu.setName("测试商品");
        
        // 模拟Mapper方法调用
        when(spuMapper.insert(any())).thenReturn(1);
        
        // 执行测试
        spuServiceImpl.save(spu);
        
        // 验证Mapper方法是否被调用
        verify(spuMapper, times(1)).insert(any());
    }
    
    @Test
    public void testUpdate() {
        // 模拟数据
        Spu spu = new Spu();
        spu.setId(1L);
        spu.setName("测试商品");
        
        // 模拟Mapper方法调用
        when(spuMapper.updateByPrimaryKeySelective(any())).thenReturn(1);
        
        // 执行测试
        spuServiceImpl.update(spu);
        
        // 验证Mapper方法是否被调用
        verify(spuMapper, times(1)).updateByPrimaryKeySelective(any());
    }
    
    @Test
    public void testDeleteById() {
        // 模拟Mapper方法调用
        when(spuMapper.deleteByPrimaryKey(1L)).thenReturn(1);
        
        // 执行测试
        spuServiceImpl.deleteById(1L);
        
        // 验证Mapper方法是否被调用
        verify(spuMapper, times(1)).deleteByPrimaryKey(1L);
    }
}
