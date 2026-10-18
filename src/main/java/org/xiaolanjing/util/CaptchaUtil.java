package org.xiaolanjing.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 图形验证码生成工具（纯 Java 实现，无需额外依赖）
 */
public class CaptchaUtil {

    // 去掉容易混淆的 0/O/1/I
    private static final char[] CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789".toCharArray();
    private static final Random RANDOM = new Random();

    /** 验证码结果：图片 + 文字 */
    public static class Captcha {
        private final String code;
        private final BufferedImage image;

        public Captcha(String code, BufferedImage image) {
            this.code = code;
            this.image = image;
        }

        public String getCode() {
            return code;
        }

        public BufferedImage getImage() {
            return image;
        }
    }

    /** 生成一张 4 位验证码图片 */
    public static Captcha generate() {
        int width = 120;
        int height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 浅蓝背景
        g.setColor(new Color(240, 248, 255));
        g.fillRect(0, 0, width, height);

        // 干扰线
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(170 + RANDOM.nextInt(60), 200 + RANDOM.nextInt(40), 240 + RANDOM.nextInt(15)));
            g.drawLine(RANDOM.nextInt(width), RANDOM.nextInt(height),
                    RANDOM.nextInt(width), RANDOM.nextInt(height));
        }

        // 绘制随机字符（带轻微旋转）
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            char c = CHARS[RANDOM.nextInt(CHARS.length)];
            code.append(c);

            int x = 16 + i * 26;
            int y = 28;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.translate(x, y);
            g2.rotate((RANDOM.nextInt(40) - 20) * Math.PI / 180);
            g2.setFont(new Font("Arial", Font.BOLD, 26));
            g2.setColor(new Color(20 + RANDOM.nextInt(80), 70 + RANDOM.nextInt(120), 150 + RANDOM.nextInt(105)));
            g2.drawString(String.valueOf(c), 0, 0);
            g2.dispose();
        }

        // 噪点
        for (int i = 0; i < 25; i++) {
            g.setColor(new Color(180 + RANDOM.nextInt(75), 210 + RANDOM.nextInt(45), 240 + RANDOM.nextInt(15)));
            g.fillRect(RANDOM.nextInt(width), RANDOM.nextInt(height), 1, 1);
        }

        g.dispose();
        return new Captcha(code.toString(), image);
    }
}
