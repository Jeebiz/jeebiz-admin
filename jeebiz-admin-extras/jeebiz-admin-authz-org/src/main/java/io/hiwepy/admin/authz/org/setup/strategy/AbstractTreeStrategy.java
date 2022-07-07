package io.hiwepy.admin.authz.org.setup.strategy;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;

import java.util.List;

@Slf4j
public abstract class AbstractTreeStrategy implements TreeStrategy {

	@Autowired
	private RedisOperationTemplate redisOperation;

	@Override
	public List<TreeNode> initNode(TreeNode node) {
		return Lists.newArrayList();
	}

	public RedisOperationTemplate getRedisOperation() {
		return redisOperation;
	}

}
