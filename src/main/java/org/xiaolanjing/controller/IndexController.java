package org.xiaolanjing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiaolanjing.entity.Product;
import org.xiaolanjing.mapper.BannerMapper;
import org.xiaolanjing.mapper.BrandMapper;
import org.xiaolanjing.mapper.ProductMapper;

import java.util.List;

/**
 * 首页（顾客端）
 */
@Controller
public class IndexController {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 首页：展示轮播图 + 商品列表，支持按品牌名搜索
     */
    @GetMapping("/")
    public String index(@RequestParam(required = false) String brand, Model model) {
        List<Product> products;
        if (brand != null && !brand.isBlank()) {
            products = productMapper.findByBrandName(brand.trim());
        } else {
            products = productMapper.findAll();
        }
        model.addAttribute("products", products);
        model.addAttribute("banners", bannerMapper.findByStatus(1));
        model.addAttribute("brands", brandMapper.findAll());
        model.addAttribute("keyword", brand);
        return "index";
    }
}
