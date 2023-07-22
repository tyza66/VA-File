package com.tyza66.vfm_core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyza66.vfm_core.mapper.VfmLocationMapper;
import com.tyza66.vfm_core.pojo.VfmLocation;
import com.tyza66.vfm_core.service.VfmLocationService;
import org.springframework.stereotype.Service;

/**
 * Author: tyza66
 * Date: 2023/7/22 14:56
 * Github: https://github.com/tyza66
 **/

@Service
public class VfmLocationServiceImpl extends ServiceImpl<VfmLocationMapper, VfmLocation> implements VfmLocationService {
    @Override
    public String getNowLocation() {
        return baseMapper.selectById(1).getVfmlLocation();
    }
}
