package io.hiwepy.admin.authz.org.service;

import io.hiwepy.admin.authz.org.dao.entities.TreeConfigEntity;
import io.hiwepy.admin.authz.org.setup.strategy.TreeNode;
import io.hiwepy.boot.api.service.IBaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 * @since 2022-04-22
 */
public interface ITreeConfigService extends IBaseService<TreeConfigEntity> {

    List<TreeNode> getTreeByTreeId(String term, Long sourceId);
    List<TreeNode> getTreeByTreeId(String term);

}
