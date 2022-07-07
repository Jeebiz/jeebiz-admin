package io.hiwepy.admin.authz.org.setup.strategy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.hiwepy.boot.api.sequence.Sequence;
import io.hiwepy.cloud.user.dao.entities.SjZxxxXxjbsjlbEntity;
import io.hiwepy.cloud.user.service.SjZxxxXxjbsjlbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class TreeXxStrategy extends AbstractTreeStrategy{

    @Autowired
    private SjZxxxXxjbsjlbService sjZxxxXxjbsjlbService;
    @Autowired
    private Sequence sequence;

    @Override
    public TreeChannel getChannel() {
        return TreeChannel.TREE_XX;
    }

    @Override
    public List<TreeNode> initNode(TreeNode node) {
        List<SjZxxxXxjbsjlbEntity> list = sjZxxxXxjbsjlbService.list(new QueryWrapper<SjZxxxXxjbsjlbEntity>().eq("xd", node.getSourceCode()));
        List<TreeNode> treeNodes = super.initNode(node);
        for (SjZxxxXxjbsjlbEntity  entity: list) {
            TreeNode treeNode = new TreeNode();
            Long treeId = sequence.nextId();
            treeNode.setId(treeId);
            treeNode.setSourceId(entity.getId());
            treeNode.setSourceCode(entity.getXxdm());
            treeNode.setTreeName(entity.getXxmc());
            if(Objects.nonNull(node)){
                treeNode.setTreeParentId(node.getId());
            }
            treeNode.setTreeType("2");
            treeNode.setSourceType("02");
            treeNodes.add(treeNode);
        }
        // 查询学段数据返回树集合

        return treeNodes;
    }
}
