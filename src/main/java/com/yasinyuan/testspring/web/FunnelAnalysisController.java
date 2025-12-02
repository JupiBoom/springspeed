package com.yasinyuan.testspring.web;

import com.yasinyuan.testspring.model.FunnelConfig;
import com.yasinyuan.testspring.model.FunnelConversionResult;
import com.yasinyuan.testspring.model.FunnelStep;
import com.yasinyuan.testspring.service.FunnelAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/funnel")
public class FunnelAnalysisController {

    @Autowired
    private FunnelAnalysisService funnelAnalysisService;

    @PostMapping("/config")
    public FunnelConfig saveFunnelConfig(@RequestBody FunnelConfigRequest request) {
        return funnelAnalysisService.saveFunnelConfig(request.getFunnelConfig(), request.getFunnelSteps());
    }

    @PutMapping("/config")
    public FunnelConfig updateFunnelConfig(@RequestBody FunnelConfigRequest request) {
        return funnelAnalysisService.updateFunnelConfig(request.getFunnelConfig(), request.getFunnelSteps());
    }

    @DeleteMapping("/config/{funnelId}")
    public void deleteFunnelConfig(@PathVariable Long funnelId) {
        funnelAnalysisService.deleteFunnelConfig(funnelId);
    }

    @GetMapping("/config/enabled")
    public List<FunnelConfig> listEnabledFunnelConfigs() {
        return funnelAnalysisService.listEnabledFunnelConfigs();
    }

    @GetMapping("/config/{funnelId}")
    public FunnelConfig getFunnelConfigById(@PathVariable Long funnelId) {
        return funnelAnalysisService.getFunnelConfigById(funnelId);
    }

    @PostMapping("/calculate")
    public List<FunnelConversionResult> calculateFunnelConversion(
            @RequestParam Long funnelId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return funnelAnalysisService.calculateFunnelConversion(funnelId, startDate, endDate);
    }

    @GetMapping("/result")
    public List<FunnelConversionResult> getFunnelConversionResult(
            @RequestParam Long funnelId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return funnelAnalysisService.getFunnelConversionResult(funnelId, date);
    }

    @GetMapping("/lost-users")
    public Map<String, Object> getLostUsers(
            @RequestParam Long funnelId,
            @RequestParam Integer fromStep,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return funnelAnalysisService.getLostUsers(funnelId, fromStep, startDate, endDate);
    }

    // 内部请求DTO类
    public static class FunnelConfigRequest {
        private FunnelConfig funnelConfig;
        private List<FunnelStep> funnelSteps;

        public FunnelConfig getFunnelConfig() {
            return funnelConfig;
        }

        public void setFunnelConfig(FunnelConfig funnelConfig) {
            this.funnelConfig = funnelConfig;
        }

        public List<FunnelStep> getFunnelSteps() {
            return funnelSteps;
        }

        public void setFunnelSteps(List<FunnelStep> funnelSteps) {
            this.funnelSteps = funnelSteps;
        }
    }
}
