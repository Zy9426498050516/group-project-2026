package org.xiaolanjing.mapper;

import org.apache.ibatis.annotations.*;
import org.xiaolanjing.entity.ShippingTemplate;

import java.util.List;

public interface ShippingTemplateMapper {
    @Select("SELECT * FROM shipping_template ORDER BY id")
    List<ShippingTemplate> findAll();

    @Select("SELECT * FROM shipping_template WHERE id = #{id}")
    ShippingTemplate findById(Long id);

    @Insert("INSERT INTO shipping_template(name, first_weight, first_price, continue_price) VALUES(#{name}, #{firstWeight}, #{firstPrice}, #{continuePrice})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ShippingTemplate template);

    @Update("UPDATE shipping_template SET name=#{name}, first_weight=#{firstWeight}, first_price=#{firstPrice}, continue_price=#{continuePrice} WHERE id=#{id}")
    int update(ShippingTemplate template);

    @Delete("DELETE FROM shipping_template WHERE id=#{id}")
    int deleteById(Long id);
}
