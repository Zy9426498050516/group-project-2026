package org.xiaolanjing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xiaolanjing.entity.FullReduction;
import org.xiaolanjing.mapper.FullReductionMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/full-reduction")
public class FullReductionController {

    @Autowired
    private FullReductionMapper fullReductionMapper;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", fullReductionMapper.findAll());
        return "admin/full-reduction";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("fr", new FullReduction());
        return "admin/full-reduction-form";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name, @RequestParam BigDecimal fullAmount,
                      @RequestParam BigDecimal reduceAmount,
                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startTime,
                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endTime,
                      @RequestParam(defaultValue = "1") Integer status) {
        FullReduction fr = new FullReduction();
        fr.setName(name.trim());
        fr.setFullAmount(fullAmount);
        fr.setReduceAmount(reduceAmount);
        fr.setStartTime(startTime);
        fr.setEndTime(endTime);
        fr.setStatus(status);
        fullReductionMapper.insert(fr);
        return "redirect:/admin/full-reduction";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        model.addAttribute("fr", fullReductionMapper.findById(id));
        return "admin/full-reduction-form";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam String name, @RequestParam BigDecimal fullAmount,
                         @RequestParam BigDecimal reduceAmount,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startTime,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endTime,
                         @RequestParam(defaultValue = "1") Integer status) {
        FullReduction fr = fullReductionMapper.findById(id);
        if (fr != null) {
            fr.setName(name.trim());
            fr.setFullAmount(fullAmount);
            fr.setReduceAmount(reduceAmount);
            fr.setStartTime(startTime);
            fr.setEndTime(endTime);
            fr.setStatus(status);
            fullReductionMapper.update(fr);
        }
        return "redirect:/admin/full-reduction";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        fullReductionMapper.deleteById(id);
        return "redirect:/admin/full-reduction";
    }
}
