package io.hiwepy.admin.authz.org.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@TableName("t_tree_config")
public class TreeConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    @TableField(value = "tree_label" )
    private String treeLabel;

    /**
     * 名称
     */
    @TableField(value = "tree_type" )
    private String treeType;
    /**
     * 上级节点id
     */
    @TableField(value = "tree_parent_id" )
    @JsonSerialize(using = ToStringSerializer.class)
    private Long treeParentId;

    /**
     * 节点类型（对应t_org_tree_type中的type)
     */
    @TableField(value = "tree_data_type" )
    private String treeDateType;

    @TableField(value = "tree_data_source" )
    private String treeDataSource;

    /**
     * 子节点
     */
    @TableField(exist = false)
    private List<TreeConfigEntity> children;

}
