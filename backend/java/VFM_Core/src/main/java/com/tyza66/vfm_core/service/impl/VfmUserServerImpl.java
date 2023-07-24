package com.tyza66.vfm_core.service.impl;

import cn.hutool.core.lang.UUID;
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
        if(vfmUsers.get(0).getIsDelete()){
            return null;
        }
        return vfmUsers.get(0);
    }

    @Override
    public Boolean register(String username, String password,VfmUser vfmUser){
        QueryWrapper<VfmUser> vfmUserQueryWrapper = new QueryWrapper<>();
        vfmUserQueryWrapper.eq("vfmu_username", username);
        List<VfmUser> vfmUsers = baseMapper.selectList(vfmUserQueryWrapper);
        if(vfmUsers.size()>0){
            return false;
        }
        UUID uuid = UUID.fastUUID();
        VfmUser vfmUser1 = new VfmUser(0, uuid.toString(), username, password,
                vfmUser.getCreateUserId(), vfmUser.getCreateUserId(),
                "SYSTEM", "SYSTEM",
                false);
        if(baseMapper.insert(vfmUser1)>0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean changePassword(String username, String newPassword) {
        QueryWrapper<VfmUser> vfmUserQueryWrapper = new QueryWrapper<>();
        vfmUserQueryWrapper.eq("vfmu_username", username);
        List<VfmUser> vfmUsers = baseMapper.selectList(vfmUserQueryWrapper);
        if(vfmUsers.size()<=0){
            return false;
        }
        VfmUser vfmUser = vfmUsers.get(0);
        vfmUser.setVfmuPassword(newPassword);
        return baseMapper.updateById(vfmUser)>0;
    }

    @Override
    public Boolean disableUser(String username) {
        QueryWrapper<VfmUser> vfmUserQueryWrapper = new QueryWrapper<>();
        vfmUserQueryWrapper.eq("vfmu_username", username);
        List<VfmUser> vfmUsers = baseMapper.selectList(vfmUserQueryWrapper);
        if(vfmUsers==null||vfmUsers.size()<=0){
            return false;
        }
        VfmUser vfmUser = vfmUsers.get(0);
        vfmUser.setIsDelete(true);
        return baseMapper.updateById(vfmUser)>0;
    }

    @Override
    public Boolean enableUser(String username) {
        QueryWrapper<VfmUser> vfmUserQueryWrapper = new QueryWrapper<>();
        vfmUserQueryWrapper.eq("vfmu_username", username);
        List<VfmUser> vfmUsers = baseMapper.selectList(vfmUserQueryWrapper);
        if(vfmUsers==null||vfmUsers.size()<=0){
            return false;
        }
        VfmUser vfmUser = vfmUsers.get(0);
        vfmUser.setIsDelete(false);
        return baseMapper.updateById(vfmUser)>0;
    }
}
