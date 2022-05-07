package io.hiwepy.admin.shadow.service;

import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.service.IBaseService;

/**
 * 公共查询Service
 */
public interface ICommonService extends IBaseService<PairModel> {


	/**
	 *  数据库当前秒 ：
	 * @return
	 */
	Long getNow();

	/**
	 *  数据库当前毫秒 ：
	 * @return
	 */
	Long getNowMillis();

	/**
	 *  数据库当前时间 ：
	 * @return
	 */
	String getNowString(String format);

}
