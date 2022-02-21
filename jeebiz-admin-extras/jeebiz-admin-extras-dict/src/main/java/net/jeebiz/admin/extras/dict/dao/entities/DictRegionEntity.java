package net.jeebiz.admin.extras.dict.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import net.jeebiz.boot.api.dao.entities.PaginationEntity;
import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 国家地区代码 http://doc.chacuo.net/iso-3166-1
 * </p>
 *
 * @author wandl
 * @since 2022-01-22
 */
@Alias("DictRegionEntity")
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_data_region")
public class DictRegionEntity extends PaginationEntity<DictRegionEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 两位字母
     */
    @TableField(value = "code2")
    private String code2;

    /**
     * 三位字母
     */
    @TableField(value = "code3")
    private String code3;

    /**
     * 数字
     */
    @TableField(value = "number")
    private String number;

    /**
     * ISO 3166-2相应代码
     */
    @TableField(value = "iso_code")
    private String isoCode;

    /**
     * 国家或地区（ISO 英文用名）
     */
    @TableField(value = "iso_name")
    private String isoName;

    /**
     * 中国大陆 惯用名
     */
    @TableField(value = "cn_name")
    private String cnName;

    /**
     * 中国台湾 惯用名
     */
    @TableField(value = "tw_name")
    private String twName;

    /**
     * 中国香港 惯用名
     */
    @TableField(value = "hk_name")
    private String hkName;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;


}
