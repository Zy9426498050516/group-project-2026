package org.xiaolanjing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xiaolanjing.entity.Announcement;
import org.xiaolanjing.mapper.AnnouncementMapper;

@Controller
@RequestMapping("/admin/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementMapper announcementMapper;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("announcements", announcementMapper.findAll());
        return "admin/announcement";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("announcement", new Announcement());
        return "admin/announcement-form";
    }

    @PostMapping("/add")
    public String add(Announcement announcement) {
        if (announcement.getStatus() == null) announcement.setStatus(1);
        announcementMapper.insert(announcement);
        return "redirect:/admin/announcement";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        model.addAttribute("announcement", announcementMapper.findById(id));
        return "admin/announcement-form";
    }

    @PostMapping("/update")
    public String update(Announcement announcement) {
        if (announcement.getStatus() == null) announcement.setStatus(1);
        announcementMapper.update(announcement);
        return "redirect:/admin/announcement";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        announcementMapper.deleteById(id);
        return "redirect:/admin/announcement";
    }
}
