package org.xiaolanjing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xiaolanjing.entity.Admin;
import org.xiaolanjing.entity.ExpressCompany;
import org.xiaolanjing.entity.ShippingTemplate;
import org.xiaolanjing.mapper.*;

@Controller
@RequestMapping("/admin/ops")
public class OpsController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ShippingTemplateMapper shippingTemplateMapper;

    @Autowired
    private ExpressCompanyMapper expressCompanyMapper;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private StatsMapper statsMapper;

    // ---------- 管理员管理 ----------
    @GetMapping("/admin")
    public String admins(Model model) {
        model.addAttribute("admins", adminMapper.findAll());
        return "admin/admin-list";
    }

    @PostMapping("/admin/add")
    public String adminAdd(@RequestParam String username, @RequestParam String password, Model model) {
        if (adminMapper.findByUsername(username) != null) {
            model.addAttribute("error", "管理员账号已存在");
            model.addAttribute("admins", adminMapper.findAll());
            return "admin/admin-list";
        }
        Admin a = new Admin();
        a.setUsername(username.trim());
        a.setPassword(password);
        adminMapper.insertAdmin(a);
        return "redirect:/admin/ops/admin";
    }

    @PostMapping("/admin/reset")
    public String adminReset(@RequestParam Long id, @RequestParam String password) {
        adminMapper.updatePassword(id, password);
        return "redirect:/admin/ops/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String adminDelete(@PathVariable Long id) {
        adminMapper.deleteById(id);
        return "redirect:/admin/ops/admin";
    }

    // ---------- 用户管理 ----------
    @GetMapping("/user")
    public String users(Model model) {
        model.addAttribute("users", customerMapper.findAll());
        return "admin/user-list";
    }

    @PostMapping("/user/reset")
    public String userReset(@RequestParam Long id, @RequestParam String password) {
        customerMapper.updatePassword(id, password);
        return "redirect:/admin/ops/user";
    }

    @GetMapping("/user/delete/{id}")
    public String userDelete(@PathVariable Long id) {
        customerMapper.deleteById(id);
        return "redirect:/admin/ops/user";
    }

    // ---------- 运费模板 ----------
    @GetMapping("/shipping")
    public String shipping(Model model) {
        model.addAttribute("list", shippingTemplateMapper.findAll());
        return "admin/shipping-template";
    }

    @PostMapping("/shipping/add")
    public String shippingAdd(ShippingTemplate template) {
        shippingTemplateMapper.insert(template);
        return "redirect:/admin/ops/shipping";
    }

    @PostMapping("/shipping/update")
    public String shippingUpdate(ShippingTemplate template) {
        shippingTemplateMapper.update(template);
        return "redirect:/admin/ops/shipping";
    }

    @GetMapping("/shipping/delete/{id}")
    public String shippingDelete(@PathVariable Long id) {
        shippingTemplateMapper.deleteById(id);
        return "redirect:/admin/ops/shipping";
    }

    // ---------- 快递公司 ----------
    @GetMapping("/express")
    public String express(Model model) {
        model.addAttribute("list", expressCompanyMapper.findAll());
        return "admin/express-company";
    }

    @PostMapping("/express/add")
    public String expressAdd(ExpressCompany company) {
        expressCompanyMapper.insert(company);
        return "redirect:/admin/ops/express";
    }

    @PostMapping("/express/update")
    public String expressUpdate(ExpressCompany company) {
        expressCompanyMapper.update(company);
        return "redirect:/admin/ops/express";
    }

    @GetMapping("/express/delete/{id}")
    public String expressDelete(@PathVariable Long id) {
        expressCompanyMapper.deleteById(id);
        return "redirect:/admin/ops/express";
    }

    // ---------- 操作日志 ----------
    @GetMapping("/log")
    public String log(Model model) {
        model.addAttribute("logs", operationLogMapper.findAll());
        return "admin/operation-log";
    }

    // ---------- 数据备份 / 数据迁移 ----------
    @GetMapping("/backup")
    public String backup(Model model) {
        model.addAttribute("tables", statsMapper.listTables());
        return "admin/data-backup";
    }

    @GetMapping("/migrate")
    public String migrate() {
        return "admin/data-migrate";
    }
}
