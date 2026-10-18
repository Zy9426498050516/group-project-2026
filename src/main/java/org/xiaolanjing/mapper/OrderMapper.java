package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.*;
import org.xiaolanjing.entity.Order;
import org.xiaolanjing.entity.OrderItem;

import java.util.List;

public interface OrderMapper {
    @Select("SELECT o.*, c.username AS customer_name FROM orders o LEFT JOIN customer c ON o.customer_id = c.id ORDER BY o.id DESC")
    List<Order> findAll();

    @Select("SELECT o.*, c.username AS customer_name FROM orders o LEFT JOIN customer c ON o.customer_id = c.id WHERE o.id = #{id}")
    Order findById(Long id);

    @Select("SELECT * FROM order_item WHERE order_id = #{orderId}")
    List<OrderItem> findItemsByOrderId(Long orderId);

    @Insert("INSERT INTO orders(order_no, customer_id, total_amount, status, receiver, phone, address) VALUES(#{orderNo}, #{customerId}, #{totalAmount}, #{status}, #{receiver}, #{phone}, #{address})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertOrder(Order order);

    @Insert("INSERT INTO order_item(order_id, product_id, product_name, product_image, price, quantity) VALUES(#{orderId}, #{productId}, #{productName}, #{productImage}, #{price}, #{quantity})")
    int insertItem(OrderItem item);

    @Update("UPDATE orders SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    @Delete("DELETE FROM order_item WHERE order_id = #{orderId}")
    int deleteItemsByOrderId(Long orderId);

    @Delete("DELETE FROM orders WHERE id = #{id}")
    int deleteById(Long id);
}
