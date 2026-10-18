package org.xiaolanjing.mapper;

import org.xiaolanjing.entity.Banner;

import java.util.List;

public interface BannerMapper {

    List<Banner> findAll();

    List<Banner> findByStatus(Integer status);

    Banner findById(Long id);

    int insert(Banner banner);

    int update(Banner banner);

    int deleteById(Long id);
}
