package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.*;
import org.xiaolanjing.entity.Announcement;

import java.util.List;

public interface AnnouncementMapper {
    @Select("SELECT * FROM announcement ORDER BY id DESC")
    List<Announcement> findAll();

    @Select("SELECT * FROM announcement WHERE id = #{id}")
    Announcement findById(Long id);

    @Insert("INSERT INTO announcement(title, content, status) VALUES(#{title}, #{content}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Announcement announcement);

    @Update("UPDATE announcement SET title=#{title}, content=#{content}, status=#{status} WHERE id=#{id}")
    int update(Announcement announcement);

    @Delete("DELETE FROM announcement WHERE id=#{id}")
    int deleteById(Long id);
}
