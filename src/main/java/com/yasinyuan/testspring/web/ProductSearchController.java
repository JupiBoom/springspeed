package com.yasinyuan.testspring.web;

import com.yasinyuan.testspring.core.Result;
import com.yasinyuan.testspring.core.ResultGenerator;
import com.yasinyuan.testspring.elasticsearch.ProductEs;
import com.yasinyuan.testspring.service.ProductSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/search")
public class ProductSearchController extends AbstractController {

    @Resource
    private ProductSearchService productSearchService;

    @GetMapping("/importAll")
    public Result importAll() {
        int count = productSearchService.importAll();
        return ResultGenerator.genSuccessResult(count);
    }

    @GetMapping
    public Result search(@RequestParam(required = false) String keyword,
                         @RequestParam(required = false) Long brandId,
                         @RequestParam(required = false) Long productCategoryId,
                         @RequestParam(required = false) Double minPrice,
                         @RequestParam(required = false) Double maxPrice,
                         @RequestParam(required = false) String attrValue,
                         @RequestParam(defaultValue = "0") Integer page,
                         @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "sort", "createTime");
        Page<ProductEs> productPage = productSearchService.search(keyword, brandId, productCategoryId, minPrice, maxPrice, attrValue, pageable);
        return ResultGenerator.genSuccessResult(productPage);
    }

    @GetMapping("/simple")
    public Result simpleSearch(@RequestParam String keyword,
                               @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "sort", "createTime");
        Page<ProductEs> productPage = productSearchService.search(keyword, pageable);
        return ResultGenerator.genSuccessResult(productPage);
    }
}