package io.hiwepy.admin.authz.org.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import io.hiwepy.admin.authz.org.dao.TreeConfigMapper;
import io.hiwepy.admin.authz.org.dao.entities.TreeConfigEntity;
import io.hiwepy.admin.authz.org.service.ITreeConfigService;
import io.hiwepy.admin.authz.org.setup.strategy.TreeChannel;
import io.hiwepy.admin.authz.org.setup.strategy.TreeNode;
import io.hiwepy.admin.authz.org.setup.strategy.TreeStrategyRouter;
import io.hiwepy.boot.api.sequence.Sequence;
import io.hiwepy.boot.api.service.BaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class TreeConfigServiceImpl extends BaseServiceImpl<TreeConfigMapper, TreeConfigEntity> implements ITreeConfigService {

    @Autowired
    private Sequence sequence;

    @Autowired
    private TreeStrategyRouter treeStrategyRouter;

    @Autowired
    private TreeConfigMapper orgTreeConfigMapper;


    @Override
    public List<TreeNode> getTreeByTreeId(String term, Long sourceId) {
        return null;
    }

    @Override
    public List<TreeNode> getTreeByTreeId(String treeId) {
        // 1、查询树层级配置
        List<TreeConfigEntity> treeConfigEntities =  getBaseMapper().selectList(new QueryWrapper<TreeConfigEntity>().eq("tree_id", treeId));
        // 2、层级配置变为tree结构
        List<TreeConfigEntity> treeConfigList = this.selectConfigTree(treeConfigEntities);
        // 3、学校
        TreeNode pTreeNode = new TreeNode();
        pTreeNode.setId(sequence.nextId());
        List<TreeNode> tree = this.selectTree(pTreeNode, treeConfigList);
        System.out.println(JSONArray.toJSON(tree));
        return tree;
    }

    public List<TreeConfigEntity> selectConfigTree(List<TreeConfigEntity> list) {
        return list.stream().filter(entity -> entity.getTreeParentId() == 0)
                .peek(entity -> entity.setChildren(getConfigChildNode(entity,list))).collect(Collectors.toList());
    }

    private List<TreeConfigEntity> getConfigChildNode(TreeConfigEntity treeEntity, List<TreeConfigEntity> allListTree) {
        return allListTree.stream().filter(entity -> Objects.equals(entity.getTreeParentId(),treeEntity.getId()))
                .peek(entity -> entity.setChildren(getConfigChildNode(entity, allListTree))).collect(Collectors.toList());
    }


    //装配各层级节点
    public List<TreeNode> selectTree(TreeNode pTreeNode, List<TreeConfigEntity> list) {
        List<TreeNode> treeList = Lists.newArrayList();
        for (TreeConfigEntity configEntity: list) {
            //节点类型，虚拟节点
            if (configEntity.getTreeType().equals("1")){
                TreeNode treeNode = new TreeNode();
                treeNode.setId(configEntity.getId());
                treeNode.setLabel(configEntity.getTreeLabel());
                treeNode.setTreeType(configEntity.getTreeType());
                treeNode.setParentId(configEntity.getTreeParentId());
                if(CollectionUtils.isNotEmpty(configEntity.getChildren())){
                    treeNode.setChildren(this.selectTree(pTreeNode, configEntity.getChildren()));
                }
                treeList.add(treeNode);
            } else {
                //数据节点

                //1.检查数据来源
                TreeChannel treeChannel = TreeChannel.valueOfIgnoreCase(configEntity.getTreeDataSource());
                //2.路由策略装配数据
                List<TreeNode> treeChildrenList = treeStrategyRouter.route(treeChannel).initNode(pTreeNode);
                for (TreeNode childrenTreeNode: treeChildrenList) {
                    if(CollectionUtils.isNotEmpty(configEntity.getChildren())){
                        childrenTreeNode.setChildren(this.selectTree(childrenTreeNode, configEntity.getChildren()));
                    }
                }
                treeList.addAll(treeChildrenList);
            }
        }
        return treeList;
    }

}
