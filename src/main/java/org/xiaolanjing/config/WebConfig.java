package org.xiaolanjing.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.xiaolanjing.interceptor.AdminLoginInterceptor;
import org.xiaolanjing.interceptor.CustomerLoginInterceptor;

import java.io.File;

/**
 * Web 配置：静态资源映射 + 登录拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;

    @Autowired
    private CustomerLoginInterceptor customerLoginInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 把本地上传目录映射成 /upload/**，用于展示管理员上传的图片
        // 用 toURI() 生成规范的 file: 地址，避免中文路径/空格导致的编码问题
        String location = new File(uploadDir).getAbsoluteFile().toURI().toString();
        if (!location.endsWith("/")) {
            location += "/";
        }
        registry.addResourceHandler("/upload/**").addResourceLocations(location);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 后台管理需要管理员登录（登录页除外）
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login");
        // 购物车需要顾客登录
        registry.addInterceptor(customerLoginInterceptor)
                .addPathPatterns("/cart/**");
    }
}
