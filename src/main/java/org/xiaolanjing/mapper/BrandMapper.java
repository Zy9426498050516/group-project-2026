package org.xiaolanjing.mapper;

import org.xiaolanjing.entity.Brand;

import java.util.List;

public interface BrandMapper {

    List<Brand> findAll();

    Brand findById(Long id);

    Brand findByName(String name);

    int insert(Brand brand);

    int update(Brand brand);

    int deleteById(Long id);
}
