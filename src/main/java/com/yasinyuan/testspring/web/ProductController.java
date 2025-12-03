package com.yasinyuan.testspring.web;

import com.yasinyuan.testspring.core.Result;
import com.yasinyuan.testspring.core.ResultGenerator;
import com.yasinyuan.testspring.model.Product;
import com.yasinyuan.testspring.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController extends AbstractController {

    @Resource
    private ProductService productService;

    @PostMapping
    public Result add(@RequestBody Product product) {
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        productService.save(product);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        productService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody Product product) {
        product.setUpdateTime(new Date());
        productService.update(product);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Product product = productService.findById(id);
        return ResultGenerator.genSuccessResult(product);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Product> list = productService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/publish/{id}")
    public Result publish(@PathVariable Long id) {
        boolean success = productService.publishProduct(id);
        if(success) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("上架失败");
        }
    }

    @PostMapping("/unpublish/{id}")
    public Result unpublish(@PathVariable Long id) {
        boolean success = productService.unpublishProduct(id);
        if(success) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("下架失败");
        }
    }

    @PostMapping("/verify/{id}")
    public Result verify(@PathVariable Long id, @RequestParam Integer verifyStatus) {
        boolean success = productService.verifyProduct(id, verifyStatus);
        if(success) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("审核失败");
        }
    }

    @GetMapping("/low-stock")
    public Result lowStock() {
        List<Product> list = productService.getLowStockProducts();
        return ResultGenerator.genSuccessResult(list);
    }

    @PostMapping("/stock/{id}")
    public Result updateStock(@PathVariable Long id, @RequestParam Integer stock) {
        boolean success = productService.updateStock(id, stock);
        if(success) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("库存更新失败");
        }
    }
}