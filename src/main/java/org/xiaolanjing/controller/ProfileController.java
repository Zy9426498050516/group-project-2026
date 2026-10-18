package org.xiaolanjing.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xiaolanjing.entity.Admin;
import org.xiaolanjing.entity.Message;
import org.xiaolanjing.entity.SystemConfig;
import org.xiaolanjing.mapper.AdminMapper;
import org.xiaolanjing.mapper.MessageMapper;
import org.xiaolanjing.mapper.SystemConfigMapper;

@Controller
@RequestMapping("/admin/profile")
public class ProfileController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    // 我的资料
    @GetMapping
    public String profile(Model model, HttpSession session) {
        model.addAttribute("admin", session.getAttribute("loginAdmin"));
        return "admin/profile";
    }

    @PostMapping("/password")
    public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword,
                                 HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("loginAdmin");
        if (admin == null || !admin.getPassword().equals(oldPassword)) {
            model.addAttribute("error", "原密码错误");
            model.addAttribute("admin", admin);
            return "admin/profile";
        }
        adminMapper.updatePassword(admin.getId(), newPassword);
        session.removeAttribute("loginAdmin");
        return "redirect:/admin/login";
    }

    // 消息通知
    @GetMapping("/message")
    public String message(Model model) {
        model.addAttribute("messages", messageMapper.findAll());
        return "admin/message";
    }

    @PostMapping("/message/add")
    public String messageAdd(@RequestParam String title, @RequestParam String content) {
        Message m = new Message();
        m.setTitle(title.trim());
        m.setContent(content);
        messageMapper.insert(m);
        return "redirect:/admin/profile/message";
    }

    @GetMapping("/message/delete/{id}")
    public String messageDelete(@PathVariable Long id) {
        messageMapper.deleteById(id);
        return "redirect:/admin/profile/message";
    }

    // 系统设置
    @GetMapping("/setting")
    public String setting(Model model) {
        model.addAttribute("configs", systemConfigMapper.findAll());
        return "admin/setting";
    }

    @PostMapping("/setting/update")
    public String settingUpdate(@RequestParam Long id, @RequestParam String configValue) {
        SystemConfig config = new SystemConfig();
        config.setId(id);
        config.setConfigValue(configValue);
        systemConfigMapper.update(config);
        return "redirect:/admin/profile/setting";
    }
}
