package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.*;
import org.xiaolanjing.entity.Message;

import java.util.List;

public interface MessageMapper {
    @Select("SELECT * FROM message ORDER BY id DESC")
    List<Message> findAll();

    @Insert("INSERT INTO message(title, content) VALUES(#{title}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Message message);

    @Delete("DELETE FROM message WHERE id=#{id}")
    int deleteById(Long id);
}
