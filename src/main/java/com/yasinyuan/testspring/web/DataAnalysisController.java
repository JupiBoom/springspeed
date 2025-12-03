package com.yasinyuan.testspring.web;

import com.yasinyuan.testspring.core.ResultGenerator;
import com.yasinyuan.testspring.service.DataAnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据分析控制器
 */
@Api("数据分析接口")
@RestController
@RequestMapping("/analysis")
public class DataAnalysisController {

    @Resource
    private DataAnalysisService dataAnalysisService;

    @ApiOperation(value = "获取商品销售排行", notes = "获取指定时间段内的商品销售排行，默认前10条")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "topN", value = "获取前N条数据", defaultValue = "10", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间(yyyy-MM-dd HH:mm:ss)", dataType = "string"),
            @ApiImplicitParam(name = "endTime", value = "结束时间(yyyy-MM-dd HH:mm:ss)", dataType = "string")
    })
    @GetMapping("/sales-ranking")
    public Object getProductSalesRanking(@RequestParam(defaultValue = "10") Integer topN,
                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        List<Map<String, Object>> result = dataAnalysisService.getProductSalesRanking(topN, startTime, endTime);
        return ResultGenerator.genSuccessResult(result);
    }

    @ApiOperation(value = "库存周转率分析", notes = "分析指定分类和品牌的库存周转率")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类ID(可选)", dataType = "long"),
            @ApiImplicitParam(name = "brandId", value = "品牌ID(可选)", dataType = "long"),
            @ApiImplicitParam(name = "startTime", value = "开始时间(yyyy-MM-dd HH:mm:ss)", dataType = "string"),
            @ApiImplicitParam(name = "endTime", value = "结束时间(yyyy-MM-dd HH:mm:ss)", dataType = "string")
    })
    @GetMapping("/inventory-turnover")
    public Object getInventoryTurnoverAnalysis(@RequestParam(required = false) Long categoryId,
                                               @RequestParam(required = false) Long brandId,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        List<Map<String, Object>> result = dataAnalysisService.getInventoryTurnoverAnalysis(categoryId, brandId, startTime, endTime);
        return ResultGenerator.genSuccessResult(result);
    }

    @ApiOperation(value = "商品上下架效率统计", notes = "统计指定时间段内商品上下架效率")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间(yyyy-MM-dd HH:mm:ss)", dataType = "string"),
            @ApiImplicitParam(name = "endTime", value = "结束时间(yyyy-MM-dd HH:mm:ss)", dataType = "string")
    })
    @GetMapping("/shelf-efficiency")
    public Object getProductShelfEfficiency(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        Map<String, Object> result = dataAnalysisService.getProductShelfEfficiency(startTime, endTime);
        return ResultGenerator.genSuccessResult(result);
    }

    @ApiOperation(value = "商品销售趋势", notes = "获取商品销售趋势，支持按日/周/月统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "商品ID", required = true, dataType = "long"),
            @ApiImplicitParam(name = "startTime", value = "开始时间(yyyy-MM-dd HH:mm:ss)", dataType = "string"),
            @ApiImplicitParam(name = "endTime", value = "结束时间(yyyy-MM-dd HH:mm:ss)", dataType = "string"),
            @ApiImplicitParam(name = "periodType", value = "周期类型(day/week/month)", defaultValue = "day", dataType = "string")
    })
    @GetMapping("/sales-trend")
    public Object getProductSalesTrend(@RequestParam Long productId,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                       @RequestParam(defaultValue = "day") String periodType) {
        List<Map<String, Object>> result = dataAnalysisService.getProductSalesTrend(productId, startTime, endTime, periodType);
        return ResultGenerator.genSuccessResult(result);
    }
}