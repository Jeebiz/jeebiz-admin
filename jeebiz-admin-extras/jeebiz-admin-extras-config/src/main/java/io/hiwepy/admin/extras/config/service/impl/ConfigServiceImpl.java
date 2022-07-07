package io.hiwepy.admin.extras.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.hiwepy.admin.extras.config.dao.ConfigItemMapper;
import io.hiwepy.admin.extras.config.dao.ConfigMapper;
import io.hiwepy.admin.extras.config.dao.entities.ConfigEntity;
import io.hiwepy.admin.extras.config.dao.entities.ConfigItemEntity;
import io.hiwepy.admin.extras.config.service.IConfigItemService;
import io.hiwepy.admin.extras.config.service.IConfigService;
import io.hiwepy.admin.extras.config.setup.Constants;
import io.hiwepy.admin.extras.config.web.dto.ConfigDTO;
import io.hiwepy.admin.extras.config.web.dto.ConfigItemDTO;
import io.hiwepy.admin.extras.config.web.dto.ConfigNewDTO;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 三方集成配置信息 服务实现类
 * </p>
 *
 * @author wandl
 * @since 2022-06-14
 */
@Service
public class ConfigServiceImpl extends BaseServiceImpl<ConfigMapper, ConfigEntity> implements IConfigService {

    @Autowired
    private IConfigItemService configItemService;

    @Override
    public Long getCount(ConfigEntity entity) {
        return getBaseMapper().selectCount(new LambdaQueryWrapper<ConfigEntity>()
                .eq(ConfigEntity::getId, entity.getId())
                .eq(ConfigEntity::getIsDelete, Constants.Normal.IS_DELETE_0));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(ConfigNewDTO config) {
        ConfigEntity entity = getBeanMapper().map(config, ConfigEntity.class);
        boolean rt = this.saveOrUpdate(entity);
        if(!CollectionUtils.isEmpty(config.getConfigList())){
            config.getConfigList().stream().forEach(item -> {
                ConfigItemEntity itemEntity = getBeanMapper().map(item, ConfigItemEntity.class);
                itemEntity.setConfigId(entity.getId());
                configItemService.saveOrUpdate(itemEntity);
            });
        }
        return rt;
    }

    @Override
    public ConfigDTO getConfigById(String id) {
        ConfigEntity entity = getBaseMapper().selectById(id);
        if(Objects.nonNull(entity)){
            List<ConfigItemEntity> list = configItemService.list(new LambdaQueryWrapper<ConfigItemEntity>()
                    .eq(ConfigItemEntity::getConfigId, entity.getId())
                    .eq(ConfigItemEntity::getIsDelete, Constants.Normal.IS_DELETE_0));

            ConfigDTO configDTO = getBeanMapper().map(entity, ConfigDTO.class);
            if(!CollectionUtils.isEmpty(list)){
                configDTO.setConfigList(list.stream().map(itemEntity -> getBeanMapper().map(itemEntity, ConfigItemDTO.class)).collect(Collectors.toList()));
            }
            return configDTO;
        }
        return null;
    }

}
