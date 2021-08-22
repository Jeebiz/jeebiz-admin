package net.jeebiz.admin.extras.filestore.strategy;

import net.jeebiz.admin.extras.filestore.bo.FileDeleteBO;
import net.jeebiz.admin.extras.filestore.bo.FileUploadBO;
import net.jeebiz.admin.extras.filestore.bo.FileUploadResult;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;
import net.jeebiz.admin.extras.filestore.web.dto.FilestoreConfig;

/**
 * 短信发送策略
 */
public interface FilestoreStrategy {

	/**
	 * 获取发送渠道
	 * @return
	 */
	FilestoreChannel getChannel();

	/**
	 * Filestore Config
	 * @return
	 */
	FilestoreConfig getConfig();
	
	<O extends FileUploadBO> FileUploadResult upload(O uploadBo) throws Exception;
	
	<O extends FileUploadBO> FileUploadResult reupload(O uploadBo) throws Exception;
	
	<O extends FileDeleteBO> boolean delete(O deleteBo) throws Exception;
	
}
