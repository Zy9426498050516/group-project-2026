package org.xiaolanjing.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiaolanjing.entity.Customer;
import org.xiaolanjing.mapper.CustomerMapper;

/**
 * 顾客登录 / 注册 / 退出
 */
@Controller
public class AuthController {

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        @RequestParam(required = false) String captcha,
                        HttpSession session, Model model) {
        // 校验验证码
        String sessionCaptcha = (String) session.getAttribute("captchaCode");
        if (captcha == null || captcha.isBlank() || sessionCaptcha == null
                || !captcha.trim().equalsIgnoreCase(sessionCaptcha)) {
            model.addAttribute("error", "验证码错误");
            return "login";
        }
        Customer customer = customerMapper.findByUsername(username);
        if (customer != null && customer.getPassword().equals(password)) {
            session.removeAttribute("captchaCode");
            session.setAttribute("loginCustomer", customer);
            return "redirect:/";
        }
        model.addAttribute("error", "用户名或密码错误");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password,
                           @RequestParam String confirmPassword, Model model) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            model.addAttribute("error", "用户名和密码不能为空");
            return "register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "两次输入的密码不一致");
            return "register";
        }
        if (customerMapper.findByUsername(username) != null) {
            model.addAttribute("error", "用户名已存在");
            return "register";
        }
        Customer customer = new Customer();
        customer.setUsername(username.trim());
        customer.setPassword(password);
        customerMapper.insert(customer);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
