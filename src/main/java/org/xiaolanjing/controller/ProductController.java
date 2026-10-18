package org.xiaolanjing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xiaolanjing.entity.Product;
import org.xiaolanjing.mapper.BrandMapper;
import org.xiaolanjing.mapper.ProductMapper;
import org.xiaolanjing.util.FileUtil;

import java.io.IOException;

/**
 * 商品管理（管理员）：增删改查 + 图片上传 + 库存增减
 */
@Controller
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productMapper.findAll());
        return "admin/product";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("brands", brandMapper.findAll());
        return "admin/product-form";
    }

    @PostMapping("/add")
    public String add(Product product,
                      @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        String image = FileUtil.saveImage(file, uploadDir);
        if (image != null) {
            product.setImage(image);
        }
        productMapper.insert(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        model.addAttribute("product", productMapper.findById(id));
        model.addAttribute("brands", brandMapper.findAll());
        return "admin/product-form";
    }

    @PostMapping("/update")
    public String update(Product product,
                         @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        String image = FileUtil.saveImage(file, uploadDir);
        if (image != null) {
            product.setImage(image);
        } else {
            // 没有重新上传图片时，保留原图片
            product.setImage(productMapper.findById(product.getId()).getImage());
        }
        productMapper.update(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productMapper.deleteById(id);
        return "redirect:/admin/product";
    }

    /** 增加 / 减少库存（数量可以是任意多个） */
    @PostMapping("/stock")
    public String updateStock(@RequestParam Long productId,
                              @RequestParam String action,
                              @RequestParam Integer amount) {
        if (amount == null || amount < 1) {
            amount = 1;
        }
        Product product = productMapper.findById(productId);
        int newStock = product.getStock() == null ? 0 : product.getStock();
        if ("in".equals(action)) {
            newStock += amount;
        } else if ("out".equals(action)) {
            newStock = Math.max(0, newStock - amount);
        }
        productMapper.updateStock(productId, newStock);
        return "redirect:/admin/product";
    }
}
