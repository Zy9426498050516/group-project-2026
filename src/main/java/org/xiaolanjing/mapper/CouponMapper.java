package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.*;
import org.xiaolanjing.entity.Coupon;

import java.util.List;

public interface CouponMapper {
    @Select("SELECT * FROM coupon ORDER BY id DESC")
    List<Coupon> findAll();

    @Select("SELECT * FROM coupon WHERE id = #{id}")
    Coupon findById(Long id);

    @Insert("INSERT INTO coupon(name, amount, min_amount, total_count, status, start_time, end_time) VALUES(#{name}, #{amount}, #{minAmount}, #{totalCount}, #{status}, #{startTime}, #{endTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Coupon coupon);

    @Update("UPDATE coupon SET name=#{name}, amount=#{amount}, min_amount=#{minAmount}, total_count=#{totalCount}, status=#{status}, start_time=#{startTime}, end_time=#{endTime} WHERE id=#{id}")
    int update(Coupon coupon);

    @Delete("DELETE FROM coupon WHERE id=#{id}")
    int deleteById(Long id);
}
