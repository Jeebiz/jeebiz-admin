package io.hiwepy.admin.extras.config.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.hiwepy.admin.extras.config.utils.enums.ConfigType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("t_thirdparty_config")
public class ConfigEntity implements Serializable {

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

    /**
     * 是否删除（0:未删除,1:已删除）
     */
    private Integer isDelete;

    /**
     * 创建人ID
     */
    private Long creator;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    private Long modifyer;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;


}
