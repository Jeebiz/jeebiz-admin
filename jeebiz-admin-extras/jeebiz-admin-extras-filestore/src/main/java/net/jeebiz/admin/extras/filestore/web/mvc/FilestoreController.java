/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.filestore.web.mvc;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.biz.authz.principal.ShiroPrincipal;
import org.apache.shiro.biz.utils.SubjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.biz.utils.WebUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
import net.jeebiz.admin.extras.filestore.strategy.FilestoreStrategyRouter;
import net.jeebiz.admin.extras.filestore.web.param.FileDeleteByPathParam;
import net.jeebiz.admin.extras.filestore.web.param.FileDeleteByUuidParam;
import net.jeebiz.admin.extras.filestore.web.param.FileDeleteParam;
import net.jeebiz.admin.extras.filestore.web.param.FileDownloadByPathParam;
import net.jeebiz.admin.extras.filestore.web.param.FileDownloadByUuidParam;
import net.jeebiz.admin.extras.filestore.web.param.FileListByPathParam;
import net.jeebiz.admin.extras.filestore.web.param.FileListByUuidParam;
import net.jeebiz.admin.extras.filestore.web.param.FileReuploadParam;
import net.jeebiz.admin.extras.filestore.web.param.FileUploadParam;
import net.jeebiz.admin.extras.filestore.web.param.FilesUploadParam;
import net.jeebiz.boot.api.ApiCode;
import net.jeebiz.boot.api.ApiRestResponse;
import net.jeebiz.boot.api.XHeaders;
import net.jeebiz.boot.api.web.BaseApiController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "文件服务：文件上传、下载（Ok）")
@RestController
@RequestMapping("/file/store/")
@Slf4j
@Validated
public class FilestoreController extends BaseApiController {

	@Autowired
	private FilestoreStrategyRouter filestoreStrategyRouter;
	
	@ApiOperation(value = "文件服务配置信息", notes = "文件服务配置信息")
    @GetMapping("config")
	public ApiRestResponse<FilestoreConfig> config(@ApiParam(value = "存储目标", required = true, defaultValue = "LOCAL") @RequestParam(value = "channel", required = true, defaultValue = "LOCAL") FilestoreChannel channel){
		FilestoreConfig config = filestoreStrategyRouter.routeFor(channel).getConfig();
		return ApiRestResponse.success(config);
	}
	
	@ApiOperation(value = "文件服务：单上传文件", notes = "将单个文件上传到指定的存储对象")
    @PostMapping(value = "upload", headers = "content-type=multipart/form-data")
	@RequiresAuthentication
    public ApiRestResponse<FileData> upload(
    		@Valid FileUploadParam param,
    		@RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1") String appId,
			@RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
			@RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
			@RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
			@ApiIgnore HttpServletRequest request) throws Exception {
		try {

			log.info("upload => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);
			
			// 1、当前登录用户
			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			// 2、文件上传参数
			FileUploadBO uploadBo = FileUploadBO.builder()
					.appId(appId)
	                .appChannel(appChannel)
	                .appVer(appVersion)
					.channel(param.getChannel())
					.file(param.getFile())
					.width(param.getWidth())
					.height(param.getHeight())
					.userId(principal.getUserid())
					.ipAddress(WebUtils.getRemoteAddr(request))
					.build();
			
			// 3、执行文件上传操作
			FileUploadResult uploadRt = filestoreStrategyRouter.routeFor(param.getChannel()).upload(uploadBo);
			if(uploadRt.getStatus() == 1) {
				return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("file.upload.success"), uploadRt.getFile());
			}
			return fail("file.upload.fail");
		} catch (Exception e) {
			return ApiRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value = "文件服务：多上传文件", notes = "将多个文件上传到指定的存储对象")
    @PostMapping(value = "uploads", headers = "content-type=multipart/form-data")
	@RequiresAuthentication
    public ApiRestResponse<List<FileData>> uploads(
    		@Valid FilesUploadParam param,
    		@RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1")  String appId,
			@RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
			@RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
			@RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
			@ApiIgnore HttpServletRequest request) throws Exception {
		if (param.getFiles().length == 0){
			return ApiRestResponse.of(ApiCode.SC_UNSATISFIED_PARAM);
		}
		
		try {

			log.info("uploads => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);
			
			// 1、当前登录用户
			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			// 2、文件上传参数
			FilesUploadBO uploadBo = FilesUploadBO.builder()
					.appId(appId)
	                .appChannel(appChannel)
	                .appVer(appVersion)
					.channel(param.getChannel())
					.files(param.getFiles())
					.width(param.getWidth())
					.height(param.getHeight())
					.userId(principal.getUserid())
					.ipAddress(WebUtils.getRemoteAddr(request))
					.build();
			
			// 3、执行文件上传操作
			FilesUploadResult uploadRt = filestoreStrategyRouter.routeFor(param.getChannel()).upload(uploadBo);
			if(uploadRt.getStatus() == 1) {
				return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("file.upload.success"), uploadRt.getFiles());
			}
			return fail("file.upload.fail");
		} catch (Exception e) {
			return ApiRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value = "文件服务：根据uuid删除文件", notes = "删除指定存储对象下的指定文件")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "uuid", value = "文件Uid", required = true, dataType = "String"),
	})
	@PostMapping("delete")
	@RequiresAuthentication
	public ApiRestResponse<String> delete(
			@Valid @RequestBody FileDeleteParam param,
			@RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1")  String appId,
			@RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
			@RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
			@RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
			@ApiIgnore HttpServletRequest request){
		try {

			log.info("delete => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);
			
			// 1、当前登录用户
			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			// 2、文件删除参数
			FileDeleteBO deleteBo = FileDeleteBO.builder()
					.appId(appId)
	                .appChannel(appChannel)
	                .appVer(appVersion)
					.channel(param.getChannel())
					.userId(principal.getUserid())
					.uuids(Arrays.asList(param.getUuid()))
					.ipAddress(WebUtils.getRemoteAddr(request))
					.build();
			
			// 3、执行文件删除操作
			filestoreStrategyRouter.routeFor(param.getChannel()).delete(deleteBo);
			
			return success("file.delete.success");
		} catch (Exception e) {
			return ApiRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value = "文件服务：根据path批量删除文件", notes = "批量删除指定存储对象下的指定文件")
	@PostMapping("deleteByPath")
	@RequiresAuthentication
	public ApiRestResponse<String> deleteByPath(
			@Valid @RequestBody FileDeleteByPathParam param,
			@RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1")  String appId,
			@RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
			@RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
			@RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
			@ApiIgnore HttpServletRequest request){
		try {

			log.info("deleteByPath => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);
			
			// 1、当前登录用户
			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			// 2、文件删除参数
			FileDeleteBO deleteBo = FileDeleteBO.builder()
					.appId(appId)
	                .appChannel(appChannel)
	                .appVer(appVersion)
					.channel(param.getChannel())
					.paths(param.getPaths())
					.userId(principal.getUserid())
					.ipAddress(WebUtils.getRemoteAddr(request))
					.build();
			
			// 3、执行文件删除操作
			filestoreStrategyRouter.routeFor(param.getChannel()).delete(deleteBo);
			
			return success("file.delete.success");
		} catch (Exception e) {
			logException(this, e);
			return ApiRestResponse.error(e.getMessage());
		}
	}

	@ApiOperation(value = "文件服务：根据uuid批量删除文件", notes = "批量删除指定存储对象下的指定文件")
	@PostMapping("deleteByUuid")
	@RequiresAuthentication
	public ApiRestResponse<String> deleteByUuid(
			@Valid @RequestBody FileDeleteByUuidParam param,
			@RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1")  String appId,
			@RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
			@RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
			@RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
			@ApiIgnore HttpServletRequest request){
		try {

			log.info("deleteByUuid => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);
			
			// 1、当前登录用户
			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			// 2、文件删除参数
			FileDeleteBO deleteBo = FileDeleteBO.builder()
					.appId(appId)
	                .appChannel(appChannel)
	                .appVer(appVersion)
					.channel(param.getChannel())
					.userId(principal.getUserid())
					.uuids(param.getUuids())
					.ipAddress(WebUtils.getRemoteAddr(request))
					.build();
			
			// 3、执行文件删除操作
			filestoreStrategyRouter.routeFor(param.getChannel()).delete(deleteBo);
			
			return success("file.delete.success");
		} catch (Exception e) {
			return ApiRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value = "文件服务：重新上传文件", notes = "重新上传指定的文件")
    @PostMapping(value = "reupload", headers = "content-type=multipart/form-data")
	@RequiresAuthentication
    public ApiRestResponse<FileData> reupload(
    		@Valid FileReuploadParam param,
    		@RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1")  String appId,
			@RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
			@RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
			@RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
			@ApiIgnore HttpServletRequest request) throws Exception {
		
		try {
			
			log.info("reupload => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);
			
			// 1、当前登录用户
			ShiroPrincipal principal = SubjectUtils.getPrincipal(ShiroPrincipal.class);
			
			// 2、文件重新上传参数
			FileReuploadBO uploadBo = FileReuploadBO.builder()
					.appId(appId)
	                .appChannel(appChannel)
	                .appVer(appVersion)
					.channel(param.getChannel())
					.file(param.getFile())
					.width(param.getWidth())
					.height(param.getHeight())
					.userId(principal.getUserid())
					.ipAddress(WebUtils.getRemoteAddr(request))
					.build();
			
			// 3、执行文件上传操作
			FileReuploadResult uploadRt = filestoreStrategyRouter.routeFor(param.getChannel()).reupload(uploadBo);
			if(uploadRt.getStatus() == 1) {
				return ApiRestResponse.of(ApiCode.SC_SUCCESS, getMessage("file.reupload.success"), uploadRt.getFile());
			}
			return fail("file.reupload.fail");
		} catch (Exception e) {
			return ApiRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value = "文件服务：根据path查询文件信息", notes = "根据给出的文件相对路径获取文件信息")
	@PostMapping("listByPath")
	@RequiresAuthentication
    public ApiRestResponse<List<FileData>> listByPath(
			@Valid @RequestBody FileListByPathParam param,
			@RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1")  String appId,
			@RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
			@RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
			@RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
			@ApiIgnore HttpServletRequest request){
		try {
			
			log.info("listByPath => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);
			
			// 1、执行文件删除操作
			List<FileData> fileDatas = filestoreStrategyRouter.routeFor(param.getChannel()).listByPath(param.getPaths());
			
			return ApiRestResponse.success(fileDatas);
		} catch (Exception e) {
			return ApiRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value = "文件服务：根据uuid查询文件信息", notes = "根据给出的文件Uuid获取文件信息")
	@PostMapping("listByUuid")
	@RequiresAuthentication
    public ApiRestResponse<List<FileData>> listByUuid(
			@Valid @RequestBody FileListByUuidParam param,
			@RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1")  String appId,
			@RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
			@RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
			@RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
			@ApiIgnore HttpServletRequest request){
		try {
			
			log.info("listByUuid => appId：{}, appChannel：{}, appVersion：{},", appId, appChannel, appVersion);
			
			// 1、执行文件删除操作
			List<FileData> fileDatas = filestoreStrategyRouter.routeFor(param.getChannel()).listByPath(param.getUuids());
			
			return ApiRestResponse.success(fileDatas);
		} catch (Exception e) {
			return ApiRestResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value = "文件服务：根据path下载文件", notes = "根据给出的文件相对路径下载文件")
    @GetMapping("downloadByPath")
	@RequiresAuthentication
	public ResponseEntity<byte[]> downloadByPath(
			@Valid @RequestBody FileDownloadByPathParam param,
			@RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1")  String appId,
			@RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
			@RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
			@RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
			@ApiIgnore HttpServletRequest request){

		ResponseEntity<byte[]> entity = null;
		try {

			log.info("downloadByPath => appId：{}, appChannel：{}, appVersion：{}, path：{}", appId, appChannel, appVersion, param.getPath());
			
			FileDownloadResult downloadRt = filestoreStrategyRouter.routeFor(param.getChannel()).downloadByPath(param.getPath());
			if (Objects.nonNull(downloadRt)) {
				
				String filename = downloadRt.getFile().getName();
				
				// 定义http头 ，状态
				HttpHeaders header = new HttpHeaders();
				header.add("Content-Disposition", "attchement;filename=" + filename);
				header.setContentType(MediaTypeFactory.getMediaType(filename).get());

				// 定义ResponseEntity封装返回信息
				return new ResponseEntity<byte[]>(downloadRt.getBytes(), header, HttpStatus.OK);

			}

		} catch (Exception ignored) {
			ignored.printStackTrace();
		}

		return entity;
	}

	@ApiOperation(value = "文件服务：根据uuid下载文件", notes = "根据给出的文件Uuid下载文件")
	@GetMapping("downloadByUuid")
	@RequiresAuthentication
	public ResponseEntity<byte[]> downloadByUuid(
			@Valid @RequestBody FileDownloadByUuidParam param,
			@RequestHeader(value = XHeaders.X_APP_ID, defaultValue = "1")  String appId,
			@RequestHeader(value = XHeaders.X_APP_CHANNEL, defaultValue = "0") String appChannel,
			@RequestHeader(value = XHeaders.X_APP_VERSION, defaultValue = "1.0.0") String appVersion,
			@RequestHeader(value = XHeaders.X_LANGUAGE, defaultValue = "en") String languageCode,
			@ApiIgnore HttpServletRequest request) throws Exception {
		ResponseEntity<byte[]> entity = null;
		try {
			
			log.info("downloadByUuid => appId：{}, appChannel：{}, appVersion：{}, path：{}", appId, appChannel, appVersion, param.getUuid());
			
			FileDownloadResult downloadRt = filestoreStrategyRouter.routeFor(param.getChannel()).downloadByUuid(param.getUuid());
			if (Objects.nonNull(downloadRt)) {
				
				String filename = downloadRt.getFile().getName();
				
				// 定义http头 ，状态
				HttpHeaders header = new HttpHeaders();
				header.add("Content-Disposition", "attchement;filename=" + filename);
				header.setContentType(MediaTypeFactory.getMediaType(filename).get());

				// 定义ResponseEntity封装返回信息
				return new ResponseEntity<byte[]>(downloadRt.getBytes(), header, HttpStatus.OK);

			}

		} catch (Exception ignored) {
			ignored.printStackTrace();
		}

		return entity;
	}
	
}
