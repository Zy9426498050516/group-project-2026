package org.xiaolanjing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xiaolanjing.entity.Coupon;
import org.xiaolanjing.mapper.CouponMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/coupon")
public class CouponController {

    @Autowired
    private CouponMapper couponMapper;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("coupons", couponMapper.findAll());
        return "admin/coupon";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("coupon", new Coupon());
        return "admin/coupon-form";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name, @RequestParam BigDecimal amount,
                      @RequestParam(defaultValue = "0") BigDecimal minAmount,
                      @RequestParam(defaultValue = "0") Integer totalCount,
                      @RequestParam(defaultValue = "1") Integer status,
                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startTime,
                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endTime) {
        Coupon c = new Coupon();
        c.setName(name.trim());
        c.setAmount(amount);
        c.setMinAmount(minAmount);
        c.setTotalCount(totalCount);
        c.setStatus(status);
        c.setStartTime(startTime);
        c.setEndTime(endTime);
        couponMapper.insert(c);
        return "redirect:/admin/coupon";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        model.addAttribute("coupon", couponMapper.findById(id));
        return "admin/coupon-form";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam String name, @RequestParam BigDecimal amount,
                         @RequestParam(defaultValue = "0") BigDecimal minAmount,
                         @RequestParam(defaultValue = "0") Integer totalCount,
                         @RequestParam(defaultValue = "1") Integer status,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startTime,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endTime) {
        Coupon c = couponMapper.findById(id);
        if (c != null) {
            c.setName(name.trim());
            c.setAmount(amount);
            c.setMinAmount(minAmount);
            c.setTotalCount(totalCount);
            c.setStatus(status);
            c.setStartTime(startTime);
            c.setEndTime(endTime);
            couponMapper.update(c);
        }
        return "redirect:/admin/coupon";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        couponMapper.deleteById(id);
        return "redirect:/admin/coupon";
    }
}
