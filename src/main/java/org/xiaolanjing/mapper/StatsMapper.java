package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 数据分析统计查询
 */
public interface StatsMapper {
    @Select("SELECT COUNT(*) FROM orders")
    Long countOrders();

    @Select("SELECT IFNULL(SUM(total_amount),0) FROM orders")
    BigDecimal sumSales();

    @Select("SELECT COUNT(*) FROM orders WHERE DATE(create_time)=CURDATE()")
    Long countTodayOrders();

    @Select("SELECT IFNULL(SUM(total_amount),0) FROM orders WHERE DATE(create_time)=CURDATE()")
    BigDecimal sumTodaySales();

    @Select("SELECT COUNT(*) FROM customer")
    Long countCustomers();

    @Select("SELECT COUNT(*) FROM product")
    Long countProducts();

    @Select("SELECT status, COUNT(*) AS cnt, IFNULL(SUM(total_amount),0) AS amount FROM orders GROUP BY status")
    List<Map<String, Object>> orderStatusStats();

    @Select("SELECT oi.product_name AS name, SUM(oi.quantity) AS qty, IFNULL(SUM(oi.quantity*oi.price),0) AS amount FROM order_item oi GROUP BY oi.product_name ORDER BY qty DESC LIMIT 10")
    List<Map<String, Object>> topProducts();

    @Select("SELECT c.username AS name, COUNT(o.id) AS orderCount, IFNULL(SUM(o.total_amount),0) AS amount FROM customer c LEFT JOIN orders o ON o.customer_id=c.id GROUP BY c.id ORDER BY amount DESC LIMIT 10")
    List<Map<String, Object>> topCustomers();

    @Select("SELECT DATE(create_time) AS d, COUNT(*) AS cnt, IFNULL(SUM(total_amount),0) AS amount FROM orders GROUP BY DATE(create_time) ORDER BY d DESC LIMIT 7")
    List<Map<String, Object>> salesTrend();

    @Select("SELECT table_name AS name, table_rows AS rows FROM information_schema.tables WHERE table_schema = 'xiaolanjing' ORDER BY table_name")
    List<Map<String, Object>> listTables();
}
