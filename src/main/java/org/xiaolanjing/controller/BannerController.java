package org.xiaolanjing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xiaolanjing.entity.Banner;
import org.xiaolanjing.mapper.BannerMapper;
import org.xiaolanjing.util.FileUtil;

import java.io.IOException;

/**
 * 轮播图 / 活动海报管理（管理员）：增删改查 + 图片上传
 */
@Controller
@RequestMapping("/admin/banner")
public class BannerController {

    @Autowired
    private BannerMapper bannerMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("banners", bannerMapper.findAll());
        return "admin/banner";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        Banner banner = new Banner();
        banner.setSortOrder(0);
        banner.setStatus(1);
        model.addAttribute("banner", banner);
        return "admin/banner-form";
    }

    @PostMapping("/add")
    public String add(Banner banner,
                      @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        String image = FileUtil.saveImage(file, uploadDir);
        if (image != null) {
            banner.setImage(image);
        }
        if (banner.getSortOrder() == null) {
            banner.setSortOrder(0);
        }
        if (banner.getStatus() == null) {
            banner.setStatus(1);
        }
        bannerMapper.insert(banner);
        return "redirect:/admin/banner";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        model.addAttribute("banner", bannerMapper.findById(id));
        return "admin/banner-form";
    }

    @PostMapping("/update")
    public String update(Banner banner,
                         @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        String image = FileUtil.saveImage(file, uploadDir);
        if (image != null) {
            banner.setImage(image);
        } else {
            // 没有重新上传图片时，保留原图片
            banner.setImage(bannerMapper.findById(banner.getId()).getImage());
        }
        if (banner.getSortOrder() == null) {
            banner.setSortOrder(0);
        }
        if (banner.getStatus() == null) {
            banner.setStatus(1);
        }
        bannerMapper.update(banner);
        return "redirect:/admin/banner";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bannerMapper.deleteById(id);
        return "redirect:/admin/banner";
    }
}
