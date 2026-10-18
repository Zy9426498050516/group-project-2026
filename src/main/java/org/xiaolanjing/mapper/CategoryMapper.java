package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.*;
import org.xiaolanjing.entity.Category;

import java.util.List;

public interface CategoryMapper {
    @Select("SELECT * FROM category ORDER BY sort_order, id")
    List<Category> findAll();

    @Select("SELECT * FROM category WHERE id = #{id}")
    Category findById(Long id);

    @Insert("INSERT INTO category(name, sort_order, status) VALUES(#{name}, #{sortOrder}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Category category);

    @Update("UPDATE category SET name=#{name}, sort_order=#{sortOrder}, status=#{status} WHERE id=#{id}")
    int update(Category category);

    @Delete("DELETE FROM category WHERE id=#{id}")
    int deleteById(Long id);
}
