package io.hiwepy.admin.extras.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.hiwepy.admin.extras.config.dao.entities.ConfigEntity;
import io.hiwepy.admin.extras.config.web.dto.ConfigDTO;
import io.hiwepy.admin.extras.config.web.dto.ConfigNewDTO;
import io.hiwepy.boot.api.service.IBaseService;

/**
 * <p>
 * 三方集成配置信息 服务类
 * </p>
 *
 * @author wandl
 * @since 2022-06-14
 */
public interface IConfigService extends IBaseService<ConfigEntity> {

    boolean saveOrUpdate(ConfigNewDTO config);

    ConfigEntity getByDeptId(String deptId);

    ConfigEntity getByCorpId(String corpId);

    ConfigEntity getByAppId(String appId);

    ConfigDTO getConfigById(String id);

    ConfigDTO getConfigByDeptId(String deptId);

    ConfigDTO getConfigByCorpId(String corpId);

    ConfigDTO getConfigByAppId(String appId);

}
