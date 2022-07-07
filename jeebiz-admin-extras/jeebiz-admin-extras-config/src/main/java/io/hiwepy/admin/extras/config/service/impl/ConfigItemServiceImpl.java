package io.hiwepy.admin.extras.config.service.impl;

import io.hiwepy.admin.extras.config.dao.ConfigItemMapper;
import io.hiwepy.admin.extras.config.dao.ConfigMapper;
import io.hiwepy.admin.extras.config.dao.entities.ConfigEntity;
import io.hiwepy.admin.extras.config.dao.entities.ConfigItemEntity;
import io.hiwepy.admin.extras.config.service.IConfigItemService;
import io.hiwepy.admin.extras.config.service.IConfigService;
import io.hiwepy.admin.extras.config.web.dto.ConfigDTO;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

}
