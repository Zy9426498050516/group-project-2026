package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.xiaolanjing.entity.Cart;
import org.xiaolanjing.vo.CartVO;

import java.util.List;

public interface CartMapper {

    /** 查询某顾客的购物车（带商品和品牌信息） */
    List<CartVO> findByCustomerId(Long customerId);

    Cart findByCustomerIdAndProductId(@Param("customerId") Long customerId,
                                      @Param("productId") Long productId);

    int insert(Cart cart);

    int updateQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    int deleteById(Long id);

    @Delete("DELETE FROM cart WHERE customer_id = #{customerId}")
    int deleteByCustomerId(Long customerId);
}
