package org.xiaolanjing.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiaolanjing.entity.Admin;
import org.xiaolanjing.entity.Banner;
import org.xiaolanjing.entity.Brand;
import org.xiaolanjing.entity.Product;
import org.xiaolanjing.mapper.AdminMapper;
import org.xiaolanjing.mapper.BannerMapper;
import org.xiaolanjing.mapper.BrandMapper;
import org.xiaolanjing.mapper.ProductMapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 管理员登录 / 后台首页（仪表盘）
 */
@Controller
public class AdminController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private BannerMapper bannerMapper;

    @GetMapping("/admin/login")
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/admin/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        @RequestParam(required = false) String captcha,
                        HttpSession session, Model model) {
        // 校验验证码
        String sessionCaptcha = (String) session.getAttribute("captchaCode");
        if (captcha == null || captcha.isBlank() || sessionCaptcha == null
                || !captcha.trim().equalsIgnoreCase(sessionCaptcha)) {
            model.addAttribute("error", "验证码错误");
            return "admin/login";
        }
        Admin admin = adminMapper.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            session.removeAttribute("captchaCode");
            session.setAttribute("loginAdmin", admin);
            return "redirect:/admin";
        }
        model.addAttribute("error", "管理员账号或密码错误");
        return "admin/login";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginAdmin");
        return "redirect:/admin/login";
    }

    @GetMapping("/admin")
    public String dashboard(Model model) {
        List<Product> products = productMapper.findAll();
        List<Brand> brands = brandMapper.findAll();
        List<Banner> banners = bannerMapper.findAll();

        // 统计卡片数据
        model.addAttribute("productCount", products.size());
        model.addAttribute("brandCount", brands.size());
        model.addAttribute("bannerCount", banners.size());

        int totalStock = 0;
        for (Product p : products) {
            if (p.getStock() != null) {
                totalStock += p.getStock();
            }
        }
        model.addAttribute("totalStock", totalStock);

        // 库存 TOP5 柱状图数据
        List<Product> topStock = products.stream()
                .sorted(Comparator.comparingInt((Product p) -> p.getStock() == null ? 0 : p.getStock()).reversed())
                .limit(5)
                .collect(Collectors.toList());
        List<String> stockNames = new ArrayList<>();
        List<Integer> stockValues = new ArrayList<>();
        for (Product p : topStock) {
            stockNames.add(p.getName());
            stockValues.add(p.getStock() == null ? 0 : p.getStock());
        }
        model.addAttribute("stockNames", stockNames);
        model.addAttribute("stockValues", stockValues);

        // 品牌商品分布（按数量降序）饼图数据
        Map<String, Long> brandCountMap = products.stream()
                .filter(p -> p.getBrandName() != null)
                .collect(Collectors.groupingBy(Product::getBrandName, Collectors.counting()));
        List<Map.Entry<String, Long>> brandStats = brandCountMap.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .collect(Collectors.toList());
        List<String> brandNames = new ArrayList<>();
        List<Integer> brandValues = new ArrayList<>();
        for (Map.Entry<String, Long> e : brandStats) {
            brandNames.add(e.getKey());
            brandValues.add(e.getValue().intValue());
        }
        model.addAttribute("brandNames", brandNames);
        model.addAttribute("brandValues", brandValues);

        // 最新商品（前 6 条）
        int recentCount = Math.min(6, products.size());
        model.addAttribute("recentProducts", products.subList(0, recentCount));

        return "admin/index";
    }
}
