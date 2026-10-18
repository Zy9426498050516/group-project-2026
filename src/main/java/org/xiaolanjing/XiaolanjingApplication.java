package org.xiaolanjing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 小蓝鲸电商后台管理系统 —— 启动入口
 */
@SpringBootApplication
@MapperScan("org.xiaolanjing.mapper")
public class XiaolanjingApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaolanjingApplication.class, args);
    }
}
