package net.jeebiz.admin.extras.filestore.strategy;

import java.util.List;

import net.jeebiz.admin.extras.filestore.bo.FileData;
import net.jeebiz.admin.extras.filestore.bo.FileDeleteBO;
import net.jeebiz.admin.extras.filestore.bo.FileDownloadResult;
import net.jeebiz.admin.extras.filestore.bo.FileReuploadBO;
import net.jeebiz.admin.extras.filestore.bo.FileReuploadResult;
import net.jeebiz.admin.extras.filestore.bo.FileUploadBO;
import net.jeebiz.admin.extras.filestore.bo.FileUploadResult;
import net.jeebiz.admin.extras.filestore.bo.FilesUploadBO;
import net.jeebiz.admin.extras.filestore.bo.FilesUploadResult;
import net.jeebiz.admin.extras.filestore.bo.FilestoreConfig;
import net.jeebiz.admin.extras.filestore.enums.FilestoreChannel;

/**
 * 文件存储策略
 */
public interface FilestoreStrategy {

	/**
	 * 获取渠道
	 * @return
	 */
	FilestoreChannel getChannel();

	/**
	 * Filestore Config
	 * @return
	 */
	FilestoreConfig getConfig();

	<O extends FileUploadBO> FileUploadResult upload(O uploadBo) throws Exception;

	<O extends FilesUploadBO> FilesUploadResult upload(O uploadBo) throws Exception;

	<O extends FileReuploadBO> FileReuploadResult reupload(O uploadBo) throws Exception;

	<O extends FileDeleteBO> boolean delete(O deleteBo) throws Exception;

	/**
	  *   根据给出的文件相对路径获取文件信息
	 * @param paths
	 * @return
	 */
	List<FileData> listByPath(List<String> paths) throws Exception;

	/**
	  *   根据给出的文件相对路径获取文件信息
	 * @param uuids
	 * @return
	 */
	List<FileData> listByUuid(List<String> uuids) throws Exception;

	/**
	 * 根据给出的文件相对路径下载文件
	 * @param path	要下载的文件path
	 * @return
	 */
	FileDownloadResult downloadByPath(String path) throws Exception;

	/**
	 * 根据给出的文件Uuid下载文件
	 * @param uuid	要下载的文件Uuid
	 * @return
	 */
	FileDownloadResult downloadByUuid(String uuid) throws Exception;


}
