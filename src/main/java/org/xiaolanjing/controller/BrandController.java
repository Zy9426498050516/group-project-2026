package org.xiaolanjing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xiaolanjing.entity.Brand;
import org.xiaolanjing.mapper.BrandMapper;

/**
 * 品牌管理（管理员）：增删改查
 */
@Controller
@RequestMapping("/admin/brand")
public class BrandController {

    @Autowired
    private BrandMapper brandMapper;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("brands", brandMapper.findAll());
        return "admin/brand";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name, Model model) {
        if (name == null || name.isBlank()) {
            model.addAttribute("error", "品牌名不能为空");
            model.addAttribute("brands", brandMapper.findAll());
            return "admin/brand";
        }
        if (brandMapper.findByName(name.trim()) != null) {
            model.addAttribute("error", "品牌已存在");
            model.addAttribute("brands", brandMapper.findAll());
            return "admin/brand";
        }
        Brand brand = new Brand();
        brand.setName(name.trim());
        brandMapper.insert(brand);
        return "redirect:/admin/brand";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam String name) {
        Brand brand = brandMapper.findById(id);
        if (brand != null && name != null && !name.isBlank()) {
            brand.setName(name.trim());
            brandMapper.update(brand);
        }
        return "redirect:/admin/brand";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        brandMapper.deleteById(id);
        return "redirect:/admin/brand";
    }
}
