# 小蓝鲸电商后台管理系统 🐳

一个基于 **Spring Boot + MyBatis + MySQL + Thymeleaf** 的电商后台管理系统，蓝色可爱主题。
管理员可管理首页轮播海报、商品（含品牌、库存、图片上传），顾客可管理购物车（全选 / 单选 / 自动计算总价），并支持按品牌名搜索商品。

---

## 一、技术栈

| 分类 | 技术 |
| --- | --- |
| 语言 / 运行时 | Java 17（JDK 17） |
| 构建工具 | Maven |
| 后端框架 | Spring Boot 3.2.5 + Spring MVC |
| 持久层 | MyBatis 3.0.3 |
| 数据库 | MySQL（使用 Navicat Premium 16 管理） |
| 模板引擎 | Thymeleaf + HTML/CSS/JS |
| 开发工具 | IntelliJ IDEA |

---

## 二、功能清单

### 管理员端
- ✅ 轮播图 / 活动海报的 **增、删、改、查**，支持从本地电脑 **上传图片**
- ✅ 商品的 **增、删、改、查**（含名称、品牌、价格、库存、描述、图片）
- ✅ 品牌的 **增、删、改、查**
- ✅ 商品库存 **增加 / 减少**（数量可以是任意多个）
- ✅ 管理员登录 / 退出（Session 会话 + 登录拦截器）

### 顾客端
- ✅ 商品浏览 + **按品牌名搜索**
- ✅ 购物车 **增、删、改、查**
- ✅ 打开购物车有 **全选** 选项，也可 **逐个勾选**
- ✅ 勾选商品后 **自动计算所选商品总价**
- ✅ 顾客注册 / 登录 / 退出

### 页面风格
- 🎨 蓝色渐变 + 圆角卡片 + 可爱鲸鱼元素（响应式布局）

---

## 三、项目结构

```
xiaolanjing
├── pom.xml                          # Maven 配置（Spring Boot 3.2.5 / JDK 17）
├── sql/xiaolanjing.sql              # 数据库建库建表 + 初始数据脚本
└── src/main
    ├── java/org/xiaolanjing
    │   ├── XiaolanjingApplication.java   # 启动类
    │   ├── config/WebConfig.java         # 静态资源映射 + 登录拦截器
    │   ├── controller/                   # 控制器（首页/登录/购物车/后台）
    │   ├── entity/                       # 实体类（Admin/Brand/Product/Banner/Customer/Cart）
    │   ├── vo/CartVO.java                # 购物车展示对象
    │   ├── mapper/                       # MyBatis 接口
    │   ├── interceptor/                  # 管理员/顾客登录拦截器
    │   └── util/FileUtil.java            # 图片上传工具
    └── resources
        ├── application.yml               # 数据库 / 端口 / 上传目录配置
        ├── mapper/*.xml                  # MyBatis SQL 映射
        ├── static/                       # 静态资源（css/图片/上传图片）
        └── templates/                    # Thymeleaf 页面
```

---

## 四、环境准备

请先确认本机已安装：

1. **JDK 17**（`java -version` 能显示 17）
2. **Maven**（`mvn -v` 能显示版本）
3. **IntelliJ IDEA**（社区版 / 旗舰版均可）
4. **MySQL**（建议 5.7+ 或 8.x，记住 root 密码）
5. **Navicat Premium 16**

---

## 五、数据库初始化（Navicat Premium 16）

1. 打开 **Navicat Premium 16**，点击左上角 **连接 → MySQL**：
   - 连接名：随便填，例如 `本地MySQL`
   - 主机：`localhost`，端口：`3306`
   - 用户名：`root`，密码：你安装 MySQL 时设置的密码
   - 点击 **测试连接**，成功后点 **确定**。

2. 在左侧双击该连接，打开连接后，点击菜单栏 **查询 → 新建查询**。

3. 打开项目里的 `sql/xiaolanjing.sql`，把内容 **全部复制** 到查询窗口，点击 **运行**（或按 `Ctrl + R`）。

4. 刷新左侧连接，即可看到 `xiaolanjing` 数据库，包含 6 张表：
   `admin`、`brand`、`product`、`banner`、`customer`、`cart`。

5. 初始账号（可直接登录）：
   - 管理员：`admin / 123456`
   - 顾客：`xiaolan / 123456`

> 说明：脚本执行时会 **先删除再重建** `xiaolanjing` 数据库，请确认库里没有重要数据。

---

## 六、修改数据库密码

如果你的 MySQL root 密码不是 `123456`，请打开
`src/main/resources/application.yml`，修改这两行：

```yaml
spring:
  datasource:
    username: root        # 改成你的 MySQL 用户名
    password: 123456      # 改成你的 MySQL 密码
```

---

## 七、在 IDEA 中运行

1. 打开 IDEA，点击 **File → Open**，选择项目根目录 `xiaolanjing`，点 **OK**。
   （IDEA 会自动识别为 Maven 项目，等待右下角 Maven 依赖下载完成。）

2. 确认 JDK 版本：
   - 菜单 **File → Project Structure → Project**，`SDK` 选择 **17**，`Language level` 选择 **17**。

3. 找到启动类 `src/main/java/org/xiaolanjing/XiaolanjingApplication.java`，
   点击 `main` 方法左边的 **绿色三角形 ▶** 运行（或右键 → Run）。

4. 看到控制台输出 `Tomcat started on port 8080` 即启动成功。

5. 打开浏览器访问：**http://localhost:8080/** 🎉

---

## 八、使用说明（功能演示）

### 1. 首页（顾客端）
- 顶部是 **轮播图 / 活动海报**，自动播放，可左右切换。
- 中间是 **商品列表**，每件商品显示图片、名称、品牌、价格、库存。
- 搜索框输入品牌名（如「小蓝鲸」）点击搜索，或点击品牌标签，即可 **按品牌名搜索商品**。
- 点击商品下方的 **「加入购物车」**（需先顾客登录）。

### 2. 顾客注册 / 登录
- 首页右上角点 **「顾客登录」**，没有账号先 **「立即注册」**。
- 也可用初始顾客账号 `xiaolan / 123456` 直接登录。

### 3. 购物车
- 进入 **「我的购物车」**，可看到已加购商品。
- 顶部有 **全选** 复选框，点击可全选 / 全不选；也可 **逐个勾选** 每个商品前的复选框。
- 勾选 / 取消勾选后，底部 **合计金额会自动重新计算**，并显示已选件数。
- 修改数量（改数字后点「更新」）、删除商品等均为购物车的增删改操作。

### 4. 管理员登录
- 首页右上角点 **「管理员」**，或直接访问 `http://localhost:8080/admin/login`。
- 用 `admin / 123456` 登录后进入后台仪表盘。

### 5. 轮播图 / 海报管理
- 左侧点 **「轮播图管理」**，可新增、编辑、删除海报。
- 新增时从 **本地电脑选择图片上传**，上传后会自动保存到项目根目录 `upload/` 文件夹，
  并通过 `/upload/xxx` 地址展示在首页轮播中。

### 6. 商品管理
- 左侧点 **「商品管理」**，可新增、编辑、删除商品。
- 新增 / 编辑时：填写名称、选择 **品牌**、价格、库存、描述，并 **上传商品图片**。
- 列表右侧 **「增减库存」**：输入数量后点 `＋` 增加、点 `－` 减少（数量可以是任意多个）。

### 7. 品牌管理
- 左侧点 **「品牌管理」**，可新增、重命名、删除品牌。
- 品牌用于商品分类，也是首页按品牌搜索的依据。

---

## 九、功能与需求对照表

| 需求 | 实现位置 |
| --- | --- |
| 管理员增删改查首页轮播海报（图片上传） | `BannerController` + `admin/banner*.html` |
| 管理员增删改查商品及信息（含品牌） | `ProductController` + `admin/product*.html` |
| 增加 / 减少商品数量（可多个） | `ProductController.updateStock` + 库存表单 |
| 顾客增删改查购物车 | `CartController` + `cart.html` |
| 购物车全选 + 逐个勾选 | `cart.html` 中的 checkbox + JS |
| 勾选自动计算总价 | `cart.html` 中的 `calcTotal()` JS |
| 按品牌名搜索商品 | `ProductMapper.findByBrandName` + 首页搜索框 |
| 商品附带图片（管理员本地上传） | `FileUtil` + `WebConfig` 静态映射 |
| 蓝色可爱主题 | `static/css/style.css` |

---

## 十、常见问题

**Q1：启动报 `Access denied for user 'root'`？**
→ `application.yml` 里的数据库账号密码没改对，改成你的 MySQL 密码。

**Q2：启动报 `Unknown database 'xiaolanjing'`？**
→ 还没执行 SQL 脚本，请按「五、数据库初始化」在 Navicat 中运行 `sql/xiaolanjing.sql`。

**Q3：图片上传后页面看不到图？**
→ 图片保存在项目根目录 `upload/` 文件夹，`WebConfig` 已把 `/upload/**` 映射到该目录；
   确认图片已上传成功、且浏览器直接访问 `http://localhost:8080/upload/文件名` 能打开。

**Q4：IDEA 提示找不到包 / 大量报错？**
→ 等待 Maven 依赖下载完成（右下角进度条），或点击右侧 **Maven → 刷新（Reload）**。

**Q5：端口 8080 被占用？**
→ 修改 `application.yml` 中 `server.port` 为其他端口（如 8081）。

**Q6：后台仪表盘的图表（柱状图 / 饼图）不显示？**
→ 图表使用 ECharts，通过 CDN 在线加载（staticfile.org，自动回退到 jsdelivr），需要电脑联网。
   若无法联网，可手动下载 `echarts.min.js` 放到 `src/main/resources/static/js/` 目录，
   并把 `admin/index.html` 里的 `<script src="https://...">` 改成 `<script src="/js/echarts.min.js"></script>`。
