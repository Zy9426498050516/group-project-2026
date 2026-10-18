package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.Param;
import org.xiaolanjing.entity.Product;

import java.util.List;

public interface ProductMapper {

    /** 查询所有商品（带品牌名） */
    List<Product> findAll();

    /** 按品牌 id 查询 */
    List<Product> findByBrandId(Long brandId);

    /** 按品牌名模糊查询 */
    List<Product> findByBrandName(String brandName);

    /** 按 id 查询（带品牌名） */
    Product findById(Long id);

    int insert(Product product);

    int update(Product product);

    int deleteById(Long id);

    /** 更新库存（增 / 减） */
    int updateStock(@Param("id") Long id, @Param("stock") Integer stock);
}
