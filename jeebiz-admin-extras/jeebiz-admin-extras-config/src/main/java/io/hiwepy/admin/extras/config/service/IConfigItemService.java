package io.hiwepy.admin.extras.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.hiwepy.admin.extras.config.dao.entities.ConfigEntity;
import io.hiwepy.admin.extras.config.dao.entities.ConfigItemEntity;
import io.hiwepy.admin.extras.config.web.dto.ConfigDTO;

/**
 * <p>
 * 三方集成配置信息 服务类
 * </p>
 *
 * @author wandl
 * @since 2022-06-14
 */
public interface IConfigItemService extends IService<ConfigItemEntity> {

}