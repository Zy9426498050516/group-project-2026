package org.xiaolanjing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xiaolanjing.mapper.ProductMapper;
import org.xiaolanjing.mapper.StatsMapper;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/analysis")
public class AnalysisController {

    @Autowired
    private StatsMapper statsMapper;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping("/sales")
    public String sales(Model model) {
        model.addAttribute("totalOrders", statsMapper.countOrders());
        model.addAttribute("totalSales", statsMapper.sumSales());
        model.addAttribute("todayOrders", statsMapper.countTodayOrders());
        model.addAttribute("todaySales", statsMapper.sumTodaySales());
        model.addAttribute("statusStats", statsMapper.orderStatusStats());
        model.addAttribute("trend", statsMapper.salesTrend());
        return "admin/analysis-sales";
    }

    @GetMapping("/user")
    public String user(Model model) {
        model.addAttribute("customerCount", statsMapper.countCustomers());
        model.addAttribute("topCustomers", statsMapper.topCustomers());
        return "admin/analysis-user";
    }

    @GetMapping("/product")
    public String product(Model model) {
        model.addAttribute("productCount", statsMapper.countProducts());
        model.addAttribute("topProducts", statsMapper.topProducts());
        List<org.xiaolanjing.entity.Product> lowStock = productMapper.findAll().stream()
                .filter(p -> p.getStock() != null && p.getStock() < 20)
                .collect(Collectors.toList());
        model.addAttribute("lowStock", lowStock);
        return "admin/analysis-product";
    }
}
