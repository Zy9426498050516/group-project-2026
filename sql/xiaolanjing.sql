-- ============================================================
-- 小蓝鲸商城数据库初始化脚本
-- 使用 Navicat Premium 16 直接执行本文件即可完成建库建表 + 初始数据
-- 注意：执行本脚本会删除并重建同名数据库，请确认没有重要数据
-- ============================================================

DROP DATABASE IF EXISTS xiaolanjing;
CREATE DATABASE xiaolanjing DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE xiaolanjing;

-- ---------- 管理员表 ----------
CREATE TABLE admin (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- ---------- 品牌表 ----------
CREATE TABLE brand (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL UNIQUE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- ---------- 商品表 ----------
CREATE TABLE product (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100)   NOT NULL,
    brand_id    BIGINT         NOT NULL,
    price       DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    stock       INT            NOT NULL DEFAULT 0,
    description VARCHAR(500),
    image       VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- ---------- 轮播图 / 活动海报表 ----------
CREATE TABLE banner (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(100),
    image       VARCHAR(255),
    sort_order  INT          NOT NULL DEFAULT 0,
    status      TINYINT      NOT NULL DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- ---------- 顾客表 ----------
CREATE TABLE customer (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- ---------- 购物车表 ----------
CREATE TABLE cart (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    product_id  BIGINT NOT NULL,
    quantity    INT    NOT NULL DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_customer_product (customer_id, product_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- ============================================================
-- 初始数据
-- ============================================================

-- 默认管理员：admin / 123456
INSERT INTO admin(username, password) VALUES ('admin', '123456');

-- 默认顾客：xiaolan / 123456
INSERT INTO customer(username, password) VALUES ('xiaolan', '123456');

-- 品牌
INSERT INTO brand(name) VALUES ('小蓝鲸'), ('海洋'), ('萌宠'), ('零食');

-- 轮播海报（示例图放在项目 static/images 目录）
INSERT INTO banner(title, image, sort_order, status) VALUES
('小蓝鲸 · 蓝色海洋季', '/images/banner1.svg', 1, 1),
('萌宠好物节', '/images/banner2.svg', 2, 1);

-- 商品（图片留空，运行后可登录后台为商品上传图片）
INSERT INTO product(name, brand_id, price, stock, description) VALUES
('小蓝鲸毛绒玩偶', 1, 39.90, 100, '软萌可爱的蓝色小鲸鱼玩偶，抱着睡觉超舒服'),
('鲸鱼抱枕', 1, 59.00, 80, '大号鲸鱼抱枕，柔软亲肤'),
('海豚挂件', 2, 19.90, 200, '可爱的海豚小挂件，可挂在包包上'),
('小海星发夹', 2, 9.90, 300, '蓝色系海星发夹，可爱百搭'),
('柴犬玩偶', 3, 49.90, 60, '治愈系柴犬玩偶，超级软萌'),
('鲸鱼曲奇饼干', 4, 15.90, 150, '鲸鱼造型黄油曲奇，好吃又好看');

-- ============================================================
-- 以下为扩展模块新增表（商品分类、公告、订单、优惠券、商家等）
-- ============================================================

-- 商品分类
CREATE TABLE category (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL,
    sort_order  INT         NOT NULL DEFAULT 0,
    status      TINYINT     NOT NULL DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 公告
CREATE TABLE announcement (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(100) NOT NULL,
    content     TEXT,
    status      TINYINT      NOT NULL DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 订单
CREATE TABLE orders (
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no     VARCHAR(40)    NOT NULL,
    customer_id  BIGINT         NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL DEFAULT 0,
    status       VARCHAR(20)    NOT NULL DEFAULT '待发货',
    receiver     VARCHAR(50),
    phone        VARCHAR(20),
    address      VARCHAR(255),
    create_time  DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 订单明细
CREATE TABLE order_item (
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id      BIGINT         NOT NULL,
    product_id    BIGINT         NOT NULL,
    product_name  VARCHAR(100),
    product_image VARCHAR(255),
    price         DECIMAL(10, 2) NOT NULL,
    quantity      INT            NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 优惠券
CREATE TABLE coupon (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50)    NOT NULL,
    amount      DECIMAL(10, 2) NOT NULL,
    min_amount  DECIMAL(10, 2) NOT NULL DEFAULT 0,
    total_count INT            NOT NULL DEFAULT 0,
    status      TINYINT        NOT NULL DEFAULT 1,
    start_time  DATETIME,
    end_time    DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 操作日志
CREATE TABLE operation_log (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    admin_name  VARCHAR(50),
    operation   VARCHAR(100),
    ip          VARCHAR(50),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 消息通知
CREATE TABLE message (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(100) NOT NULL,
    content     VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 系统设置（键值对）
CREATE TABLE system_config (
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    config_key   VARCHAR(50) NOT NULL UNIQUE,
    config_value VARCHAR(255)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 运费模板
CREATE TABLE shipping_template (
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    name           VARCHAR(50)    NOT NULL,
    first_weight   DECIMAL(10, 2) NOT NULL DEFAULT 1,
    first_price    DECIMAL(10, 2) NOT NULL DEFAULT 0,
    continue_price DECIMAL(10, 2) NOT NULL DEFAULT 0,
    create_time    DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 快递公司
CREATE TABLE express_company (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL,
    code        VARCHAR(50),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 店铺分类
CREATE TABLE store_category (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL,
    sort_order  INT         NOT NULL DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 店铺品牌
CREATE TABLE store_brand (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL,
    sort_order  INT         NOT NULL DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 店铺 / 商家
CREATE TABLE store (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    owner_name  VARCHAR(50),
    phone       VARCHAR(20),
    status      INT          NOT NULL DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 满减活动
CREATE TABLE full_reduction (
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    name          VARCHAR(50)    NOT NULL,
    full_amount   DECIMAL(10, 2) NOT NULL,
    reduce_amount DECIMAL(10, 2) NOT NULL,
    start_time    DATETIME,
    end_time      DATETIME,
    status        TINYINT        NOT NULL DEFAULT 1,
    create_time   DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- ---------- 扩展模块初始数据 ----------
INSERT INTO category(name, sort_order, status) VALUES
('数码家电', 1, 1), ('服饰鞋包', 2, 1), ('食品零食', 3, 1), ('家居生活', 4, 1);

INSERT INTO announcement(title, content, status) VALUES
('欢迎使用小蓝鲸商城', '小蓝鲸商城全新上线，欢迎各位顾客选购心仪商品～', 1),
('双11大促预告', '近期将开启海洋季大促，敬请期待！', 1);

INSERT INTO orders(order_no, customer_id, total_amount, status, receiver, phone, address) VALUES
('202610170001', 1, 98.90, '已发货', '小蓝', '13800000001', '广东省深圳市南山区'),
('202610170002', 1, 39.90, '待发货', '小蓝', '13800000001', '广东省深圳市南山区'),
('202610160003', 1, 59.00, '已完成', '小蓝', '13800000001', '广东省深圳市南山区');

INSERT INTO order_item(order_id, product_id, product_name, product_image, price, quantity) VALUES
(1, 1, '小蓝鲸毛绒玩偶', '', 39.90, 1),
(1, 2, '鲸鱼抱枕', '', 59.00, 1),
(2, 1, '小蓝鲸毛绒玩偶', '', 39.90, 1),
(3, 2, '鲸鱼抱枕', '', 59.00, 1);

INSERT INTO coupon(name, amount, min_amount, total_count, status, start_time, end_time) VALUES
('新人立减券', 5.00, 30.00, 1000, 1, '2026-01-01 00:00:00', '2027-01-01 00:00:00'),
('满100减20', 20.00, 100.00, 500, 1, '2026-01-01 00:00:00', '2027-01-01 00:00:00');

INSERT INTO message(title, content) VALUES
('系统通知', '系统将于今晚 23:00 进行例行维护，请知悉。'),
('活动提醒', '海洋季大促活动即将开启，请提前准备库存。');

INSERT INTO system_config(config_key, config_value) VALUES
('site_name', '小蓝鲸商城'), ('site_desc', '蓝色海洋主题电商平台'), ('service_phone', '400-888-8888');

INSERT INTO shipping_template(name, first_weight, first_price, continue_price) VALUES
('全国包邮', 1.00, 0.00, 0.00), ('标准运费', 1.00, 8.00, 4.00);

INSERT INTO express_company(name, code) VALUES
('顺丰速运', 'SF'), ('圆通速递', 'YTO'), ('中通快递', 'ZTO'), ('韵达快递', 'YD');

INSERT INTO store_category(name, sort_order) VALUES ('数码店', 1), ('服饰店', 2), ('食品店', 3);

INSERT INTO store_brand(name, sort_order) VALUES ('鲸选优选', 1), ('海洋严选', 2);

INSERT INTO store(name, owner_name, phone, status) VALUES
('小蓝鲸自营店', '小蓝鲸官方', '400-100-0001', 1),
('海底萌物旗舰店', '张海洋', '13900000002', 0),
('鲸喜零食铺', '李鲸鱼', '13700000003', 0);

INSERT INTO full_reduction(name, full_amount, reduce_amount, start_time, end_time, status) VALUES
('满200减30', 200.00, 30.00, '2026-01-01 00:00:00', '2027-01-01 00:00:00', 1);
