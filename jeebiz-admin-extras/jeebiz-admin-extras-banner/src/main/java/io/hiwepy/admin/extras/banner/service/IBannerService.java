package io.hiwepy.admin.extras.banner.service;

import io.hiwepy.admin.extras.banner.dao.entities.BannerEntity;
import io.hiwepy.admin.extras.banner.web.dto.BannerDTO;
import io.hiwepy.boot.api.service.IBaseService;

import java.util.List;

public interface IBannerService extends IBaseService<BannerEntity> {

    /**
     * 根据条件查询对应的横幅轮播图
     * @param appId  客户端应用ID
     * @param appChannel 客户端应用渠道编码
     * @param region 地区编码
     * @param language 语区标签
     * @param type 横幅类型
     * @return
     */
    List<BannerDTO> getBannerList(String appId, String appChannel, String region, String language, Integer type);

}
