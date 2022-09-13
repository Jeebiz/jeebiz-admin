package io.hiwepy.admin.extras.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.hiwepy.admin.extras.config.dao.ConfigItemMapper;
import io.hiwepy.admin.extras.config.dao.entities.ConfigItemEntity;
import io.hiwepy.admin.extras.config.service.IConfigItemService;
import io.hiwepy.admin.extras.config.setup.Constants;
import io.hiwepy.admin.extras.config.utils.enums.ConfigItemType;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 三方集成配置信息 服务实现类
 * </p>
 *
 * @author wandl
 * @since 2022-06-14
 */
@Service
public class ConfigItemServiceImpl extends BaseServiceImpl<ConfigItemMapper, ConfigItemEntity> implements IConfigItemService {

    @Override
    public ConfigItemEntity getByAppId(String appId) {
        return getBaseMapper().selectOne(new LambdaQueryWrapper<ConfigItemEntity>()
                .eq(ConfigItemEntity::getConfigValue, appId)
                .eq(ConfigItemEntity::getIsDelete, Constants.Normal.IS_DELETE_0)
                .last(" limit 1"));
    }

    @Override
    public List<ConfigItemEntity> getListByAppId(String appId) {
        ConfigItemEntity itemEntity = this.getById(appId);
        if(Objects.isNull(itemEntity)){
            return null;
        }
        return this.getListByConfigId(itemEntity.getConfigId(), itemEntity.getConfigType());
    }

    @Override
    public List<ConfigItemEntity> getListByConfigId(Long configId) {
        return getBaseMapper().selectList(new LambdaQueryWrapper<ConfigItemEntity>()
                .eq(ConfigItemEntity::getConfigId, configId)
                .eq(ConfigItemEntity::getIsDelete, Constants.Normal.IS_DELETE_0));
    }

    @Override
    public List<ConfigItemEntity> getListByConfigId(Long configId, ConfigItemType configType) {
        return getBaseMapper().selectList(new LambdaQueryWrapper<ConfigItemEntity>()
                .eq(ConfigItemEntity::getConfigId, configId)
                .eq(ConfigItemEntity::getConfigType, configType)
                .eq(ConfigItemEntity::getIsDelete, Constants.Normal.IS_DELETE_0));
    }

}
