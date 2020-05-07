package com.example.csgs.controller;

import com.example.csgs.bean.CommunityInfo;
import com.example.csgs.bean.DistrictInfo;
import com.example.csgs.mapper.DistrictDao;
import com.example.csgs.entity.DistrictEntity;
import com.example.csgs.service.LeaderViewService;
import com.example.csgs.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leader")
@Slf4j
public class LeaderViewController {
    @Autowired
    DistrictDao districtDao;
    @Autowired
    LeaderViewService leaderViewService;

    /**
     * 场景：此时，领导想要看的的整体各项数据（以区为单位展示数据信息内容）
     * 所以，我们要整理出每个区下，Residents、Houses、ParkingSpaces各自总和
     */
    @GetMapping("/getDistrictRPHList")
    public Object getDistrictRPHList() {
        List<DistrictInfo> districtRPHList = leaderViewService.getDistrictRPHList();
        if (!districtRPHList.isEmpty()) {
            return ResultUtils.success(districtRPHList, "获取所有区RPH信息成功！ ");
        }
        return ResultUtils.error("获取所有区RPH信息失败！ ");
    }

    /**
     * 场景：领导在选择查看某一片区数据情况时，前端得先从服务器获取所有片区名信息
     */
    @GetMapping("/getAllDistrictName")
    public Object getAllDistrictName() {
        //没有构建相应的service实现方法，主要是太简单。
        //如果你使用mybatis查询过程比较复杂，可以新建接口方法，并在service中实现
        Iterable<DistrictEntity> districtEntities = districtDao.findAll();
        return ResultUtils.success(districtEntities, "获取所有区名信息成功！");
    }

    /**
     * 场景：此时，领导查看的数据形式为：选择某一区对车位数、居民数、住宅数可视化数据查看
     * 所以从前端接受到内容主要是“在District表中区所对应的id号”
     */
    @GetMapping("/getCommunityRPHList/{id}")
    public Object getCommunityRPHList(@PathVariable String id) {
        List<CommunityInfo> communityRPHList = leaderViewService.getCommunityRPHList(Long.parseLong(id));
        if (!communityRPHList.isEmpty()) {
            return ResultUtils.success(communityRPHList, "获取" + id + "为区所有小区RPH信息成功！ ");
        }
        return ResultUtils.error("获取" + id + "为区所有小区RPH信息失败！ ");
    }
}
