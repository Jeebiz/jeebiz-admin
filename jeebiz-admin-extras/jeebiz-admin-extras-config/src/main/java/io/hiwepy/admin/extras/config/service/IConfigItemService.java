package io.hiwepy.admin.extras.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.hiwepy.admin.extras.config.dao.entities.ConfigItemEntity;
import io.hiwepy.admin.extras.config.utils.enums.ConfigItemType;

import java.util.List;

/**
 * <p>
 * 三方集成配置信息 服务类
 * </p>
 *
 * @author wandl
 * @since 2022-06-14
 */
public interface IConfigItemService extends IService<ConfigItemEntity> {

    ConfigItemEntity getByAppId(String appId);

    List<ConfigItemEntity> getListByAppId(String appId);

    List<ConfigItemEntity> getListByConfigId(Long configId);

    List<ConfigItemEntity> getListByConfigId(Long configId, ConfigItemType configType);

}
