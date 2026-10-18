package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.xiaolanjing.entity.Customer;

import java.util.List;

public interface CustomerMapper {

    Customer findByUsername(String username);

    int insert(Customer customer);

    @Select("SELECT * FROM customer ORDER BY id DESC")
    List<Customer> findAll();

    @Select("SELECT * FROM customer WHERE id = #{id}")
    Customer findById(Long id);

    @Update("UPDATE customer SET password = #{password} WHERE id = #{id}")
    int updatePassword(@Param("id") Long id, @Param("password") String password);

    @Delete("DELETE FROM customer WHERE id = #{id}")
    int deleteById(Long id);
}
