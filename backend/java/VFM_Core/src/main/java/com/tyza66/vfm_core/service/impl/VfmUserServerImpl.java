package com.tyza66.vfm_core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyza66.vfm_core.mapper.VfmUserMapper;
import com.tyza66.vfm_core.pojo.VfmUser;
import com.tyza66.vfm_core.service.VfmUserServer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: tyza66
 * Date: 2023/7/22 15:24
 * Github: https://github.com/tyza66
 **/

@Service
public class VfmUserServerImpl extends ServiceImpl<VfmUserMapper, VfmUser> implements VfmUserServer {
    @Override
    public VfmUser login(String username, String password) {
        QueryWrapper<VfmUser> vfmUserQueryWrapper = new QueryWrapper<>();
        vfmUserQueryWrapper.eq("vfmu_username", username);
        vfmUserQueryWrapper.eq("vfmu_password", password);
        List<VfmUser> vfmUsers = baseMapper.selectList(vfmUserQueryWrapper);
        if(vfmUsers.size()<=0){
            return null;
        }
        return vfmUsers.get(0);
    }
}
