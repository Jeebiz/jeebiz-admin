package net.jeebiz.admin.extras.article.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import net.jeebiz.admin.extras.article.dao.entities.ArticleTopicModel;
import net.jeebiz.admin.extras.article.web.dto.ArticleTopicTreeDTO;

public final class TopicUtils {

	public static List<ArticleTopicTreeDTO> getTopicTreeList(List<ArticleTopicModel> topicList) {
		
		// 优先获得最顶层的主题集合
		List<ArticleTopicModel> topTopicList = topicList.stream()
				.filter(topic -> StringUtils.equals("0", topic.getPid()))
				.collect(Collectors.toList());
		
		List<ArticleTopicTreeDTO> topics = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(topTopicList)){
			
			for (ArticleTopicModel topic : topTopicList) {
				
				ArticleTopicTreeDTO topicDTO = new ArticleTopicTreeDTO();
				
				// 上级文章栏目id
				topicDTO.setPid(topic.getId());
				topicDTO.setPname(topic.getPname());
				// 文章栏目id
				topicDTO.setId(topic.getId());
				// 文章栏目名称
				topicDTO.setName(topic.getName());
				// 文章栏目类型
				topicDTO.setCid(topic.getCid());
				topicDTO.setCname(topic.getCname());
				// 文章栏目备注
				topicDTO.setRemark(topic.getRemark());
				// 文章栏目状态（0:禁用|1:可用）
				topicDTO.setStatus(topic.getStatus());
				// 文章栏目排序
				topicDTO.setOrder(topic.getOrder());
				// 文章栏目创建时间
				topicDTO.setTime24(topic.getTime24());
				// 子文章栏目
				List<ArticleTopicTreeDTO> subTopics = getSubTopicList(topic, topicList);
				if(CollectionUtils.isNotEmpty(subTopics)) {
					topicDTO.setChildren(subTopics);
				}
				topics.add(topicDTO);
			}
			return topics.stream().sorted().collect(Collectors.toList());
		}
		return topics;
	}
	
	protected static List<ArticleTopicTreeDTO> getSubTopicList(ArticleTopicModel parentTopic, List<ArticleTopicModel> topicList) {
		
		List<ArticleTopicTreeDTO> topics = Lists.newArrayList();
		//筛选当前父功能模块节点的子功能模块节点数据
		List<ArticleTopicModel> childTopicList = topicList.stream()
				.filter(topic -> StringUtils.equals(parentTopic.getId(), topic.getPid()))
				.collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(childTopicList)){
			for (ArticleTopicModel topic : childTopicList) {
				ArticleTopicTreeDTO topicDTO = new ArticleTopicTreeDTO();
				// 上级文章栏目id
				topicDTO.setPid(topic.getId());
				topicDTO.setPname(topic.getPname());
				// 文章栏目id
				topicDTO.setId(topic.getId());
				// 文章栏目名称
				topicDTO.setName(topic.getName());
				// 文章栏目类型
				topicDTO.setCid(topic.getCid());
				topicDTO.setCname(topic.getCname());
				// 文章栏目备注
				topicDTO.setRemark(topic.getRemark());
				// 文章栏目状态（0:禁用|1:可用）
				topicDTO.setStatus(topic.getStatus());
				// 文章栏目排序
				topicDTO.setOrder(topic.getOrder());
				// 文章栏目创建时间
				topicDTO.setTime24(topic.getTime24());
				// 子文章栏目
				List<ArticleTopicTreeDTO> subTopics = getSubTopicList(topic, topicList);
				if(CollectionUtils.isNotEmpty(subTopics)) {
					topicDTO.setChildren(subTopics);
				}
				topics.add(topicDTO);
			}
			return topics.stream().sorted().collect(Collectors.toList());
		}
		return topics;
	}
	
}
