package io.hiwepy.admin.extras.config.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.admin.extras.config.utils.enums.ConfigType;
import io.hiwepy.boot.api.dao.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 三方集成配置信息
 * </p>
 *
 * @author wandl
 * @since 2022-06-14
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_data_config")
public class ConfigEntity extends BaseEntity<ConfigEntity> implements Serializable  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *  钉钉机构 CropId 
     */
    private String corpId;

    /**
     *  组织机构ID 
     */
    private Long deptId;

    /**
     *  配置类型 
     */
    private ConfigType configType;

}
