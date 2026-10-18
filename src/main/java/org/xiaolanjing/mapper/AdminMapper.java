package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.*;
import org.xiaolanjing.entity.Admin;

import java.util.List;

public interface AdminMapper {

    Admin findByUsername(String username);

    @Select("SELECT * FROM admin ORDER BY id")
    List<Admin> findAll();

    @Select("SELECT * FROM admin WHERE id = #{id}")
    Admin findById(Long id);

    @Insert("INSERT INTO admin(username, password) VALUES(#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertAdmin(Admin admin);

    @Update("UPDATE admin SET password = #{password} WHERE id = #{id}")
    int updatePassword(@Param("id") Long id, @Param("password") String password);

    @Delete("DELETE FROM admin WHERE id = #{id}")
    int deleteById(Long id);
}
