package com.ecommerce.controller;

import com.ecommerce.entity.Funnel;
import com.ecommerce.entity.FunnelAnalysisResult;
import com.ecommerce.service.FunnelAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/funnel-analysis")
public class FunnelAnalysisController {

    @Autowired
    private FunnelAnalysisService funnelAnalysisService;

    /**
     * 创建漏斗
     * @param funnel 漏斗信息
     * @return 创建的漏斗
     */
    @PostMapping("/funnels")
    public ResponseEntity<Funnel> createFunnel(@RequestBody Funnel funnel) {
        Funnel createdFunnel = funnelAnalysisService.createFunnel(funnel);
        return ResponseEntity.ok(createdFunnel);
    }

    /**
     * 更新漏斗
     * @param funnelId 漏斗ID
     * @param funnel 漏斗信息
     * @return 更新后的漏斗
     */
    @PutMapping("/funnels/{funnelId}")
    public ResponseEntity<Funnel> updateFunnel(@PathVariable Long funnelId, @RequestBody Funnel funnel) {
        Funnel updatedFunnel = funnelAnalysisService.updateFunnel(funnelId, funnel);
        return ResponseEntity.ok(updatedFunnel);
    }

    /**
     * 删除漏斗
     * @param funnelId 漏斗ID
     * @return 响应状态
     */
    @DeleteMapping("/funnels/{funnelId}")
    public ResponseEntity<Void> deleteFunnel(@PathVariable Long funnelId) {
        funnelAnalysisService.deleteFunnel(funnelId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 查询所有漏斗
     * @return 漏斗列表
     */
    @GetMapping("/funnels")
    public ResponseEntity<List<Funnel>> getAllFunnels() {
        List<Funnel> funnels = funnelAnalysisService.getAllFunnels();
        return ResponseEntity.ok(funnels);
    }

    /**
     * 根据ID查询漏斗
     * @param funnelId 漏斗ID
     * @return 漏斗信息
     */
    @GetMapping("/funnels/{funnelId}")
    public ResponseEntity<Funnel> getFunnelById(@PathVariable Long funnelId) {
        Funnel funnel = funnelAnalysisService.getFunnelById(funnelId);
        return ResponseEntity.ok(funnel);
    }

    /**
     * 分析漏斗转化情况
     * @param funnelId 漏斗ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 漏斗分析结果
     */
    @PostMapping("/analyze/{funnelId}")
    public ResponseEntity<List<FunnelAnalysisResult>> analyzeFunnel(
            @PathVariable Long funnelId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {
        List<FunnelAnalysisResult> results = funnelAnalysisService.analyzeFunnel(funnelId, startDate, endDate);
        return ResponseEntity.ok(results);
    }

    /**
     * 获取漏斗分析结果
     * @param funnelId 漏斗ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 漏斗分析结果列表
     */
    @GetMapping("/results/{funnelId}")
    public ResponseEntity<List<FunnelAnalysisResult>> getFunnelAnalysisResults(
            @PathVariable Long funnelId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {
        List<FunnelAnalysisResult> results = funnelAnalysisService.getFunnelAnalysisResults(funnelId, startDate, endDate);
        return ResponseEntity.ok(results);
    }

    /**
     * 定位漏斗流失用户
     * @param funnelId 漏斗ID
     * @param fromStep 起始步骤
     * @param toStep 目标步骤
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 流失用户ID列表
     */
    @GetMapping("/churned-users/{funnelId}")
    public ResponseEntity<List<Long>> getChurnedUsers(
            @PathVariable Long funnelId,
            @RequestParam Integer fromStep,
            @RequestParam Integer toStep,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {
        List<Long> churnedUsers = funnelAnalysisService.getChurnedUsers(funnelId, fromStep, toStep, startDate, endDate);
        return ResponseEntity.ok(churnedUsers);
    }
}
