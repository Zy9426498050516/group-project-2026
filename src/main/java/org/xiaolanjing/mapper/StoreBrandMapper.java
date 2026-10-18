package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.*;
import org.xiaolanjing.entity.StoreBrand;

import java.util.List;

public interface StoreBrandMapper {
    @Select("SELECT * FROM store_brand ORDER BY sort_order, id")
    List<StoreBrand> findAll();

    @Insert("INSERT INTO store_brand(name, sort_order) VALUES(#{name}, #{sortOrder})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(StoreBrand brand);

    @Update("UPDATE store_brand SET name=#{name}, sort_order=#{sortOrder} WHERE id=#{id}")
    int update(StoreBrand brand);

    @Delete("DELETE FROM store_brand WHERE id=#{id}")
    int deleteById(Long id);
}
