package io.hiwepy.admin.extras.xxljob.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.hiwepy.admin.extras.xxljob.dao.entities.XxlJobInfo;


/**
 * job info
 * @author xuxueli 2016-1-12 18:03:45
 */
@Mapper
public interface XxlJobInfoMapper {

	public List<XxlJobInfo> pageList(@Param("offset") int offset,
									 @Param("pagesize") int pagesize,
									 @Param("jobGroup") int jobGroup,
									 @Param("triggerStatus") int triggerStatus,
									 @Param("jobDesc") String jobDesc,
									 @Param("executorHandler") String executorHandler,
									 @Param("author") String author);
	public int pageListCount(@Param("offset") int offset,
							 @Param("pagesize") int pagesize,
							 @Param("jobGroup") int jobGroup,
							 @Param("triggerStatus") int triggerStatus,
							 @Param("jobDesc") String jobDesc,
							 @Param("executorHandler") String executorHandler,
							 @Param("author") String author);
	
	public int save(XxlJobInfo info);

    public XxlJobInfo loadByGroup(@Param("jobGroup") int jobGroup,  @Param("executorHandler") String executorHandler);
    
	public XxlJobInfo loadById(@Param("id") int id);
	
	public int update(XxlJobInfo xxlJobInfo);
	
	public int delete(@Param("id") long id);

	public List<XxlJobInfo> getJobsByGroup(@Param("jobGroup") int jobGroup);

	public int findAllCount();

	public List<XxlJobInfo> scheduleJobQuery(@Param("maxNextTime") long maxNextTime, @Param("pagesize") int pagesize );

	public int scheduleUpdate(XxlJobInfo xxlJobInfo);


}
