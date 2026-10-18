package org.xiaolanjing.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xiaolanjing.entity.Admin;
import org.xiaolanjing.entity.OperationLog;
import org.xiaolanjing.entity.Store;
import org.xiaolanjing.mapper.OperationLogMapper;
import org.xiaolanjing.mapper.StoreMapper;

import java.util.List;

@Controller
@RequestMapping("/admin/store")
public class StoreController {

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private OperationLogMapper operationLogMapper;

    // 同行商家审核
    @GetMapping("/review")
    public String review(Model model) {
        model.addAttribute("stores", storeMapper.findAll());
        return "admin/store-review";
    }

    @PostMapping("/review/status")
    public String reviewStatus(@RequestParam Long id, @RequestParam Integer status,
                               HttpSession session, HttpServletRequest request) {
        storeMapper.updateStatus(id, status);
        recordLog(session, request, status == 1 ? "审核通过店铺 ID=" + id : "拒绝店铺 ID=" + id);
        return "redirect:/admin/store/review";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        storeMapper.deleteById(id);
        return "redirect:/admin/store/review";
    }

    // 我的店铺（展示第一个店铺并支持编辑）
    @GetMapping("/mine")
    public String mine(Model model) {
        List<Store> stores = storeMapper.findAll();
        model.addAttribute("store", stores.isEmpty() ? new Store() : stores.get(0));
        return "admin/store-mine";
    }

    @PostMapping("/mine/update")
    public String mineUpdate(Store store) {
        storeMapper.update(store);
        return "redirect:/admin/store/mine";
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
