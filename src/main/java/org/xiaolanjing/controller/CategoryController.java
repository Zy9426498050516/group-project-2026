package org.xiaolanjing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xiaolanjing.entity.Category;
import org.xiaolanjing.mapper.CategoryMapper;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryMapper.findAll());
        return "admin/category";
    }

    @PostMapping("/add")
    public String add(Category category) {
        if (category.getSortOrder() == null) category.setSortOrder(0);
        if (category.getStatus() == null) category.setStatus(1);
        categoryMapper.insert(category);
        return "redirect:/admin/category";
    }

    @PostMapping("/update")
    public String update(Category category) {
        if (category.getSortOrder() == null) category.setSortOrder(0);
        if (category.getStatus() == null) category.setStatus(1);
        categoryMapper.update(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryMapper.deleteById(id);
        return "redirect:/admin/category";
    }
}
