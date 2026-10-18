package org.xiaolanjing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xiaolanjing.mapper.OrderMapper;
import org.xiaolanjing.mapper.StatsMapper;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/sales")
public class SalesController {

    @Autowired
    private StatsMapper statsMapper;

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("todayOrders", statsMapper.countTodayOrders());
        model.addAttribute("todaySales", statsMapper.sumTodaySales());
        model.addAttribute("totalSales", statsMapper.sumSales());
        long pending = orderMapper.findAll().stream()
                .filter(o -> "待发货".equals(o.getStatus()))
                .count();
        model.addAttribute("pendingCount", pending);
        model.addAttribute("recentOrders", orderMapper.findAll().stream().limit(10).collect(Collectors.toList()));
        return "admin/sales";
    }
}
