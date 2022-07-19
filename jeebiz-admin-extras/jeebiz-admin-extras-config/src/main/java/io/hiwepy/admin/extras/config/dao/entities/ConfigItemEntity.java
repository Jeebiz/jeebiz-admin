package io.hiwepy.admin.extras.config.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.hiwepy.admin.extras.config.utils.enums.ConfigItemType;
import io.hiwepy.boot.api.dao.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 三方集成配置信息项
 * </p>
 *
 * @author wandl
 * @since 2022-06-14
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_data_config_item")
public class ConfigItemEntity extends BaseEntity<ConfigItemEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置项主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 配置信息 ID
     */
    @TableField("config_id")
    private Long configId;

    /**
     * 配置项分类
     */
    @TableField("config_type")
    private ConfigItemType configType;

    /**
     * 配置项名称，如：AppKey
     */
    @TableField("config_key")
    private String configKey;

    /**
     * 配置项值，如：xxxxx12645645
     */
    @TableField("config_value")
    private String configValue;


}
