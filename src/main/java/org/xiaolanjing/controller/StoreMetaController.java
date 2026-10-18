package org.xiaolanjing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xiaolanjing.entity.StoreBrand;
import org.xiaolanjing.entity.StoreCategory;
import org.xiaolanjing.mapper.StoreBrandMapper;
import org.xiaolanjing.mapper.StoreCategoryMapper;

@Controller
@RequestMapping("/admin/store-meta")
public class StoreMetaController {

    @Autowired
    private StoreCategoryMapper storeCategoryMapper;

    @Autowired
    private StoreBrandMapper storeBrandMapper;

    // ---------- 店铺分类 ----------
    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("list", storeCategoryMapper.findAll());
        return "admin/store-category";
    }

    @PostMapping("/category/add")
    public String categoryAdd(StoreCategory category) {
        if (category.getSortOrder() == null) category.setSortOrder(0);
        storeCategoryMapper.insert(category);
        return "redirect:/admin/store-meta/category";
    }

    @PostMapping("/category/update")
    public String categoryUpdate(StoreCategory category) {
        if (category.getSortOrder() == null) category.setSortOrder(0);
        storeCategoryMapper.update(category);
        return "redirect:/admin/store-meta/category";
    }

    @GetMapping("/category/delete/{id}")
    public String categoryDelete(@PathVariable Long id) {
        storeCategoryMapper.deleteById(id);
        return "redirect:/admin/store-meta/category";
    }

    // ---------- 店铺品牌 ----------
    @GetMapping("/brand")
    public String brand(Model model) {
        model.addAttribute("list", storeBrandMapper.findAll());
        return "admin/store-brand";
    }

    @PostMapping("/brand/add")
    public String brandAdd(StoreBrand brand) {
        if (brand.getSortOrder() == null) brand.setSortOrder(0);
        storeBrandMapper.insert(brand);
        return "redirect:/admin/store-meta/brand";
    }

    @PostMapping("/brand/update")
    public String brandUpdate(StoreBrand brand) {
        if (brand.getSortOrder() == null) brand.setSortOrder(0);
        storeBrandMapper.update(brand);
        return "redirect:/admin/store-meta/brand";
    }

    @GetMapping("/brand/delete/{id}")
    public String brandDelete(@PathVariable Long id) {
        storeBrandMapper.deleteById(id);
        return "redirect:/admin/store-meta/brand";
    }
}
