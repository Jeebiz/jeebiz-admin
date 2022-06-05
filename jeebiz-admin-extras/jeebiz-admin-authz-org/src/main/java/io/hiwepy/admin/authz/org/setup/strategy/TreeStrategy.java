package io.hiwepy.admin.authz.org.setup.strategy;

import java.util.List;

/**
 * 树
 */
public interface TreeStrategy {

    /**
     * 返回树节点类型
     * @return
     */
	TreeChannel getChannel();

    /**
     * 获取子节点数据
     * @param node
     * @return
     */
    List<TreeNode> initNode(TreeNode node);

}
