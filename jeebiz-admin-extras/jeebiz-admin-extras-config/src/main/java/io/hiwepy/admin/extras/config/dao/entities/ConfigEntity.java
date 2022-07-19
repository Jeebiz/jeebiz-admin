package io.hiwepy.admin.extras.config.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     *  钉钉机构 CropId
     */
    @TableField("corp_id")
    private String corpId;

    /**
     *  钉钉机构密钥
     */
    @TableField("corp_secret")
    private String corpSecret;

    /**
     *  组织机构ID
     */
    @TableField("dept_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;

    /**
     *  配置类型
     */
    @TableField("config_type")
    private ConfigType configType;

}
