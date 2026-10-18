package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.xiaolanjing.entity.SystemConfig;

import java.util.List;

public interface SystemConfigMapper {
    @Select("SELECT * FROM system_config ORDER BY id")
    List<SystemConfig> findAll();

    @Select("SELECT * FROM system_config WHERE config_key = #{key}")
    SystemConfig findByKey(String key);

    @Insert("INSERT INTO system_config(config_key, config_value) VALUES(#{configKey}, #{configValue})")
    int insert(SystemConfig config);

    @Update("UPDATE system_config SET config_value = #{configValue} WHERE id = #{id}")
    int update(SystemConfig config);
}
