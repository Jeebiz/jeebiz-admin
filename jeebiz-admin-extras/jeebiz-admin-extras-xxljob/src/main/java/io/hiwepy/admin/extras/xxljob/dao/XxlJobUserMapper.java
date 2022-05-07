package io.hiwepy.admin.extras.xxljob.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.hiwepy.admin.extras.xxljob.dao.entities.XxlJobUser;

/**
 * @author xuxueli 2019-05-04 16:44:59
 */
@Mapper
public interface XxlJobUserMapper {

	public List<XxlJobUser> pageList(@Param("offset") int offset,
                                     @Param("pagesize") int pagesize,
                                     @Param("account") String account,
									 @Param("role") int role);
	public int pageListCount(@Param("offset") int offset,
							 @Param("pagesize") int pagesize,
							 @Param("account") String account,
							 @Param("role") int role);

	public XxlJobUser loadByUserName(@Param("account") String account);

	public int save(XxlJobUser xxlJobUser);

	public int update(XxlJobUser xxlJobUser);
	
	public int delete(@Param("id") int id);

}
