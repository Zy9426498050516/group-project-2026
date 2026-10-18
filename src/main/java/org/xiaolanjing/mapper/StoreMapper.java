package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.*;
import org.xiaolanjing.entity.Store;

import java.util.List;

public interface StoreMapper {
    @Select("SELECT * FROM store ORDER BY id DESC")
    List<Store> findAll();

    @Select("SELECT * FROM store WHERE id = #{id}")
    Store findById(Long id);

    @Insert("INSERT INTO store(name, owner_name, phone, status) VALUES(#{name}, #{ownerName}, #{phone}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Store store);

    @Update("UPDATE store SET name=#{name}, owner_name=#{ownerName}, phone=#{phone} WHERE id=#{id}")
    int update(Store store);

    @Update("UPDATE store SET status=#{status} WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    @Delete("DELETE FROM store WHERE id=#{id}")
    int deleteById(Long id);
}
