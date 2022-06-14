package io.hiwepy.admin.authz.org.setup.strategy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.hiwepy.boot.api.sequence.Sequence;
import io.hiwepy.cloud.user.dao.entities.SjZxxxXqjbsjzlbEntity;
import io.hiwepy.cloud.user.service.SjZxxxXqjbsjzlbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class TreeXqStrategy extends AbstractTreeStrategy {


    @Autowired
    private SjZxxxXqjbsjzlbService sjZxxxXqjbsjzlbService;

    @Autowired
    private Sequence sequence;

    @Override
    public TreeChannel getChannel() {
        return TreeChannel.TREE_XQ;
    }


    @Override
    public List<TreeNode> initNode(TreeNode node) {

        List<SjZxxxXqjbsjzlbEntity> list = sjZxxxXqjbsjzlbService.list(new QueryWrapper<SjZxxxXqjbsjzlbEntity>().eq("xxdm", node.getSourceCode()));
        List<TreeNode> treeNodes = super.initNode(node);
        for (SjZxxxXqjbsjzlbEntity sjZxxxXqjbsjzlbEntity:list) {

            TreeNode treeNode = new TreeNode();
            Long treeId = sequence.nextId();
            treeNode.setId(treeId);
            treeNode.setSourceId(sjZxxxXqjbsjzlbEntity.getId().longValue());
            treeNode.setTreeName(sjZxxxXqjbsjzlbEntity.getXqmc());
            if(Objects.nonNull(node)){
                treeNode.setTreeParentId(node.getId());
            }
            treeNode.setTreeType("2");
            treeNode.setSourceType("03");
            treeNodes.add(treeNode);
        }
        // 查询学段数据返回树集合

        return treeNodes;
    }
}
