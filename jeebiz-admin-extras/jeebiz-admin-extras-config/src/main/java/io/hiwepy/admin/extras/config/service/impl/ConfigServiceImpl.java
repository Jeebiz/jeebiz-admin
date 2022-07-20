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
import io.hiwepy.admin.extras.config.utils.enums.ConfigItemType;
import io.hiwepy.admin.extras.config.web.dto.ConfigDTO;
import io.hiwepy.admin.extras.config.web.dto.ConfigItemDTO;
import io.hiwepy.admin.extras.config.web.dto.ConfigItemNewDTO;
import io.hiwepy.admin.extras.config.web.dto.ConfigNewDTO;
import io.hiwepy.admin.extras.redis.setup.BizRedisKey;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private RedisOperationTemplate redisOperation;

    @Override
    public Long getCount(ConfigEntity entity) {
        return getBaseMapper().selectCount(new LambdaQueryWrapper<ConfigEntity>()
                .eq(ConfigEntity::getId, entity.getId())
                .eq(ConfigEntity::getIsDelete, Constants.Normal.IS_DELETE_0));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdate(ConfigNewDTO config) {
        // 1、如果是更新且已经设置过，则返回
        ConfigEntity entity = getBeanMapper().map(config, ConfigEntity.class);
        String userIdLong = SubjectUtils.getPrincipal(ShiroPrincipal.class).getUserid();
        if (Objects.isNull(entity.getId())){
            entity.setCreateTime(LocalDateTime.now());
            entity.setCreator(userIdLong);
        } else {
            entity.setModifyTime(LocalDateTime.now());
            entity.setModifyer(userIdLong);
        }
        boolean rt = this.saveOrUpdate(entity);
        if(!CollectionUtils.isEmpty(config.getConfigList())){
            config.getConfigList().stream().forEach(item -> {
                ConfigItemEntity itemEntity = getBeanMapper().map(item, ConfigItemEntity.class);
                itemEntity.setConfigId(entity.getId());
                if (Objects.isNull(itemEntity.getId())){
                    itemEntity.setCreateTime(LocalDateTime.now());
                    itemEntity.setCreator(userIdLong);
                } else {
                    itemEntity.setModifyTime(LocalDateTime.now());
                    itemEntity.setModifyer(userIdLong);
                }
                configItemService.saveOrUpdate(itemEntity);

                // String redisKey = itemEntity.getConfigType().getKey(config.getCorpId());
                // redisOperation.hmSet(redisKey, JSONObject.parseObject(JSONObject.toJSONString(entity), Map.class));

            });

            // 缓存deptId与cropId对应关系
            String redisKey = entity.getConfigType().getKey(entity.getDeptId());
            redisOperation.hSet(redisKey, "corpId", entity.getCorpId());

            // 缓存钉钉配置
            Map<ConfigItemType, List<ConfigItemNewDTO>> configMap = config.getConfigList().stream().collect(Collectors.groupingBy(ConfigItemNewDTO::getConfigType));
            configMap.entrySet().stream().forEach(item -> {
                Map<String, Object> configItemMap = item.getValue().stream().collect(Collectors.toMap(ConfigItemNewDTO::getConfigKey, ConfigItemNewDTO::getConfigValue));
                String itemRedisKey = BizRedisKey.THIRD_CONFIG_ITEM.getKey(config.getCorpId(), item.getKey().getKey());
                redisOperation.hmSet(itemRedisKey, configItemMap);
            });

        }
        return rt;
    }

    @Override
    public ConfigEntity getByDeptId(String deptId) {
        return getBaseMapper().selectOne(new LambdaQueryWrapper<ConfigEntity>()
                .eq(ConfigEntity::getDeptId, deptId).last(" limit 1"));
    }

    @Override
    public ConfigEntity getByCorpId(String corpId) {
        return getBaseMapper().selectOne(new LambdaQueryWrapper<ConfigEntity>()
                .eq(ConfigEntity::getCorpId, corpId).last(" limit 1"));
    }

    @Override
    public ConfigEntity getByAppId(String appId) {
        ConfigItemEntity itemEntity = configItemService.getOne(new LambdaQueryWrapper<ConfigItemEntity>()
                .eq(ConfigItemEntity::getConfigValue, appId)
                .eq(ConfigItemEntity::getIsDelete, Constants.Normal.IS_DELETE_0)
                .last(" limit 1"));
        if(Objects.isNull(itemEntity)){
            return null;
        }
        return getBaseMapper().selectById(itemEntity.getConfigId());
    }

    @Override
    public ConfigDTO getConfigById(String id) {
        ConfigEntity entity = getBaseMapper().selectById(id);
        if(Objects.isNull(entity)){
            return null;
        }
        return this.initConfigDto(entity);
    }

    @Override
    public ConfigDTO getConfigByDeptId(String deptId) {
        ConfigEntity entity = this.getByDeptId(deptId);
        if(Objects.isNull(entity)){
            return null;
        }
        return this.initConfigDto(entity);
    }

    @Override
    public ConfigDTO getConfigByCorpId(String corpId) {
        ConfigEntity entity = this.getByCorpId(corpId);
        if(Objects.isNull(entity)){
            return null;
        }
        return this.initConfigDto(entity);
    }

    @Override
    public ConfigDTO getConfigByAppId(String appId) {
        ConfigEntity entity = this.getByAppId(appId);
        if(Objects.isNull(entity)){
            return null;
        }
        return this.initConfigDto(entity);
    }

    protected ConfigDTO initConfigDto(ConfigEntity entity){
        if(Objects.isNull(entity)){
            return null;
        }
        List<ConfigItemEntity> list = configItemService.list(new LambdaQueryWrapper<ConfigItemEntity>()
                .eq(ConfigItemEntity::getConfigId, entity.getId())
                .eq(ConfigItemEntity::getIsDelete, Constants.Normal.IS_DELETE_0));

        ConfigDTO configDTO = getBeanMapper().map(entity, ConfigDTO.class);
        if(!CollectionUtils.isEmpty(list)){
            configDTO.setConfigList(list.stream().map(itemEntity -> getBeanMapper().map(itemEntity, ConfigItemDTO.class)).collect(Collectors.toList()));
        }
        return configDTO;
    }

}
