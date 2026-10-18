package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.xiaolanjing.entity.OperationLog;

import java.util.List;

public interface OperationLogMapper {
    @Select("SELECT * FROM operation_log ORDER BY id DESC LIMIT 500")
    List<OperationLog> findAll();

    @Insert("INSERT INTO operation_log(admin_name, operation, ip) VALUES(#{adminName}, #{operation}, #{ip})")
    int insert(OperationLog log);
}
