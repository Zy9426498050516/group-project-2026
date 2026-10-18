package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.*;
import org.xiaolanjing.entity.StoreCategory;

import java.util.List;

public interface StoreCategoryMapper {
    @Select("SELECT * FROM store_category ORDER BY sort_order, id")
    List<StoreCategory> findAll();

    @Insert("INSERT INTO store_category(name, sort_order) VALUES(#{name}, #{sortOrder})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(StoreCategory category);

    @Update("UPDATE store_category SET name=#{name}, sort_order=#{sortOrder} WHERE id=#{id}")
    int update(StoreCategory category);

    @Delete("DELETE FROM store_category WHERE id=#{id}")
    int deleteById(Long id);
}
