/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.dbmeta.dao;

import org.apache.ibatis.annotations.Mapper;

import net.jeebiz.admin.extras.dbmeta.dao.entities.DatabaseMetaModel;
import net.jeebiz.boot.api.dao.BaseDao;

@Mapper
public interface IDatabaseMetaDao extends BaseDao<DatabaseMetaModel> {
	
	
}
