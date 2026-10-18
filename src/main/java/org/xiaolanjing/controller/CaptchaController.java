package org.xiaolanjing.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.xiaolanjing.util.CaptchaUtil;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 图形验证码接口：生成验证码图片，并把验证码文字存入 Session
 */
@Controller
public class CaptchaController {

    @GetMapping("/captcha")
    public ResponseEntity<byte[]> captcha(HttpSession session) throws IOException {
        CaptchaUtil.Captcha captcha = CaptchaUtil.generate();
        // 验证码文字存入 Session，登录时校验
        session.setAttribute("captchaCode", captcha.getCode());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(captcha.getImage(), "png", baos);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setCacheControl(CacheControl.noStore());
        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }
}
