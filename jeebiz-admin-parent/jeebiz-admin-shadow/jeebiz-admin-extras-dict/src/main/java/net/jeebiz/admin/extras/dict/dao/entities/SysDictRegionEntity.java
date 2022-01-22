package net.jeebiz.admin.extras.dict.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_dict_region")
public class SysDictRegionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 两位字母
     */
    private String code2;

    /**
     * 三位字母
     */
    private String code3;

    /**
     * 数字
     */
    private String number;

    /**
     * ISO 3166-2相应代码
     */
    private String isoCode;

    /**
     * 国家或地区（ISO 英文用名）
     */
    private String isoName;

    /**
     * 中国 惯用名
     */
    private String cnName;

    /**
     * 台湾 惯用名
     */
    private String twName;

    /**
     * 香港 惯用名
     */
    private String hkName;

    /**
     * 备注
     */
    private String remark;


}
