package io.hiwepy.admin.authz.org.setup.strategy;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TreeNode {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("树节点id")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty("树节点名称")
    private String label;

    /**
     * 上级节点id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 节点类型（对应t_org_tree_type中的type)
     */
    @ApiModelProperty("树节点类型")
    private String treeType;

    /**
     * 业务数据ID （部门或其他）
     */
    @ApiModelProperty("来源id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sourceId;

    @ApiModelProperty("来源Code")
    private String sourceCode;

    /**
     * 树节点选中状态（1：选中，0：未选中）
     */
    @ApiModelProperty(value = "树节点选中状态（1：选中，0：未选中）")
    private Boolean checked;
    /**
     * 树节点展开状态（1：展开，0：关闭）
     */
    @ApiModelProperty(value = "树节点展开状态（1：展开，0：关闭）")
    private Boolean expanded;

    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点")
    private List<TreeNode> children;

}
