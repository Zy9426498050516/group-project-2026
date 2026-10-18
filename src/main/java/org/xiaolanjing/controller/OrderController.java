package org.xiaolanjing.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xiaolanjing.entity.Admin;
import org.xiaolanjing.entity.OperationLog;
import org.xiaolanjing.entity.Order;
import org.xiaolanjing.mapper.OperationLogMapper;
import org.xiaolanjing.mapper.OrderMapper;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/order")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @GetMapping
    public String list(@RequestParam(required = false) String status, Model model) {
        List<Order> orders = orderMapper.findAll();
        if (status != null && !status.isBlank()) {
            orders = orders.stream().filter(o -> status.equals(o.getStatus())).collect(Collectors.toList());
        }
        model.addAttribute("orders", orders);
        model.addAttribute("status", status);
        return "admin/order";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderMapper.findById(id));
        model.addAttribute("items", orderMapper.findItemsByOrderId(id));
        return "admin/order-detail";
    }

    @PostMapping("/status")
    public String status(@RequestParam Long id, @RequestParam String status,
                         HttpSession session, HttpServletRequest request) {
        orderMapper.updateStatus(id, status);
        recordLog(session, request, "修改订单状态为：" + status);
        return "redirect:/admin/order";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session, HttpServletRequest request) {
        orderMapper.deleteItemsByOrderId(id);
        orderMapper.deleteById(id);
        recordLog(session, request, "删除订单 ID=" + id);
        return "redirect:/admin/order";
    }

    private void recordLog(HttpSession session, HttpServletRequest request, String operation) {
        Admin admin = (Admin) session.getAttribute("loginAdmin");
        OperationLog log = new OperationLog();
        log.setAdminName(admin != null ? admin.getUsername() : "未知");
        log.setOperation(operation);
        log.setIp(request.getRemoteAddr());
        operationLogMapper.insert(log);
    }
}
