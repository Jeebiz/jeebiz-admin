package io.hiwepy.admin.authz.org.setup.strategy;

import io.hiwepy.boot.api.sequence.Sequence;
import io.hiwepy.cloud.user.dao.entities.TDictCodeStandardEntity;
import io.hiwepy.cloud.user.service.ITDictCodeStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class TreeXdStrategy extends AbstractTreeStrategy{
    @Autowired
    private ITDictCodeStandardService itDictCodeStandardService;

    @Autowired
    private Sequence sequence;

    @Override
    public TreeChannel getChannel() {
        return TreeChannel.TREE_XD;
    }

    @Override
    public List<TreeNode> initNode(TreeNode node) {

        List<TDictCodeStandardEntity> xxbxlxm = itDictCodeStandardService.selectDictCodeStandardList("xxbxlxm");

        List<TreeNode> treeNodes = super.initNode(node);
        for (TDictCodeStandardEntity entity: xxbxlxm) {
            TreeNode treeNode = new TreeNode();
            Long treeId = sequence.nextId();
            treeNode.setId(treeId);
            treeNode.setSourceId(entity.getId());
            treeNode.setSourceCode(entity.getDm());
            treeNode.setTreeName(entity.getMc());
            if(Objects.nonNull(node)){
                treeNode.setTreeParentId(node.getId());
            }
            treeNode.setTreeType("2");
            treeNode.setSourceType("01");
            treeNodes.add(treeNode);
        }
        // 查询学段数据返回树集合

        return treeNodes;
    }
        // 查询学段数据返回树集合

}
