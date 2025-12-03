package com.yasinyuan.testspring.controller;

import com.yasinyuan.testspring.model.product.Spu;
import com.yasinyuan.testspring.model.product.Sku;
import com.yasinyuan.testspring.service.product.SpuService;
import com.yasinyuan.testspring.service.product.SkuService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private SpuService spuService;

    @Autowired
    private SkuService skuService;

    /**
     * 分页查询商品列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().put("page", spuService.queryPage(params));
    }

    /**
     * 查询商品详情
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        Spu spu = spuService.findById(id);
        List<Sku> skus = skuService.findBySpuId(id);
        return R.ok().put("spu", spu).put("skus", skus);
    }

    /**
     * 保存商品
     */
    @PostMapping("/save")
    public R save(@RequestBody Spu spu) {
        spuService.save(spu);
        return R.ok();
    }

    /**
     * 修改商品
     */
    @PostMapping("/update")
    public R update(@RequestBody Spu spu) {
        spuService.update(spu);
        return R.ok();
    }

    /**
     * 删除商品
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        spuService.deleteBatchIds(ids);
        return R.ok();
    }
}
