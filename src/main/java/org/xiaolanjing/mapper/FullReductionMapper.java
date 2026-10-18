package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.*;
import org.xiaolanjing.entity.FullReduction;

import java.util.List;

public interface FullReductionMapper {
    @Select("SELECT * FROM full_reduction ORDER BY id DESC")
    List<FullReduction> findAll();

    @Select("SELECT * FROM full_reduction WHERE id = #{id}")
    FullReduction findById(Long id);

    @Insert("INSERT INTO full_reduction(name, full_amount, reduce_amount, start_time, end_time, status) VALUES(#{name}, #{fullAmount}, #{reduceAmount}, #{startTime}, #{endTime}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FullReduction fullReduction);

    @Update("UPDATE full_reduction SET name=#{name}, full_amount=#{fullAmount}, reduce_amount=#{reduceAmount}, start_time=#{startTime}, end_time=#{endTime}, status=#{status} WHERE id=#{id}")
    int update(FullReduction fullReduction);

    @Delete("DELETE FROM full_reduction WHERE id=#{id}")
    int deleteById(Long id);
}
