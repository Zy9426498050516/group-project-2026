package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.*;
import org.xiaolanjing.entity.ExpressCompany;

import java.util.List;

public interface ExpressCompanyMapper {
    @Select("SELECT * FROM express_company ORDER BY id")
    List<ExpressCompany> findAll();

    @Insert("INSERT INTO express_company(name, code) VALUES(#{name}, #{code})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ExpressCompany company);

    @Update("UPDATE express_company SET name=#{name}, code=#{code} WHERE id=#{id}")
    int update(ExpressCompany company);

    @Delete("DELETE FROM express_company WHERE id=#{id}")
    int deleteById(Long id);
}
