package net.jeebiz.admin.extras.core.service;

import net.jeebiz.boot.api.dao.entities.PairModel;
import net.jeebiz.boot.api.service.IBaseService;

/**
 * 公共查询Service
 */
public interface ICommonService extends IBaseService<PairModel> {

	/**
	 *  数据库当前时间 ： 
	 * @return
	 */
	String getNow();

}
